import api from "../api/axios";

export async function addFavorite(itemId) {
    return api.post(`/favorites/${itemId}`);
}

export async function removeFavorite(itemId) {
    return api.delete(`/favorites/${itemId}`);
}

export async function isFavorite(itemId) {
    const res = await api.get(`/favorites/${itemId}`);
    return res.data.data;
}

export async function getFavorites() {
    const res = await api.get("/favorites");
    return res.data.data;
}
export async function getFavoriteCategories() {
    const res = await api.get("/favorites/categories");
    return res.data.data;
}