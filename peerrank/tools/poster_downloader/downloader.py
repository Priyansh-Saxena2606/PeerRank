import os
import requests

TMDB_BASE = "https://image.tmdb.org/t/p/original"


def download(image_path, destination):

    if image_path.startswith("http"):
        url = image_path
    else:
        url = TMDB_BASE + image_path

    response = requests.get(url, timeout=30)

    if response.status_code != 200:
        return False

    destination = str(destination)

    os.makedirs(
        os.path.dirname(destination),
        exist_ok=True
    )

    with open(destination, "wb") as file:
        file.write(response.content)

    return True