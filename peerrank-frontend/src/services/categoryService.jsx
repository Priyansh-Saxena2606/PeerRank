import axios from "axios";

const API = "http://localhost:8080/categories";

export async function getCategories() {

    const response = await axios.get(API);

    return response.data.data;

}