import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

import Home from "./pages/Home/Home";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import Dashboard from "./pages/Dashboard/Dashboard";
import Profile from "./pages/Profile/Profile";
import Products from "./pages/Products/Products";
import Cart from "./pages/Cart/Cart";

import { AuthProvider } from "./features/auth/AuthProvider";
import { CartProvider } from "./features/cart/CartContext";

function App() {

  return (

    <AuthProvider>
      <CartProvider>
        <BrowserRouter>

        <div
          style={{
            minHeight: "100vh",
            display: "flex",
            flexDirection: "column",
          }}
        >

          <Navbar />

          <div style={{ flex: 1 }}>

            <Routes>

              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/products" element={<Products />} />
              <Route path="/cart" element={<Cart />} />

            </Routes>

          </div>

          <Footer />

        </div>

        </BrowserRouter>
      </CartProvider>
    </AuthProvider>

  );

}

export default App;