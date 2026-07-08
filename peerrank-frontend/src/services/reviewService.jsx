import api from "../api/axios";

// =========================
// Item Details
// =========================

export const getReviewsByItem = async (itemId) => {
    const response = await api.get(`/reviews/item/${itemId}`);
    return response.data.data;
};

export const createReview = async (review) => {
    const response = await api.post("/reviews", review);
    return response.data.data;
};

// =========================
// Home Page
// =========================

export const getTopRated = async () => {
    const response = await api.get("/reviews/top-rated");
    return response.data.data;
};

export const getMostReviewed = async () => {
    const response = await api.get("/reviews/most-reviewed");
    return response.data.data;
};

export async function updateReview(id, review) {
    const res = await api.put(`/reviews/${id}`, review);
    return res.data.data;
}
export async function deleteReview(id) {
    await api.delete(`/reviews/${id}`);
}