import { useWebSocket } from "../hooks/useWebSocket";
import React, { useEffect, useState } from "react";

const Chat: React.FC = () => {
  const [input, setInput] = useState("");
  const [token, setToken] = useState<string | null>(
    localStorage.getItem("jwt")
  );

  const authenticate = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/authenticate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: "user" }),
      });
      const data = await response.json();
      const newToken = data.token;

      if (newToken) {
        localStorage.setItem("jwt", newToken);
        setToken(newToken);
      } else {
        console.error("Failed to fetch JWT token.");
      }
    } catch (error) {
      console.error("Error during authentication", error);
    }
  };

  const validateToken = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/validate-token", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        console.error("Invalid or expired token. Reauthenticating...");
        await authenticate();
      } else {
        console.log("Token is valid");
      }
    } catch (error) {
      console.error("Invalid or expired token, re-authenticating...", error);
      await authenticate();
    }
  };

  useEffect(() => {
    validateToken();
  }, [validateToken]); // validateToken 의존성 추가

  const { messages, sendMessage, isConnected } = useWebSocket(
    "http://localhost:8080/chat-ws",
    token
  );

  const handleSend = () => {
    if (input.trim()) {
      sendMessage("/app/question", { contents: input });
      setInput("");
    }
  };

  return (
    <div className="flex flex-col items-center h-screen p-4 bg-gray-100">
      <div className="text-center mb-4">
        {isConnected ? (
          <p className="text-green-500">Connected to Chat</p>
        ) : (
          <p className="text-red-500">Connecting to chat server...</p>
        )}
      </div>
      <div className="w-full p-4 overflow-y-auto bg-white shadow-md h-3/4">
        {messages.map((msg, idx) => (
          <div key={idx} className="p-2 bg-gray-200 my-2 rounded-md">
            <p>{msg.contents}</p>
          </div>
        ))}
      </div>
      <div className="flex items-center w-full mt-4">
        <input
          type="text"
          value={input}
          className="flex-grow p-2 border rounded-md"
          placeholder="Type your question"
          onChange={(e) => setInput(e.target.value)}
          disabled={!isConnected}
        />
        <button
          onClick={handleSend}
          className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-md"
          disabled={!isConnected}
        >
          Send
        </button>
      </div>
    </div>
  );
};

export default Chat;
