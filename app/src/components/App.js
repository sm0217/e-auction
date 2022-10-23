import './App.css';
import Product from './Product'
import Header from './Header';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import React from 'react';
import Login from './Login';
import useToken from './useToken';
import AddProductForm from './AddProduct';
import RegisterUserForm from './RegisterUser';
import PlaceBid from './PlaceBid';

function App() {
    const { setToken } = useToken();
    if (!localStorage.getItem('token')) {
        return (
            <div className="App">
                <Header />
                {/* <Login  setToken={setToken}/> */}
                <BrowserRouter>
                <Routes>
                    <Route path="/register" element={<RegisterUserForm />} />
                    <Route path="/Login" element={<Login setToken={setToken}/>}  />
                    <Route path="/" element={<Login setToken={setToken}/>} />
                </Routes>
            </BrowserRouter>
            </div>
        )
    }

    return (
        <div className="App">
            <Header />
            <BrowserRouter>
                <Routes>
                    <Route path="/products" element={<Product />} />
                    <Route path="/add-product" element={<AddProductForm />} />
                    <Route path="/register" element={<RegisterUserForm />} />
                    <Route path="/place-bid" element={<PlaceBid/>} />
                    <Route path="/" element={<Product />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
