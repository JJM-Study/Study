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

        client.subscribe("/user/queue/answers", (message) => {
          const parsedMessage: AnswerMessage = JSON.parse(message.body); // 메세지 파싱 후 단일 지정

          // 질문-답변 데이터 그룹핑
          setMessages((prev) => {
            const existingQuestion = prev.find(
              (q) => q.questionId === parsedMessage.questionId
            );

            // 질문 존재 시 answers 배열에 추가.
            if (existingQuestion) {
              return prev.map((q) =>
                q.questionId === parsedMessage.questionId
                  ? {
                      ...q,
                      answers: [
                        ...q.answers,
                        {
                          answerId: parsedMessage.id,
                          answerContents: parsedMessage.contents,
                        },
                      ],
                    }
                  : q
              );
            } else {
              // 기존 질문이 없다면 새 질문 추가
              // (일반적인 HTML과 다르게 새로고침 등으로 끊기면, 데이터를 새로 불러오는 게 아니라 리엑트 자체에서 따로 관리하는 걸 불러오므로, 그에 대한 기억 공간 필요.)
              const newQuestion: QuestionWithAnswers = {
                questionId: parsedMessage.questionId,
                questionContents: "Unknown Question",
                answers: [
                  {
                    answerId: parsedMessage.id,
                    answerContents: parsedMessage.contents,
                  },
                ],
              };

              return [...prev, newQuestion];
            }
          });
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
