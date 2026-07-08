import requests

from config import RAWG_API_KEY

BASE_URL = "https://api.rawg.io/api/games"


def search_game(title):

    response = requests.get(
        BASE_URL,
        params={
            "key": RAWG_API_KEY,
            "search": title,
            "page_size": 1
        },
        timeout=20
    )

    if response.status_code != 200:
        print("RAWG Error", response.status_code)
        return None

    data = response.json().get("results", [])

    if not data:
        return None

    game = data[0]

    return {
        "image": game.get("background_image")
    }