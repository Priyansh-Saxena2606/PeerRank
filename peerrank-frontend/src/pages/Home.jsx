import { useEffect, useState } from "react";
import LoginModal from "../components/LoginModal";
import Navbar from "../components/Navbar";
import StatCard from "../components/StatCard";
import TrendingCard from "../components/TrendingCard";
import ReviewCountCard from "../components/ReviewCountCard";
import SectionTitle from "../components/SectionTitle";

import { getDashboard } from "../services/dashboardService";
import { getTopRated, getMostReviewed } from "../services/reviewService";

import {
    FolderKanban,
    Film,
    MessageSquare,
    Users,
    Star,
} from "lucide-react";

export default function Home() {
    const [loginOpen, setLoginOpen] = useState(false);
    const [dashboard, setDashboard] = useState(null);
    const [topRated, setTopRated] = useState([]);
    const [mostReviewed, setMostReviewed] = useState([]);

    useEffect(() => {

        async function fetchDashboard() {

            try {

                const dashboardData = await getDashboard();
                const top = await getTopRated();
                const most = await getMostReviewed();

                setDashboard(dashboardData);
                setTopRated(top);
                setMostReviewed(most);

            } catch (err) {
                console.error(err);
            }

        }

        fetchDashboard();

    }, []);

    if (!dashboard)
        return (
            <div className="flex h-screen items-center justify-center bg-slate-950 text-white">
                Loading...
            </div>
        );

    return (

        <div className="min-h-screen bg-slate-950 text-white">

            <Navbar onLoginClick={() => setLoginOpen(true)} />

            <main className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">

                <section className="py-12 sm:py-16 lg:py-20">

                    <span className="rounded-full border border-violet-500/30 bg-violet-500/10 px-4 py-2 text-sm text-violet-300">
                        ★ Community Rankings
                    </span>

                    <h1 className="mt-6 text-4xl font-semibold leading-tight sm:mt-8 sm:text-5xl lg:text-6xl">
                        Discover.
                        <br />
                        <span className="text-violet-500">
                            Review.
                        </span>
                        <br />
                        Rank.
                    </h1>

                    <p className="mt-6 max-w-xl text-base text-slate-400 sm:text-lg">
                        Explore movies, games, anime and more.
                        Rate your favourites and discover what the community loves.
                    </p>

                </section>

                <section className="grid grid-cols-1 gap-6 sm:grid-cols-2 xl:grid-cols-4">

                    <StatCard
                        title="Categories"
                        value={dashboard.totalCategories}
                        icon={<FolderKanban size={34} />}
                    />

                    <StatCard
                        title="Items"
                        value={dashboard.totalItems}
                        icon={<Film size={34} />}
                    />

                    <StatCard
                        title="Reviews"
                        value={dashboard.totalReviews}
                        icon={<MessageSquare size={34} />}
                    />

                    <StatCard
                        title="Community Members"
                        value={dashboard.totalUsers}
                        icon={<Users size={34} />}
                    />

                </section>

                <SectionTitle title="🔥 Trending" />

                <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">

                    {topRated.slice(0, 6).map((item, index) => (

                        <TrendingCard
                            key={index}
                            item={item}
                        />

                    ))}

                </div>

                <div className="mt-12 sm:mt-16">
                    <SectionTitle title="🏆 Most Reviewed" />
                </div>

                <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">

                    {mostReviewed.slice(0, 6).map((item, index) => (

                        <ReviewCountCard
                            key={index}
                            item={item}
                        />

                    ))}

                </div>

            </main>

            <LoginModal
                open={loginOpen}
                onClose={() => setLoginOpen(false)}
            />

        </div>

    );

}