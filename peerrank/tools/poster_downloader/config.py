DB_CONFIG = {
    "host": "localhost",
    "port": 3306,
    "user": "root",
    "password": "Priyansh@123",
    "database": "peerrank"
}

TMDB_API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMzc0ZmIxZmUwOWQ0YjA0NWZmNDc0NzFlMGJjMDkwMSIsIm5iZiI6MTc4MjkyODQ1OS4yODEsInN1YiI6IjZhNDU1NDRiMDkzZGQwMTdlZjMxZWY3NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wn0IcwhQEnbty_s-l2wRIbugW-7V_Krh5F4Ar-QfOik"

RAWG_API_KEY = "f87ab8df16224bf99a81311c4216681e"

from pathlib import Path

PROJECT_ROOT = Path(__file__).resolve().parents[2]

IMAGE_ROOT = PROJECT_ROOT / "src" / "main" / "resources" / "static" / "images"
print("Saving images to:", IMAGE_ROOT)

CATEGORY_FOLDERS = {
    1: "movies",
    4: "games",
    5: "places",
    6: "activities",
    7: "technology",
    9: "anime",
    12: "tv"
}