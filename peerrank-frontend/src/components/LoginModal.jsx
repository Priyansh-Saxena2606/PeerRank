import { useState } from "react";
import api from "../api/axios";
import { X } from "lucide-react";
import { useAuth } from "../context/AuthContext";

export default function LoginModal({ open, onClose }) {

    const { login } = useAuth();

    const [isRegister, setIsRegister] = useState(false);

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState(false);

    if (!open) return null;

    async function handleSubmit(e) {

        e.preventDefault();

        try {

            setLoading(true);

            if (isRegister) {

                await api.post(
                    "/auth/register",
                    {
                        username,
                        email,
                        password
                    }
                );

                alert("Account created successfully! Please login.");

                setIsRegister(false);
                setUsername("");
                setPassword("");

                return;
            }

            const res = await api.post(
                "/auth/login",
                {
                    email,
                    password
                }
            );

            login(
                res.data.token,
                res.data.username
            );

            onClose();

        } catch (err) {

            console.error(err);

            alert(
                err.response?.data?.message ||
                (isRegister
                    ? "Registration Failed"
                    : "Login Failed")
            );

        } finally {

            setLoading(false);

        }

    }

    return (

        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 p-4 backdrop-blur-sm">

            <div className="w-full max-w-md rounded-3xl border border-white/10 bg-slate-900 p-5 sm:p-8">

                <div className="mb-8 flex items-center justify-between">

                    <h2 className="text-2xl font-bold text-white sm:text-3xl">

                        {isRegister ? "Create Account" : "Login"}

                    </h2>

                    <button onClick={onClose}>

                        <X className="text-white" />

                    </button>

                </div>

                <form onSubmit={handleSubmit}>

                    {isRegister && (

                        <input
                            className="mb-4 w-full rounded-xl bg-slate-800 p-4 text-white outline-none"
                            placeholder="Username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />

                    )}

                    <input
                        className="mb-4 w-full rounded-xl bg-slate-800 p-3.5 text-white outline-none transition focus:ring-2 focus:ring-violet-500 sm:p-4"
                        placeholder="Email"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />

                    <input
                        type="password"
                        className="mb-6 w-full rounded-xl bg-slate-800 p-3.5 text-white outline-none transition focus:ring-2 focus:ring-violet-500 sm:p-4"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />

                    <button
                        disabled={loading}
                        className="w-full rounded-xl bg-violet-600 py-3 font-bold text-white transition hover:bg-violet-700 disabled:cursor-not-allowed disabled:opacity-70"
                    >

                        {loading
                            ? (isRegister ? "Creating Account..." : "Logging in...")
                            : (isRegister ? "Create Account" : "Login")}

                    </button>

                </form>

                <div className="mt-6 text-center text-xs text-slate-400 sm:text-sm">

                    {isRegister
                        ? "Already have an account?"
                        : "Don't have an account?"}

                    <button
                        onClick={() => {

                            setIsRegister(!isRegister);

                            setUsername("");
                            setEmail("");
                            setPassword("");

                        }}
                        className="ml-2 font-semibold text-violet-400 hover:text-violet-300"
                    >

                        {isRegister ? "Login" : "Create one"}

                    </button>

                </div>

            </div>

        </div>

    );

}