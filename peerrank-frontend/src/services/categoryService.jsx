import api from "../api/axios";
import { API_BASE } from "../config";
const API = "/categories";

export async function getCategories() {

    const response = await axios.get(API);

    return response.data.data;

}