import { useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
//import { Socket } from "dgram";

interface Message {
  id: number;
  contents: string;
}

export const useWebSocket = (url: string, token: string) => {
  const stompClient = useRef<Client | null>(null);
  const [messages, setMessages] = useState<Message[]>([]);
  const [isConnected, setIsConnected] = useState(false);
  const [retryCount, setRetryCount] = useState(0); // 재시도 횟수 상태 추가
  const maxRetries = 5; // 최대 재시도 횟수 설정

  useEffect(() => {
    if (!token) {
      console.error("No JWT token provided");
      return;
    }

    let isStopped = false; // 재연결 중단 여부

    const connectWebSocket = () => {
      const client = new Client({
        webSocketFactory: () => new SockJS(`${url}?token=${token}`),
        reconnectDelay: 0,
        debug: (str) => console.log(str),

        onConnect: () => {
          setIsConnected(true);
          setRetryCount(0); // 재시도 카운터 초기화함.
          console.log("WebSocket connected");

          // 메세지 구독
          client.subscribe("user/queue/answers", (message) => {
            const parseedMessage: Message = JSON.parse(message.body);
            setMessages((prev) => [...prev, parseedMessage]);
          });
        },

        onDisconnect: () => {
          setIsConnected(false);
          console.log("WebSocket disconnected");
          handleReconnect();
        },

        onStompError: (error) => {
          console.error("STOMP error:", error);
        },

        onWebSocketClose: (event) => {
          setIsConnected(false);
          console.error("WebSocket connection closed:", event);
          handleReconnect();
        },
      });

      stompClient.current = client; // useRef가 Dom객체에 직접 참조하기 위한 훅이라는 것을 잊지 말 것.
      client.activate();
    };

    const handleReconnect = () => {
      if (isStopped) {
        console.log("Reconnect stopped. No further attempts.");
        return;
      }

      if (retryCount < maxRetries) {
        console.log(
          `Retrying WebSocket connection (${retryCount + 1}/${maxRetries})...`
        );
        setRetryCount((prev) => prev + 1);

        // 일정 시간 후 재연결 시도
        setTimeout(() => {
          if (!isStopped && retryCount < maxRetries) {
            connectWebSocket();
          }
        }, 5000);
      } else {
        console.error("Max retry attempts reached. Stopping reconnection.");
        isStopped = true; // 재연결 중단
        stompClient.current?.deactivate(); // WebSocket 클라이언트 비활성화
      }
    };

    connectWebSocket();

    return () => {
      isStopped = true;
      stompClient.current?.deactivate();
      setIsConnected(false);
    };
  }, [url, token, retryCount]);

  // 질문을 보내기 위한 함수.
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

  return { messages, sendMessage, isConnected, retryCount };
};
