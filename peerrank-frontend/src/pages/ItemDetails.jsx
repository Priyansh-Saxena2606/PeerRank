import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { getItem } from "../services/itemService";
import { API_BASE } from "../config";
import {
  getReviewsByItem,
  createReview,
  updateReview,
  deleteReview,
} from "../services/reviewService";
import {
  addFavorite,
  removeFavorite,
  isFavorite
} from "../services/favoriteService";
import { useAuth } from "../context/AuthContext";
import LoginModal from "../components/LoginModal";
import {
  ArrowLeft,
  Calendar,
  Film,
  Star,
  MessageSquare,
  Send,
} from "lucide-react";

export default function ItemDetails() {
  const { id } = useParams();
  const { isLoggedIn, username } = useAuth();


  const [showLogin, setShowLogin] = useState(false);

  const [item, setItem] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);

  const [rating, setRating] = useState(5);
  const [comment, setComment] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [editingReviewId, setEditingReviewId] = useState(null);
  const [editRating, setEditRating] = useState(5);
  const [editComment, setEditComment] = useState("");
  const [reviewToDelete, setReviewToDelete] = useState(null);
  const [favorite, setFavorite] = useState(false);
  const [favoriteLoading, setFavoriteLoading] = useState(false);

  useEffect(() => {
    fetchItem();
  }, [id]);

  async function fetchItem() {
    try {

      const itemData = await getItem(id);
      const reviewData = await getReviewsByItem(id);

      setItem(itemData);
      setReviews(reviewData.reverse());

      if (isLoggedIn) {
        const favoriteStatus = await isFavorite(id);
        setFavorite(favoriteStatus);
      }

    } catch (err) {

      console.error(err);

    } finally {

      setLoading(false);

    }
  }

  async function submitReview(e) {

    e.preventDefault();

    if (!comment.trim()) return;

    try {

      setSubmitting(true);

      const newReview = await createReview({
        itemId: Number(id),
        rating,
        comment,
      });

      setReviews(prev => [newReview, ...prev]);

      setComment("");
      setRating(5);

      fetchItem();

    } catch (err) {

      console.error(err);

      alert(
        err.response?.data?.message ||
        "Failed to submit review."
      );

    } finally {

      setSubmitting(false);

    }

  }
  function startEditing(review) {
    setEditingReviewId(review.id);
    setEditRating(review.rating);
    setEditComment(review.comment);
  }

  function cancelEditing() {
    setEditingReviewId(null);
    setEditRating(5);
    setEditComment("");
  }
  async function saveEditedReview(reviewId) {
    try {
      const updated = await updateReview(reviewId, {
        itemId: Number(id),
        rating: editRating,
        comment: editComment,
      });

      setReviews(prev =>
        prev.map(r =>
          r.id === reviewId
            ? { ...r, ...updated }
            : r
        )
      );

      cancelEditing();

    } catch (err) {
      console.error(err);
      alert("Failed to update review.");
    }
  }
  async function confirmDeleteReview() {
    try {
      await deleteReview(reviewToDelete);

      setReviews(prev =>
        prev.filter(r => r.id !== reviewToDelete)
      );

      setReviewToDelete(null);

    } catch (err) {
      console.error(err);
      alert("Failed to delete review.");
    }
  }
  async function toggleFavorite() {

    try {

      setFavoriteLoading(true);

      if (favorite) {

        await removeFavorite(id);
        setFavorite(false);

      } else {

        await addFavorite(id);
        setFavorite(true);

      }

    } catch (err) {

      console.error(err);

    } finally {

      setFavoriteLoading(false);

    }

  }

  if (loading)
    return (
      <div className="min-h-screen bg-[#0f172a] text-white flex items-center justify-center">
        Loading...
      </div>
    );

  const average =
    reviews.length === 0
      ? "0.0"
      : (
        reviews.reduce((s, r) => s + r.rating, 0) /
        reviews.length
      ).toFixed(1);

  return (
    <div className="min-h-screen bg-[#0f172a] text-white">

      {/* HERO */}

      <div className="relative overflow-hidden">

        <div className="absolute inset-0 bg-gradient-to-r from-purple-900 via-indigo-900 to-slate-900" />

        <div className="absolute inset-0 bg-black/20" />

        <div className="relative mx-auto max-w-7xl px-4 py-10 sm:px-6 sm:py-14 lg:px-8 lg:py-16">

          <Link
            to={`/categories/${item.categoryId}`}
            className="inline-flex items-center gap-2 text-purple-300 hover:text-white mb-8"
          >
            <ArrowLeft size={18} />
            Back
          </Link>

          <h1 className="mb-5 text-4xl font-black leading-tight sm:text-5xl lg:text-6xl">
            {item.title}
          </h1>

          <div className="flex flex-wrap gap-3 text-sm text-gray-200 sm:gap-6 sm:text-lg">

            <span className="flex items-center gap-2 font-bold">
              ⭐ {average}
            </span>

            <span>{item.categoryName}</span>

            <span>•</span>

            <span>{item.genre}</span>

            <span>•</span>

            <span>{item.releaseYear}</span>

          </div>

        </div>

      </div>

      {/* CONTENT */}

      <div className="mx-auto max-w-7xl px-4 py-8 sm:px-6 sm:py-10 lg:px-8 lg:py-12">

        <div className="grid gap-8 lg:grid-cols-[340px_1fr] lg:gap-12">

          {/* POSTER */}

          <div>

            <div className="overflow-hidden rounded-3xl border border-white/10 shadow-[0_25px_80px_rgba(139,92,246,0.35)] h-[420px] sm:h-[500px] lg:h-[520px]">

              <img
                src={`${API_BASE}${item.imageUrl}`}
                alt={item.title}
                className="w-full h-full object-cover"
                onError={(e) => {
                  e.target.src =
                    "https://placehold.co/400x600/1e293b/ffffff?text=No+Poster";
                }}
              />

            </div>

          </div>

          {/* RIGHT */}

          <div>

            <div className="mb-8 rounded-3xl border border-white/10 bg-white/5 p-5 sm:p-8">

              <h2 className="mb-5 text-2xl font-bold sm:text-3xl">
                Description
              </h2>

              <p className="text-gray-300 leading-8">
                {item.description}
              </p>

            </div>

            <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">

              <div className="bg-white/5 rounded-2xl border border-white/10 p-6">

                <Star
                  fill="currentColor"
                  className="text-yellow-400 mb-4"
                />

                <p className="text-gray-400">
                  Rating
                </p>

                <h2 className="text-4xl font-bold mt-2">
                  {average}
                </h2>

              </div>

              <div className="bg-white/5 rounded-2xl border border-white/10 p-6">

                <Film className="text-purple-400 mb-4" />

                <p className="text-gray-400">
                  Category
                </p>

                <h2 className="text-3xl font-bold mt-2">
                  {item.categoryName}
                </h2>

              </div>

              <div className="bg-white/5 rounded-2xl border border-white/10 p-6">

                <Calendar className="text-cyan-400 mb-4" />

                <p className="text-gray-400">
                  Released
                </p>

                <h2 className="text-3xl font-bold mt-2">
                  {item.releaseYear}
                </h2>

              </div>

            </div>
            <div className="mt-8">

              {isLoggedIn && (

                <button
                  onClick={toggleFavorite}
                  disabled={favoriteLoading}
                  className={`px-6 py-3 rounded-xl font-bold transition ${favorite
                    ? "bg-red-600 hover:bg-red-700"
                    : "bg-slate-700 hover:bg-slate-600"
                    }`}
                >

                  {favorite ? "❤️ Remove Favorite" : "🤍 Add to Favorites"}

                </button>

              )}

            </div>

          </div>

        </div>

        {/* REVIEW FORM */}

        <div className="mt-12 rounded-3xl border border-white/10 bg-white/5 p-5 sm:mt-20 sm:p-8">

          <h2 className="text-3xl font-bold mb-8">
            Write a Review
          </h2>

          {!isLoggedIn ? (

            <div className="text-center py-8">

              <p className="text-gray-400 mb-6 text-lg">
                Login to write a review and rate this item.
              </p>

              <button
                onClick={() => setShowLogin(true)}
                className="bg-purple-600 hover:bg-purple-700 px-8 py-3 rounded-xl font-bold transition"
              >
                Login to Continue
              </button>

            </div>

          ) : (

            <>
              <div className="mb-8 flex flex-wrap gap-3">

                {[1, 2, 3, 4, 5].map((s) => (
                  <button
                    key={s}
                    type="button"
                    onClick={() => setRating(s)}
                  >
                    <Star
                      size={34}
                      fill={s <= rating ? "currentColor" : "none"}
                      className={
                        s <= rating
                          ? "text-yellow-400"
                          : "text-gray-600"
                      }
                    />
                  </button>
                ))}

              </div>

              <form onSubmit={submitReview}>

                <textarea
                  rows={5}
                  value={comment}
                  onChange={(e) => setComment(e.target.value)}
                  placeholder="Share your experience..."
                  className="w-full bg-[#111827] rounded-2xl p-5 border border-white/10 outline-none focus:border-purple-500 resize-none"
                />

                <button
                  disabled={submitting}
                  className="mt-6 flex w-full items-center justify-center gap-2 rounded-xl bg-purple-600 px-8 py-3 font-bold hover:bg-purple-700 sm:w-auto"
                >
                  <Send size={18} />
                  {submitting ? "Submitting..." : "Submit Review"}
                </button>

              </form>
            </>

          )}

        </div>
        {/* REVIEWS */}

        <div className="mt-20">

          <div className="flex items-center gap-3 mb-8">

            <MessageSquare className="text-purple-400" />

            <h2 className="text-3xl font-bold">
              Reviews
            </h2>

            <span className="text-gray-400">
              ({reviews.length})
            </span>

          </div>

          <div className="space-y-5">

            {reviews.length === 0 ? (

              <div className="bg-white/5 border border-white/10 rounded-2xl p-8 text-center text-gray-400">

                No reviews yet.
                <br />
                Be the first one to review this!

              </div>

            ) : (

              reviews.map((review) => (

                <div
                  key={review.id}
                  className="bg-white/5 rounded-2xl border border-white/10 p-6 hover:border-purple-500 transition"
                >

                  <div className="mb-5 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">

                    <div>

                      <h3 className="font-bold text-lg flex items-center gap-2">
                        👤 {review.username || "Unknown User"}
                      </h3>

                      <p className="text-sm text-gray-400">
                        PeerRank Member
                      </p>

                    </div>

                    <div className="flex gap-1">

                      {Array.from({ length: review.rating }).map((_, i) => (

                        <Star
                          key={i}
                          size={18}
                          fill="currentColor"
                          className="text-yellow-400"
                        />

                      ))}

                    </div>

                  </div>

                  {editingReviewId === review.id ? (

                    <div className="space-y-5">

                      <div className="flex gap-2">

                        {[1, 2, 3, 4, 5].map((star) => (

                          <button
                            key={star}
                            type="button"
                            onClick={() => setEditRating(star)}
                          >
                            <Star
                              size={28}
                              fill={star <= editRating ? "currentColor" : "none"}
                              className={
                                star <= editRating
                                  ? "text-yellow-400"
                                  : "text-gray-600"
                              }
                            />
                          </button>

                        ))}

                      </div>

                      <textarea
                        rows={4}
                        value={editComment}
                        onChange={(e) => setEditComment(e.target.value)}
                        className="w-full bg-[#111827] rounded-xl p-4 border border-white/10"
                      />

                      <div className="flex flex-col gap-3 sm:flex-row">

                        <button
                          onClick={() => saveEditedReview(review.id)}
                          className="px-5 py-2 rounded-lg bg-green-600 hover:bg-green-700"
                        >
                          Save
                        </button>

                        <button
                          onClick={cancelEditing}
                          className="px-5 py-2 rounded-lg bg-gray-700 hover:bg-gray-600"
                        >
                          Cancel
                        </button>

                      </div>

                    </div>

                  ) : (

                    <p className="text-gray-300 leading-8">
                      {review.comment}
                    </p>

                  )}
                  {isLoggedIn &&
                    review.username === username &&
                    editingReviewId !== review.id && (

                      <div className="flex gap-3 mt-5">

                        <button
                          onClick={() => startEditing(review)}
                          className="px-4 py-2 rounded-lg bg-yellow-500 hover:bg-yellow-600 transition"
                        >
                          ✏️ Edit
                        </button>

                        <button
                          onClick={() => setReviewToDelete(review.id)}
                          className="px-4 py-2 rounded-lg bg-red-600 hover:bg-red-700 transition"
                        >
                          🗑 Delete
                        </button>

                      </div>

                    )}

                </div>

              ))

            )}

          </div>

        </div>



      </div>
      {reviewToDelete && (

        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50">

          <div className="mx-4 w-full max-w-md rounded-2xl border border-white/10 bg-[#1e293b] p-6 sm:p-8">

            <h2 className="text-2xl font-bold mb-4">
              Delete Review?
            </h2>

            <p className="text-gray-400 mb-8">
              This action cannot be undone.
            </p>

            <div className="flex justify-end gap-4">

              <button
                onClick={() => setReviewToDelete(null)}
                className="px-5 py-2 rounded-lg bg-gray-700 hover:bg-gray-600"
              >
                Cancel
              </button>

              <button
                onClick={confirmDeleteReview}
                className="px-5 py-2 rounded-lg bg-red-600 hover:bg-red-700"
              >
                Delete
              </button>

            </div>

          </div>

        </div>

      )}
      <LoginModal
        open={showLogin}
        onClose={() => setShowLogin(false)}
      />

    </div>
  );
}