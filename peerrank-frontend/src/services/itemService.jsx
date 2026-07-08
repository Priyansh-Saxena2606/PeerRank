import api from "../api/axios";

export const getItem = async (id) => {
    const response = await api.get(`/items/${id}`);
    return response.data.data;
};