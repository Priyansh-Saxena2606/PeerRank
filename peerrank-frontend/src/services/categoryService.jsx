import api from "../api/axios";

const API = "/categories";

export async function getCategories() {
    const response = await api.get(API);
    return response.data.data;
}