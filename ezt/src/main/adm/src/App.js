import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Navi from "./components/Navi";
import Footer from "./components/Footer";
import Main from "./page/main";
function App() {
  return (
    <Router>
      <Header />
      <Navi />
      <div>
        <main>
          <Main />
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
