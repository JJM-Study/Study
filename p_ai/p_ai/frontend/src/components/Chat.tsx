import { useWebSocket } from "../hooks/useWebSocket";
import React, { useEffect, useState, useCallback } from "react";
import { useLoadInitialData } from "../hooks/useLoadInitialData";
import { QuestionWithAnswers } from "../types/types";

const Chat: React.FC = () => {
  const [input, setInput] = useState(""); // 사용자 입력 관리
  // 2025/01/28 주석
  // const [token, setToken] = useState<string | null>(
  //   localStorage.getItem("jwt")
  // );
  const [combineMessages, setCombineMessages] = useState<QuestionWithAnswers[]>(
    []
  ); // 화면에 표시할 메세지 리스트
  const [token, setToken] = useState<string | null>(
    localStorage.getItem("jwt")
  );
  const {
    messages: webSocketMessages,
    sendMessage,
    isConnected,
  } = useWebSocket("http://localhost:8080/chat-ws", token);
  // 초기 데이터를 가져오는 훅 호출
  const { initialMessages, isLoading } = useLoadInitialData();

  // WebSocket 메세지와 초기 메세지를 병합
  useEffect(() => {
    console.log("initial Message :", initialMessages);
    console.log("webSocket Message :", webSocketMessages);
    const questionMap = new Map(
      initialMessages.map((msg) => [msg.questionId, msg])
    );

    // ** WebSocketMessages 변환 처리 **
    const formattedWebSocketMessages = webSocketMessages.map((msg) => ({
      questionId: msg.questionId,
      questionContents:
        questionMap.get(msg.questionId)?.questionContents || "Unknown Question",
      answers: msg.answers,
    }));

    setCombineMessages([...initialMessages, ...formattedWebSocketMessages]);
  }, [initialMessages, webSocketMessages]);

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

  const validateToken = useCallback(async () => {
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
  }, [token]);

  useEffect(() => {
    validateToken();
  }, [validateToken]); // validateToken 의존성 추가

  const handleSend = () => {
    if (input.trim()) {
      sendMessage("/app/question", { contents: input });
      setInput("");
    }
  };

  if (isLoading) {
    <div>Loading...</div>;
  }

  return (
    <div className="flex flex-col items-center h-screen p-4 bg-gray-100">
      <div className="mb-4 text-center">
        {isConnected ? (
          <p className="text-green-500">Connected to Chat</p>
        ) : (
          <p className="text-red-500">Connecting to chat server...</p>
        )}
      </div>
      <div className="w-full p-4 overflow-y-auto bg-white shadow-md h-3/4">
        {combineMessages.map((msg, idx) => (
          <div key={idx} className="p-2 my-2 bg-gray-200 rounded-md">
            <p>
              <strong>Q:</strong> {msg.questionContents}
            </p>
            {msg.answers.map((answer, index) => (
              <p key={index}>
                <strong>A:</strong> {answer.answerContents}
              </p>
            ))}
          </div>
        ))}
      </div>
      {/* 질문 입력 */}
      <div className="flex items-center w-full mt-4">
        <input
          type="text"
          value={input}
          className="flex-grow p-2 border rounded-md"
          placeholder="Type your question"
          onChange={(e) => setInput(e.target.value)}
          disabled={!isConnected}
        />

        {/* 질문 전송 버튼 */}
        <button
          onClick={handleSend}
          className="px-4 py-2 ml-2 text-white bg-blue-500 rounded-md"
          disabled={!isConnected}
        >
          Send
        </button>
      </div>
    </div>
  );
};

export default Chat;
