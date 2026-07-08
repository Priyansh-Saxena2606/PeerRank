import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Categories from "./pages/Categories";
import CategoryItems from "./pages/CategoryItems";
import ItemDetails from "./pages/ItemDetails";
import Profile from "./pages/Profile";

function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route
                    path="/"
                    element={<Home />}
                />

                <Route
                    path="/categories"
                    element={<Categories />}
                />

                <Route
                    path="/categories/:id"
                    element={<CategoryItems />}
                />

                <Route
                    path="/items/:id"
                    element={<ItemDetails />}
                />

                <Route 
                    path="/profile" 
                    element={<Profile />}
                />

            </Routes>

        </BrowserRouter>

    );

}

export default App;