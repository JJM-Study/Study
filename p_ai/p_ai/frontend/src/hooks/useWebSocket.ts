import { useCallback, useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { AnswerMessage, QuestionWithAnswers } from "../types/types";

// //2025-01-29 주석
//  interface AnswerMessage {
//    id: number;
//    contents: string;
//    questionId: number;
// }

export const useWebSocket = (url: string, token: string | null) => {
  const stompClient = useRef<Client | null>(null);
  //const retryCountRef = useRef(0); // retryCount를 ref로 관리
  //const isStoppedRef = useRef(false); // isStopped를 ref로 관리
  const [messages, setMessages] = useState<QuestionWithAnswers[]>([]);
  const [isConnected, setIsConnected] = useState(false);
  //const maxRetries = 5;

  const connectWebSocket = useCallback(() => {
    const client = new Client({
      webSocketFactory: () => new SockJS(`${url}?token=${token}`),
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
      onConnect: () => {
        setIsConnected(true);
        //retryCountRef.current = 0; // 성공 시 retryCount 초기화 2025/01/29 주석
        console.log("WebSocket connected");

        client.subscribe("/user/queue/question/confirmation", (message) => {
          const newQuestion = JSON.parse(message.body);
          console.log("Original WebSocket Message:", newQuestion);
          console.log(
            "formatted Data :",
            `questionId: ${newQuestion.id}`,
            `questionContents ${newQuestion.contents}`
          );

          setMessages((prev) => [
            ...prev,
            {
              questionId: newQuestion.id,
              questionContents: newQuestion.contents,
              answers: newQuestion.answers || [],
            },
          ]);
          console.log("WebSocket에서 받은 question/confirmation:", newQuestion);
        });

        client.subscribe("/user/queue/answers", (message) => {
          const newAnswer: AnswerMessage = JSON.parse(message.body);

          setMessages((prev) =>
            prev.map((q) =>
              q.questionId === newAnswer.questionId
                ? {
                    ...q,
                    answers: [
                      ...q.answers,
                      {
                        answerId: newAnswer.id,
                        answerContents: newAnswer.contents,
                      },
                    ],
                  }
                : q
            )
          );
        });
      },
      onDisconnect: () => {
        setIsConnected(false);
        console.log("WebSocket disconnected");
        // handleReconnect();
      },
      onStompError: (error) => {
        console.error("STOMP error:", error);
      },
      onWebSocketClose: (event) => {
        setIsConnected(false);
        console.error("WebSocket connection closed:", event);
        // handleReconnect();
      },
    });
    stompClient.current = client;
    client.activate();
  }, [token, url]);

  // 20225/01/30 주석 . recoonect는 쓰지 않아도 될 듯하다.
  // const handleReconnect = () => {
  //   if (isStoppedRef.current) {
  //     console.log("Reconnect stopped. No further attempts.");
  //     return;
  //   }

  // if (retryCountRef.current < maxRetries) {
  //   retryCountRef.current += 1;
  //   console.log(
  //     `Retrying WebSocket connection (${retryCountRef.current}/${maxRetries})...`
  //   );

  //   setTimeout(() => {
  //     if (!isStoppedRef.current) {
  //       connectWebSocket();
  //     }
  //   }, 5000); // 5초 후 재연결
  // } else {
  //   console.error("Max retry attempts reached. Stopping reconnection.");
  //   isStoppedRef.current = true;
  //   stompClient.current?.deactivate();
  // }

  useEffect(() => {
    if (!token) {
      console.error("No JWT token provided");
      return;
    }

    // isStoppedRef.current = false; // 시작 시 재연결 가능 상태로 초기화
    connectWebSocket();

    return () => {
      //isStoppedRef.current = true;
      stompClient.current?.deactivate();
      setIsConnected(false);
    };
  }, [connectWebSocket, token]);

  const sendMessage = (destination: string, payload: any) => {
    if (stompClient.current?.connected) {
      stompClient.current.publish({
        destination,
        body: JSON.stringify(payload),
      });
    } else {
      console.error("WebSocket is not connected.");
    }
  };

  return { messages, sendMessage, isConnected };
};
