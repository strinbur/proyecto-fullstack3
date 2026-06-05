import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";

import { Toaster } from "react-hot-toast";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <>
      <Toaster
        position="top-center"
        toastOptions={{
          duration: 3000,
          style: {
            background: "#1e3a8a",
            color: "#ffffff",
            borderRadius: "10px",
            padding: "16px",
            fontWeight: "600",
          },
        }}
      />

      <App />
    </>
  </React.StrictMode>
);