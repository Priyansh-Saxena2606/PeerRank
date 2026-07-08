import time
import requests
from requests.exceptions import RequestException
from config import TMDB_API_KEY

BASE = "https://api.themoviedb.org/3"

HEADERS = {
    "Authorization": f"Bearer {TMDB_API_KEY}",
    "accept": "application/json"
}


def request(endpoint, params):

    for attempt in range(3):

        try:

            response = requests.get(
                BASE + endpoint,
                headers=HEADERS,
                params=params,
                timeout=20
            )

            if response.status_code == 200:
                return response.json()

            print(
                f"TMDB Error {response.status_code}:",
                response.text
            )

        except RequestException as e:

            print(
                f"Retry {attempt+1}/3:",
                e
            )

            time.sleep(2)

    return None

import re

def clean_title(title):

    title = re.sub(r"[:\-]", " ", title)

    title = re.sub(r"[^\w\s]", "", title)

    title = re.sub(r"\s+", " ", title)

    return title.strip()

def search_movie(title, year):

    data = request(
        "/search/movie",
        {
            "query": clean_title(title),
            "year": year
        }
    )

    if not data:
        return None

    if not data["results"]:
        return None

    return data["results"][0]


def search_tv(title, year):

    data = request(
        "/search/tv",
        {
            "query": clean_title(title),
            "first_air_date_year": year
        }
    )

    if not data:
        return None

    if not data["results"]:
        return None

    return data["results"][0]