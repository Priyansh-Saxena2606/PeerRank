import api from "../api/axios";

export async function getMyProfile() {
    const res = await api.get("/users/me");
    return res.data.data;
}
export async function getMyReviews() {
    const res = await api.get("/users/me/reviews");
    return res.data.data;
}