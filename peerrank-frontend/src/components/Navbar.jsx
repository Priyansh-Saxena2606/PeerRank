import { Link, useNavigate } from "react-router-dom";
import { Search, Star, Menu, X } from "lucide-react";
import { useEffect, useState } from "react";
import api from "../api/axios";
import { useAuth } from "../context/AuthContext";

export default function Navbar({ onLoginClick }) {
  const navigate = useNavigate();
  const { isLoggedIn, username, logout } = useAuth();

  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  useEffect(() => {
    if (!query.trim()) {
      setResults([]);
      return;
    }

    const timer = setTimeout(() => {
      searchItems();
    }, 300);

    return () => clearTimeout(timer);
  }, [query]);

  async function searchItems() {
    try {
      const res = await api.get(
        `/items/search?query=${encodeURIComponent(query)}`
      );

      setResults(res.data.data);
    } catch (err) {
      console.error(err);
      setResults([]);
    }
  }

  function openItem(id) {
    navigate(`/items/${id}`);
    setQuery("");
    setResults([]);
    setMobileMenuOpen(false);
  }

  function closeMenu() {
    setMobileMenuOpen(false);
  }
  return (
    <nav className="sticky top-0 z-50 border-b border-slate-800 bg-slate-950/70 backdrop-blur-lg">

      <div className="mx-auto flex max-w-7xl items-center justify-between px-4 py-4 sm:px-6 lg:px-8">

        <Link
          to="/"
          className="flex items-center gap-2 text-xl font-bold text-white sm:text-2xl"
        >
          <Star
            size={20}
            className="fill-violet-500 text-violet-500"
          />
          PeerRank
        </Link>

        <div className="hidden items-center gap-8 md:flex">

          <Link
            to="/"
            className="text-slate-300 transition hover:text-violet-400"
          >
            Home
          </Link>

          <Link
            to="/categories"
            className="text-slate-300 transition hover:text-violet-400"
          >
            Categories
          </Link>

          {!isLoggedIn ? (
            <button
              onClick={onLoginClick}
              className="rounded-lg bg-violet-600 px-5 py-2 font-semibold hover:bg-violet-700"
            >
              Login
            </button>
          ) : (
            <>
              <button
                onClick={() => navigate("/profile")}
                className="font-semibold text-violet-400 transition hover:text-violet-300"
              >
                👤 {username}
              </button>

              <button
                onClick={() => {
                  logout();
                  navigate("/");
                }}
                className="rounded-lg border border-red-500 px-4 py-2 text-red-400 transition hover:bg-red-500 hover:text-white"
              >
                Logout
              </button>
            </>
          )}
        </div>

        <button
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          className="rounded-lg p-2 text-white md:hidden"
        >
          {mobileMenuOpen ? <X size={24} /> : <Menu size={24} />}
        </button>

      </div>

      {mobileMenuOpen && (
        <div className="border-t border-slate-800 bg-slate-950 md:hidden">

          <div className="space-y-2 px-4 py-4">

            <Link
              to="/"
              onClick={closeMenu}
              className="block rounded-lg px-3 py-2 text-slate-300 hover:bg-slate-800"
            >
              Home
            </Link>

            <Link
              to="/categories"
              onClick={closeMenu}
              className="block rounded-lg px-3 py-2 text-slate-300 hover:bg-slate-800"
            >
              Categories
            </Link>

            {!isLoggedIn ? (
              <button
                onClick={() => {
                  closeMenu();
                  onLoginClick();
                }}
                className="w-full rounded-lg bg-violet-600 py-2 font-semibold"
              >
                Login
              </button>
            ) : (
              <>
                <button
                  onClick={() => {
                    navigate("/profile");
                    closeMenu();
                  }}
                  className="block w-full rounded-lg border border-slate-700 px-3 py-2 text-left text-violet-400"
                >
                  👤 {username}
                </button>

                <button
                  onClick={() => {
                    logout();
                    navigate("/");
                    closeMenu();
                  }}
                  className="mt-2 w-full rounded-lg border border-red-500 py-2 text-red-400 hover:bg-red-500 hover:text-white"
                >
                  Logout
                </button>
              </>
            )}

          </div>

        </div>
      )}

      <div className="border-t border-slate-800 bg-slate-950/60">
        <div className="mx-auto max-w-7xl px-4 py-3 sm:px-6 lg:px-8">

          <div className="relative">

            <Search
              size={18}
              className="absolute left-4 top-3.5 text-slate-400"
            />

            <input
              type="text"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              placeholder="Search movies, anime, games..."
              className="w-full rounded-full border border-slate-700 bg-slate-900 py-2.5 pl-11 pr-4 text-white outline-none focus:border-violet-500"
            />

            {results.length > 0 && (
              <div className="absolute left-0 right-0 z-50 mt-2 overflow-hidden rounded-xl border border-slate-700 bg-slate-900 shadow-2xl">

                {results.map((item) => (
                  <button
                    key={item.id}
                    onClick={() => openItem(item.id)}
                    className="block w-full border-b border-slate-700 px-4 py-3 text-left hover:bg-violet-600 last:border-none"
                  >
                    <div className="font-medium">
                      {item.title}
                    </div>

                    <div className="text-xs text-slate-400">
                      {item.categoryName}
                    </div>
                  </button>
                ))}

              </div>
            )}

          </div>

        </div>
      </div>
    </nav>
  );
}
