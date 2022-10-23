import React from "react";
import "./Header.css";
import logo from '../assets/logo.svg';

export default function Header() {
    const token = localStorage.getItem('token');
    let role = "";
    if(localStorage.getItem("user")) {
         role = JSON.parse(localStorage.getItem("user")).role;
         console.log(role)
    }
    const logout = () => {
        localStorage.clear();
    }

    return (
        <header className="Header">
            <img src={logo} className="Logo" alt="logo" />

            <nav className="Nav">
                <h2 className="titleC">E-Auction</h2>

                <a href="/">Home</a>
                {token ? "" : <a href="/register">Register</a>}
                {token ? <a href="/" onClick={logout}>Logout</a> : <a href="/">Login</a>}
            </nav>

        </header>
    );
}