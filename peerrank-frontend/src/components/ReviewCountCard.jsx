import { Link } from "react-router-dom";
import { MessageSquare } from "lucide-react";

import { API_BASE } from "../config";

function ReviewCountCard({ item }) {

    return (

        <Link to={`/items/${item.id}`}>

            <div className="group overflow-hidden rounded-3xl border border-slate-800 bg-slate-900 transition-all duration-300 hover:-translate-y-2 hover:border-violet-500 hover:shadow-2xl hover:shadow-violet-500/20">

                <div className="relative overflow-hidden">

                    <img
                        src={`${API_BASE}${item.imageUrl}`}
                        alt={item.title}
                        onError={(e) => {
                            e.target.src = "/placeholder.jpg";
                        }}
                        className="h-64 w-full object-cover transition duration-500 group-hover:scale-105 sm:h-72 lg:h-80"
                    />

                    <div className="absolute inset-0 bg-gradient-to-t from-black via-black/20 to-transparent" />

                </div>

                <div className="p-4 sm:p-5 lg:p-6">

                    <h3 className="truncate text-lg font-bold text-white sm:text-xl lg:text-2xl">
                        {item.title}
                    </h3>

                    <span className="mt-2 inline-flex rounded-full bg-violet-500/20 px-2.5 py-1 text-xs font-medium text-violet-300 sm:px-3 sm:text-sm">
                        🎬 {item.genre}
                    </span>

                    <div className="mt-4 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">

                        <div className="flex items-center gap-2">

                            <span className="text-yellow-400 font-semibold">
                                ⭐ {item.averageRating.toFixed(2)}
                            </span>

                        </div>

                        <div className="flex items-center gap-2 text-sm text-slate-300 sm:text-base">

                            <MessageSquare
                                size={18}
                                className="text-violet-400"
                            />

                            <span>
                                {item.reviewCount} Reviews
                            </span>

                        </div>

                    </div>

                </div>

            </div>

        </Link>

    );

}

export default ReviewCountCard;