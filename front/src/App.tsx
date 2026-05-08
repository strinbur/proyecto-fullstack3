import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import Home from "./pages/Home/Home";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import Profile from "./pages/Profile/Profile";
// 🔵 Importa la nueva página
import AboutPage from "./pages/About/AboutPage"; 

import { AuthProvider } from "./features/auth/AuthProvider";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>

        <Navbar />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          
          {/* 🔵 NUEVA RUTA: NOSOTROS */}
          <Route path="/about" element={<AboutPage />} />

          {/* 🟡 PROFILE */}
          <Route path="/profile" element={<Profile />} />
        </Routes>

      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;