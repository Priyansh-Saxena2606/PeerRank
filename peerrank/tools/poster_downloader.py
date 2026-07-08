import os
import re
import requests
import mysql.connector
from tqdm import tqdm

from peerrank.peerrank.tools.poster_downloader.config import DB_CONFIG, TMDB_API_KEY, IMAGE_ROOT, CATEGORY_FOLDERS

TMDB_URL = "https://api.themoviedb.org/3"

HEADERS = {
    "Authorization": f"Bearer {TMDB_API_KEY}",
    "accept": "application/json"
}


def clean_filename(name):
    name = re.sub(r'[<>:"/\\|?*]', "", name)
    return name.strip()


def search_tmdb(title, category_id):
    endpoint = "/search/tv" if category_id in [9, 12] else "/search/movie"

    response = requests.get(
        TMDB_URL + endpoint,
        headers=HEADERS,
        params={"query": title},
        timeout=20
    )

    if response.status_code != 200:
        return None

    data = response.json()

    if not data.get("results"):
        return None

    poster = data["results"][0].get("poster_path")

    if not poster:
        return None

    return "https://image.tmdb.org/t/p/w500" + poster


def download_image(url, filepath):
    response = requests.get(url, timeout=30)

    if response.status_code != 200:
        return False

    with open(filepath, "wb") as file:
        file.write(response.content)

    return True


def main():

    connection = mysql.connector.connect(**DB_CONFIG)
    cursor = connection.cursor(dictionary=True)

    cursor.execute("""
        SELECT id,title,category_id
        FROM items
        ORDER BY id
    """)

    items = cursor.fetchall()

    downloaded = 0
    skipped = 0
    failed = 0

    print()
    print("=" * 60)
    print(" PeerRank Poster Downloader ")
    print("=" * 60)
    print()

    for item in tqdm(items):

        folder = CATEGORY_FOLDERS.get(item["category_id"])

        if folder is None:
            skipped += 1
            continue

        folder_path = os.path.join(IMAGE_ROOT, folder)
        os.makedirs(folder_path, exist_ok=True)

        filename = clean_filename(item["title"]).lower()
        filename = filename.replace(" ", "-") + ".jpg"

        filepath = os.path.join(folder_path, filename)

        image_url = f"/images/{folder}/{filename}"

        if os.path.exists(filepath):

            cursor.execute("""
                UPDATE items
                SET image_url=%s
                WHERE id=%s
            """, (image_url, item["id"]))

            skipped += 1
            continue

        poster = search_tmdb(item["title"], item["category_id"])

        if poster is None:
            failed += 1
            continue

        success = download_image(poster, filepath)

        if success:

            cursor.execute("""
                UPDATE items
                SET image_url=%s
                WHERE id=%s
            """, (image_url, item["id"]))

            downloaded += 1

        else:
            failed += 1

    connection.commit()

    cursor.close()
    connection.close()

    print()
    print("=" * 60)
    print("Finished")
    print("=" * 60)
    print(f"Downloaded : {downloaded}")
    print(f"Skipped    : {skipped}")
    print(f"Failed     : {failed}")


if __name__ == "__main__":
    main()