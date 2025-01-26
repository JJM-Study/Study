import { useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

interface Message {
  id: number;
  contents: string;
}

export const useWebSocket = (url: string, token: string | null) => {
  const stompClient = useRef<Client | null>(null);
  const retryCountRef = useRef(0); // retryCount를 ref로 관리
  const isStoppedRef = useRef(false); // isStopped를 ref로 관리
  const [messages, setMessages] = useState<Message[]>([]);
  const [isConnected, setIsConnected] = useState(false);
  const maxRetries = 5;

  const connectWebSocket = () => {
    const client = new Client({
      webSocketFactory: () => new SockJS(`${url}?token=${token}`),
      reconnectDelay: 0,
      debug: (str) => console.log(str),
      onConnect: () => {
        setIsConnected(true);
        retryCountRef.current = 0; // 성공 시 retryCount 초기화
        console.log("WebSocket connected");

        client.subscribe("user/queue/answers", (message) => {
          const parsedMessage: Message = JSON.parse(message.body);
          setMessages((prev) => [...prev, parsedMessage]);
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

    stompClient.current = client;
    client.activate();
  };

  const handleReconnect = () => {
    if (isStoppedRef.current) {
      console.log("Reconnect stopped. No further attempts.");
      return;
    }

    if (retryCountRef.current < maxRetries) {
      retryCountRef.current += 1;
      console.log(
        `Retrying WebSocket connection (${retryCountRef.current}/${maxRetries})...`
      );

      setTimeout(() => {
        if (!isStoppedRef.current) {
          connectWebSocket();
        }
      }, 5000); // 5초 후 재연결
    } else {
      console.error("Max retry attempts reached. Stopping reconnection.");
      isStoppedRef.current = true;
      stompClient.current?.deactivate();
    }
  };

  useEffect(() => {
    if (!token) {
      console.error("No JWT token provided");
      return;
    }

    isStoppedRef.current = false; // 시작 시 재연결 가능 상태로 초기화
    connectWebSocket();

    return () => {
      isStoppedRef.current = true;
      stompClient.current?.deactivate();
      setIsConnected(false);
    };
  }, [url, token]);

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
