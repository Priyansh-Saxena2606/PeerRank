import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import axios from "axios";
import { ArrowLeft, Calendar, Tag } from "lucide-react";

export default function CategoryItems() {
  const { id } = useParams();

  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchItems();
  }, [id]);

  async function fetchItems() {
    try {
      const res = await axios.get(
        `http://localhost:8080/items/category/${id}`
      );

      setItems(res.data.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-[#0f172a] text-white flex items-center justify-center">
        <h1 className="text-2xl font-semibold">Loading...</h1>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#0f172a] px-4 py-8 text-white sm:px-6 lg:px-8 lg:py-10">
      <div className="max-w-7xl mx-auto">

        <Link
          to="/categories"
          className="inline-flex items-center gap-2 text-purple-400 hover:text-purple-300 mb-8"
        >
          <ArrowLeft size={18} />
          Back to Categories
        </Link>

        <h1 className="mb-8 text-3xl font-bold sm:text-4xl lg:mb-10">
          Explore Items
        </h1>

        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">

          {items.map((item) => (
            <div
              key={item.id}
              className="bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl overflow-hidden hover:border-purple-500 hover:-translate-y-2 transition duration-300"
            >
              <div className="flex h-56 items-center justify-center bg-gradient-to-br from-purple-700 to-indigo-800 sm:h-64">

                {item.imageUrl ? (
                  <>
                    <img
                      src={`http://localhost:8080${item.imageUrl}`}
                      alt={item.title}
                      className="w-full h-full object-cover"
                      onError={(e) => {
                        e.target.style.display = "none";
                        e.target.nextSibling.style.display = "flex";
                      }}
                    />

                    <div className="hidden w-full h-full items-center justify-center text-7xl">
                      🎮
                    </div>
                  </>
                ) : (
                  <span className="text-7xl">🎬</span>
                )}

              </div>

              <div className="p-4 sm:p-5 lg:p-6">

                <h2 className="mb-3 truncate text-lg font-bold sm:text-xl">
                  {item.title}
                </h2>

                <div className="flex items-center gap-2 text-gray-400 text-sm mb-2">
                  <Tag size={15} />
                  {item.genre}
                </div>

                {!["Places", "Activities", "Technology"].includes(item.categoryName) && (
                  <div className="flex items-center gap-2 text-gray-400 text-sm mb-5">
                    <Calendar size={15} />
                    {item.releaseYear}
                  </div>
                )}

                <Link
                  to={`/items/${item.id}`}
                  className="block w-full rounded-lg bg-purple-600 py-2 text-center font-semibold transition hover:bg-purple-700"
                >
                  View Details
                </Link>

              </div>
            </div>
          ))}

        </div>
      </div>
    </div>
  );
}