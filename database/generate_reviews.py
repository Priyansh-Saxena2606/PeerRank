import random
import mysql.connector

# ---------- EDIT THESE ----------
DB_CONFIG = {
    "host": "localhost",
    "user": "root",
    "password": "Priyansh@123",
    "database": "peerrank"
}
# -------------------------------

comments = [
    "Absolutely loved it.",
    "A masterpiece from start to finish.",
    "Exceeded my expectations.",
    "Amazing visuals and soundtrack.",
    "Would definitely recommend.",
    "Great characters and storytelling.",
    "Worth every minute.",
    "One of my all-time favorites.",
    "Fantastic experience overall.",
    "I'll definitely revisit this.",
    "Very entertaining.",
    "Beautifully crafted.",
    "The ending was unforgettable.",
    "The pacing was excellent.",
    "Really immersive from beginning to end.",
    "Good but not perfect.",
    "A few flaws, but still enjoyable.",
    "Solid experience overall.",
    "Highly recommended.",
    "An unforgettable journey."
]

def weighted_rating():
    r = random.random()
    if r < 0.02:
        return 1
    if r < 0.05:
        return 2
    if r < 0.15:
        return 3
    if r < 0.45:
        return 4
    return 5

conn = mysql.connector.connect(**DB_CONFIG)
cur = conn.cursor()

cur.execute("SELECT id FROM items ORDER BY id")
item_ids = [row[0] for row in cur.fetchall()]

with open("reviews.sql","w",encoding="utf-8") as f:
    f.write("USE peerrank;\n\n")
    for item_id in item_ids:
        for _ in range(random.randint(8,15)):
            rating = weighted_rating()
            comment = random.choice(comments).replace("'", "''")
            f.write(
                f"INSERT INTO reviews (rating, comment, item_id) "
                f"VALUES ({rating}, '{comment}', {item_id});\n"
            )

print(f"Generated reviews.sql for {len(item_ids)} items.")
cur.close()
conn.close()
