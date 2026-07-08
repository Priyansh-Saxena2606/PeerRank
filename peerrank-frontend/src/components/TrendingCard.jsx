import { Link } from "react-router-dom";
import { Star } from "lucide-react";

export default function TrendingCard({ item }) {

    return (

        <Link to={`/items/${item.id}`}>

            <div className="group overflow-hidden rounded-3xl border border-slate-800 bg-slate-900 transition-all duration-300 hover:-translate-y-2 hover:border-violet-500 hover:shadow-2xl hover:shadow-violet-500/20">

                <div className="relative overflow-hidden">

                    <img
                        src={`http://localhost:8080${item.imageUrl}`}
                        alt={item.title}
                        onError={(e) => {
                            e.target.src = "/placeholder.jpg";
                        }}
                        className="h-64 w-full object-cover transition duration-500 group-hover:scale-110 sm:h-72 lg:h-80"
                    />

                    <div className="absolute inset-0 bg-gradient-to-t from-black via-black/20 to-transparent" />

                    <div className="absolute right-3 top-3 flex items-center gap-2 rounded-full bg-black/70 px-3 py-2 backdrop-blur-md sm:right-4 sm:top-4">

                        <Star
                            size={16}
                            fill="currentColor"
                            className="text-yellow-400"
                        />

                        <span className="font-bold text-white">
                            {item.averageRating.toFixed(2)}
                        </span>

                    </div>

                </div>

                <div className="p-4 sm:p-5 lg:p-6">

                    <h3 className="truncate text-xl font-bold text-white sm:text-2xl">

                        {item.title}

                    </h3>

                    <p className="mt-2 text-sm text-slate-400 sm:text-base">

                        {item.genre} • {item.releaseYear}

                    </p>

                </div>

            </div>

        </Link>

    );

}