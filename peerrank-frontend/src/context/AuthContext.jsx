import { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {

    const [token, setToken] = useState(null);
    const [username, setUsername] = useState(null);

    useEffect(() => {

        const savedToken = localStorage.getItem("token");
        const savedUsername = localStorage.getItem("username");

        if (savedToken) {
            setToken(savedToken);
        }

        if (savedUsername) {
            setUsername(savedUsername);
        }

    }, []);

    function login(jwt, username) {

        localStorage.setItem("token", jwt);
        localStorage.setItem("username", username);

        setToken(jwt);
        setUsername(username);

    }

    function logout() {

        localStorage.removeItem("token");
        localStorage.removeItem("username");

        setToken(null);
        setUsername(null);

    }

    return (

        <AuthContext.Provider
            value={{
                token,
                username,
                isLoggedIn: !!token,
                login,
                logout
            }}
        >

            {children}

        </AuthContext.Provider>

    );

}

export function useAuth() {

    return useContext(AuthContext);

}