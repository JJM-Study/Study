import React from "react";
import "./App.css";
import Chat from "./components/Chat";

const App: React.FC = () => {
  return (
    <div className="flex items-center justify-center w-screen h-screen bg-grey-200">
      <Chat />
    </div>
  );
};

export default App;
