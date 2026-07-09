import Navbar from "../components/Navbar";
import {
    getMyProfile,
    getMyReviews
} from "../services/userService";

import {
    getFavorites,
    getFavoriteCategories
} from "../services/favoriteService";
import { User, Mail, Calendar, Star, MessageSquare } from "lucide-react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { API_BASE } from "../config";

export default function Profile() {

    const [profile, setProfile] = useState(null);
    const [reviews, setReviews] = useState([]);
    const navigate = useNavigate();
    const [favorites, setFavorites] = useState([]);
    const [favoriteCategories, setFavoriteCategories] = useState([]);


    useEffect(() => {
        loadProfile();
    }, []);

    async function loadProfile() {
        try {
            const [
                profileData,
                reviewData,
                favoriteData,
                categoryData
            ] = await Promise.all([
                getMyProfile(),
                getMyReviews(),
                getFavorites(),
                getFavoriteCategories()
            ]);

            setProfile(profileData);
            setReviews(reviewData);
            setFavorites(favoriteData);
            setFavoriteCategories(categoryData);
        } catch (err) {
            console.error(err);
        }
    }

    if (!profile) {
        return (
            <div className="min-h-screen bg-[#0f172a] text-white flex items-center justify-center">
                Loading...
            </div>
        );
    }

    return (
        <>
            <Navbar />

            <div className="min-h-screen bg-[#0f172a] text-white">

                <div className="mx-auto max-w-5xl px-4 py-8 sm:px-6 sm:py-10 lg:px-8 lg:py-12">

                    <div className="rounded-3xl border border-white/10 bg-white/5 p-5 sm:p-8 lg:p-10">

                        <div className="mb-10 flex flex-col items-center gap-5 text-center sm:flex-row sm:text-left">

                            <div className="flex h-20 w-20 items-center justify-center rounded-full bg-violet-600 sm:h-24 sm:w-24">

                                <span className="text-5xl font-black text-white">
                                    {profile.username.charAt(0).toUpperCase()}
                                </span>

                            </div>

                            <div>

                                <h1 className="text-3xl font-black sm:text-4xl">
                                    {profile.username}
                                </h1>

                                <p className="text-gray-400">
                                    PeerRank Member
                                </p>

                            </div>

                        </div>

                        <div className="grid grid-cols-1 gap-6 md:grid-cols-2">

                            <div className="bg-slate-900 rounded-2xl p-6">

                                <Mail className="text-violet-400 mb-3" />

                                <p className="text-gray-400">Email</p>

                                <h2 className="text-xl font-bold">
                                    {profile.email}
                                </h2>

                            </div>

                            <div className="bg-slate-900 rounded-2xl p-6">

                                <Calendar className="text-cyan-400 mb-3" />

                                <p className="text-gray-400">Joined</p>

                                <h2 className="text-xl font-bold">
                                    {new Date(profile.joinedAt).toLocaleDateString(
                                        "en-US",
                                        {
                                            day: "numeric",
                                            month: "long",
                                            year: "numeric"
                                        }
                                    )}
                                </h2>

                            </div>

                            <div className="bg-slate-900 rounded-2xl p-6">

                                <MessageSquare className="text-green-400 mb-3" />

                                <p className="text-gray-400">
                                    Reviews Written
                                </p>

                                <h2 className="text-4xl font-black">
                                    {profile.reviewCount}
                                </h2>

                            </div>

                            <div className="bg-slate-900 rounded-2xl p-6">

                                <Star
                                    className="text-yellow-400 mb-3"
                                    fill="currentColor"
                                />

                                <p className="text-gray-400">
                                    Average Rating Given
                                </p>

                                <h2 className="text-4xl font-black">
                                    {profile.averageRatingGiven.toFixed(1)}
                                </h2>

                            </div>

                        </div>

                    </div>

                    <div className="mt-6 rounded-3xl border border-white/10 bg-white/5 p-5 sm:p-8">

                        <h2 className="mb-8 text-2xl font-bold sm:text-3xl">
                            Recent Reviews
                        </h2>

                        {reviews.length === 0 ? (

                            <p className="text-gray-400">
                                You haven't written any reviews yet.
                            </p>

                        ) : (

                            <div className="space-y-6">

                                {reviews.map(review => (

                                    <div className="flex flex-col gap-6 lg:flex-row lg:justify-between">

                                        <div className="flex flex-col gap-5 sm:flex-row">

                                            <img
                                                src={`${API_BASE}${review.imageUrl}`}
                                                alt={review.itemTitle}
                                                className="w-20 h-28 rounded-xl object-cover"
                                            />

                                            <div>

                                                <h3 className="mb-2 text-xl font-bold sm:text-2xl">
                                                    {review.itemTitle}
                                                </h3>

                                                <div className="flex gap-1 mb-4">

                                                    {Array.from({ length: review.rating }).map((_, i) => (

                                                        <Star
                                                            key={i}
                                                            size={18}
                                                            fill="currentColor"
                                                            className="text-yellow-400"
                                                        />

                                                    ))}

                                                </div>

                                                <p className="max-w-xl break-words text-gray-300">
                                                    {review.comment}
                                                </p>

                                                <button
                                                    onClick={() => navigate(`/items/${review.itemId}`)}
                                                    className="mt-5 rounded-lg bg-violet-600 px-4 py-2 text-sm font-semibold hover:bg-violet-700 transition"
                                                >
                                                    View Item →
                                                </button>

                                            </div>

                                        </div>

                                    </div>


                                ))}

                            </div>


                        )}

                    </div>

                    <div className="mt-10 bg-white/5 border border-white/10 rounded-3xl p-8">

                        <h2 className="mb-8 text-2xl font-bold sm:text-3xl">
                            ❤️ Favorites
                        </h2>

                        {favorites.length === 0 ? (

                            <div className="text-center py-10">

                                <div className="text-6xl mb-4">
                                    ❤️
                                </div>

                                <h3 className="text-2xl font-bold mb-2">
                                    No Favorites Yet
                                </h3>

                                <p className="text-gray-400">
                                    Start adding your favorite movies, anime, games and TV shows.
                                </p>

                            </div>

                        ) : (

                            <div className="space-y-5">

                                {favorites.map(item => (

                                    <div
                                        key={item.id}
                                        className="flex flex-col gap-5 rounded-2xl border border-white/10 bg-slate-900 p-5 sm:flex-row sm:items-center sm:justify-between"
                                    >

                                        <div className="flex flex-col items-center gap-4 text-center sm:flex-row sm:text-left">

                                            <img
                                                src={`${API_BASE}${item.imageUrl}`}
                                                alt={item.itemTitle}
                                                className="w-16 h-24 rounded-lg object-cover"
                                            />

                                            <div>

                                                <h3 className="text-lg font-bold sm:text-xl">
                                                    {item.itemTitle}
                                                </h3>

                                                <span className="inline-block mt-2 px-3 py-1 rounded-full bg-violet-600/20 text-violet-300 text-sm">
                                                    {item.category}
                                                </span>

                                            </div>

                                        </div>

                                        <button
                                            onClick={() => navigate(`/items/${item.itemId}`)}
                                            className="bg-violet-600 hover:bg-violet-700 px-4 py-2 rounded-lg"
                                        >
                                            View Item →
                                        </button>

                                    </div>

                                ))}

                            </div>

                        )}

                    </div>
                    <div className="mt-10 bg-white/5 border border-white/10 rounded-3xl p-8">

                        <h2 className="mb-8 text-2xl font-bold sm:text-3xl">
                            Favorite Genres
                        </h2>

                        <div className="grid grid-cols-1 gap-5 md:grid-cols-2">

                            {favoriteCategories.map(category => (

                                <div
                                    key={category.category}
                                    className="bg-slate-900 rounded-2xl p-5 flex justify-between items-center"
                                >

                                    <div>

                                        <h3 className="text-xl font-bold">
                                            {category.category}
                                        </h3>

                                        <p className="text-gray-400">
                                            Favorite Category
                                        </p>

                                    </div>

                                    <span className="text-3xl font-black text-violet-400">
                                        {category.count}
                                    </span>

                                </div>

                            ))}

                        </div>

                    </div>
                </div>

            </div>

        </>
    );
}