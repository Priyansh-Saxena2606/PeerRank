import mysql.connector
from config import DB_CONFIG


def get_connection():
    return mysql.connector.connect(**DB_CONFIG)


def get_items():

    connection = get_connection()

    cursor = connection.cursor(dictionary=True)

    cursor.execute("""
        SELECT
            id,
            title,
            release_year,
            category_id
        FROM items
        WHERE image_url IS NULL
        ORDER BY id;
    """)

    items = cursor.fetchall()

    cursor.close()
    connection.close()

    return items

def get_game_items():

    connection = get_connection()

    cursor = connection.cursor(dictionary=True)

    cursor.execute("""
        SELECT
            id,
            title,
            release_year,
            category_id
        FROM items
        WHERE category_id = 4
        ORDER BY id
    """)

    items = cursor.fetchall()

    cursor.close()
    connection.close()

    return items


def update_image(item_id, image_url):

    connection = get_connection()

    cursor = connection.cursor()

    cursor.execute(
        """
        UPDATE items
        SET image_url = %s
        WHERE id = %s
        """,
        (image_url, item_id)
    )

    connection.commit()

    cursor.close()
    connection.close()