import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import Navbar from "../components/Navbar";
import { getCategories } from "../services/categoryService";

export default function Categories() {

    const [categories, setCategories] = useState([]);

    useEffect(() => {

        async function fetchCategories() {

            const data = await getCategories();

            setCategories(data);

        }

        fetchCategories();

    }, []);

    return (

        <div className="min-h-screen bg-slate-950 text-white">

            <Navbar />

            <main className="mx-auto max-w-7xl px-8 py-16">

                <h1 className="text-5xl font-bold mb-12">
                    Browse Categories
                </h1>

                <div className="grid gap-8 md:grid-cols-2 lg:grid-cols-3">

                    {categories.map(category => (

                        <Link
                            key={category.id}
                            to={`/categories/${category.id}`}
                            className="rounded-3xl border border-slate-800 bg-slate-900 p-8 hover:border-violet-500 transition duration-300 hover:-translate-y-2"
                        >

                            <div className="text-5xl mb-5">

                                {getEmoji(category.name)}

                            </div>

                            <h2 className="text-2xl font-bold">

                                {category.name}

                            </h2>

                        </Link>

                    ))}

                </div>

            </main>

        </div>

    );

}

function getEmoji(name) {

    switch(name){

        case "Movies":
            return "🎬";

        case "TV Series":
            return "📺";

        case "Games":
            return "🎮";

        case "Anime":
            return "🌸";

        case "Technology":
            return "💻";

        case "Places":
            return "📍";

        case "Activities":
            return "🎯";

        default:
            return "📁";

    }

}