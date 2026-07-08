import os
import re
from tqdm import tqdm

from config import CATEGORY_FOLDERS, IMAGE_ROOT
from db import get_items, update_image
from downloader import download
from tmdb import search_movie, search_tv
from rawg import search_game

FAILED = []


def slugify(text):
    text = text.lower()
    text = re.sub(r"[^\w\s-]", "", text)
    text = re.sub(r"\s+", "-", text)
    return text


def search_item(item):
    category = item["category_id"]

    # Movies
    if category == 1:
        return search_movie(item["title"], item["release_year"])

    # Anime + TV Series
    if category in [9, 12]:
        return search_tv(item["title"], item["release_year"])

    # Games will be added later
    # Games
    if category == 4:
        return search_game(item["title"])
    return None


def main():

    print("=" * 70)
    print("           PeerRank Poster Downloader")
    print("=" * 70)
    print()

    items = get_items()

    downloaded = 0
    skipped = 0
    failed = 0

    for item in tqdm(items):

        folder = CATEGORY_FOLDERS.get(item["category_id"])

        if folder is None:
            skipped += 1
            continue

        filename = slugify(item["title"]) + ".jpg"

        relative = f"/images/{folder}/{filename}"

        absolute = os.path.join(
            IMAGE_ROOT,
            folder,
            filename
        )

        if os.path.exists(absolute):
            update_image(item["id"], relative)
            skipped += 1
            continue

        result = search_item(item)

        if result is None:

            FAILED.append(item["title"])

            failed += 1

            continue

        poster = (result.get("poster_path") or result.get("image"))

        if poster is None:

            FAILED.append(item["title"])

            failed += 1

            continue

        success = download(
            poster,
            absolute
        )

        if success:

            update_image(
                item["id"],
                relative
            )

            downloaded += 1

        else:

            FAILED.append(item["title"])

            failed += 1

    print()

    print("=" * 70)

    print(f"Downloaded : {downloaded}")

    print(f"Skipped    : {skipped}")

    print(f"Failed     : {failed}")

    print("=" * 70)

    if FAILED:

        with open("failed.txt", "w", encoding="utf8") as file:

            for title in FAILED:

                file.write(title + "\n")

        print()

        print("failed.txt created.")


if __name__ == "__main__":
    main()