import { useWebSocket } from "../hooks/useWebSocket";
import React, {
  useEffect,
  useState,
  useCallback,
  useRef,
  useLayoutEffect,
} from "react";
import { useLoadInitialData } from "../hooks/useLoadInitialData";
//import { useFetchTrainedQandA } from "../hooks/useFetchTrainedQandA"; // 2025/02/24 추가
import { QuestionWithAnswers } from "../types/types";
import QuestionList from "./QuestionList";
import StatusIndicator from "./StatusIndicator";
import { useTraining } from "../context/TrainingProvider"; // 2025/02/25 추가

const Chat: React.FC = () => {
  const inputRef = useRef<HTMLInputElement | null>(null); // 2025/02/28 추가
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
  } = useWebSocket("http://localhost:8080/chat-ws", token); // 로컬
  //} = useWebSocket("https://web-pj.com/chat-ws", token); // CLOUDFRONT 배포
  // const {
  //   loadTrainedQandA,
  //   isLoading: FIsLoading,
  //   fetchTrainedQandA,
  // } = useFetchTrainedQandA(); // 2025/02/24 추가

  // 초기 데이터를 가져오는 훅 호출
  const { initialMessages, isLoading } = useLoadInitialData();

  //const { fetchTrainedQandA, loadTrainedQandA } = useFetchTrainedQandA();
  const { loadTrainedQandA, isLoading: isTrainedLoading } = useTraining(); // 2025/02/25 추가

  // 스크롤 감지 및 이동 2025/02/22 추가
  const messageEndRef = useRef<HTMLDivElement | null>(null);

  // 2025/02/28 추가
  const readMe = [
    {
      role: "system",
      text: "이 프로젝트는 AWS 배포 및 웹 백엔드, 풀스택을 설계를 중심으로 구현되었습니다.",
    },
    {
      role: "system",
      text: "현재 AI 모델의 학습 데이터가 제한적이므로, 특정 질문에만 정확한 답변을 제공합니다.",
    },
    {
      role: "system",
      text: "지원하는 질문 목록을 확인하려면 질문 리스트를 참고하시면 되며, 질문을 클릭하면 해당 질문이 바로 입력됩니다.",
    },
  ];

  // 질문 목록 클릭 시 포커스 이동 , 2025/03/28 추가
  const handleQuestionClick = (text: string) => {
    setInput(text);
    inputRef.current?.focus();
  };

  // Enter 입력 처리 추가 , 2025/02/28 추가
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      if (event.shiftKey) {
        setInput((prev) => prev + "\n");
      } else {
        event.preventDefault();
        handleSend();
      }
    }
  };

  // // 토큰 만료되면 데이터 refresh 하는 것 추가 고려.
  // // 2025/02/24 추가 // 나중에 서버 응답이 실패하면 토큰 재발급 받는 로직 추가할 것.
  // useEffect(() => {
  //   if (token && isConnected) {
  //     setTimeout(() => {
  //       validateToken().then(() => fetchTrainedQandA()); // 비동기 함수 // 토큰 검증 후 실행.
  //     }, 100);
  //   }
  // }, [isConnected, token]);

  // // 2025/02/20 추가
  // useEffect(() => {
  //   const chgLocalStorage = () => {
  //     setToken(localStorage.getItem("jwt"));
  //   };

  //   window.addEventListener("storage", chgLocalStorage);
  //   return () => {
  //     window.removeEventListener("storage", chgLocalStorage);
  //   };
  // }, []);

  // 2025/02/24 추가
  // useEffect(() => {
  //   const checkAndFetchData = async () => {
  //     if (!token) {
  //       console.error("JWT가 없습니다. 데이터를 불러올 수 없습니다.");
  //       return;
  //     }
  //     console.log("토큰 검증 시작...");
  //     await validateToken(); // 토큰 검증 및 재발급
  //     console.log("토큰 발급 완료.");
  //     await fetchTrainedQandA(); // 최신 토큰으로 데이터 요청
  //     console.log("fetchTrainedQandA 실행 완료");
  //   };

  //   checkAndFetchData();
  // }, [token, isConnected]);

  // 2025/02/24 추가
  useEffect(() => {
    const chgLocalStorage = () => {
      setToken(localStorage.getItem("jwt"));
    };

    window.addEventListener("storage", chgLocalStorage);
    return () => {
      window.removeEventListener("storage", chgLocalStorage);
    };
  }, []);

  // WebSocket 메세지와 초기 메세지를 병합
  useEffect(() => {
    setCombineMessages((prev) => {
      const questionMap = new Map(prev.map((msg) => [msg.questionId, msg]));

      // 기존 질문 유지 + 새로운 초기 데이터 반영
      initialMessages.forEach((msg) => {
        questionMap.set(msg.questionId, msg);
      });

      // WebSocket에서 받은 메시지도 반영
      webSocketMessages.forEach((msg) => {
        //console.log("questionId : " + msg.questionId);
        questionMap.set(msg.questionId, {
          questionId: msg.questionId,
          questionContents:
            msg.questionContents ||
            questionMap.get(msg.questionId)?.questionContents ||
            "Unknown Question",
          answers: msg.answers || [],
        });
      });

      return Array.from(questionMap.values());
    });
  }, [initialMessages, webSocketMessages]);

  // --- 2025/02/22 / 추가 스크롤 자동 이동. ---

  useLayoutEffect(() => {
    requestAnimationFrame(() => {
      setTimeout(() => {
        const container = messageEndRef.current?.parentElement;
        if (!container) return;

        // 현재 스크롤이 맨 아래에 있는지 확인
        const isAtBottom =
          container.scrollHeight -
            container.scrollTop -
            container.clientHeight <
          100;

        if (isAtBottom) {
          // 부드러운 스크롤을 시도하되, 실패하면 강제로 이동
          messageEndRef.current?.scrollIntoView({ behavior: "smooth" });

          setTimeout(() => {
            // 만약 scrollIntoView가 이상하게 동작하면 강제 이동
            container.scrollTop = container.scrollHeight;
          }, 500); // 애니메이션 후 보정
        }
      }, 50); // DOM 업데이트 후 실행
    });
  }, [combineMessages]);

  useLayoutEffect(() => {
    const container = messageEndRef.current?.parentElement;
    if (!container) return;

    // 초기에만 스크롤을 이동하도록 조건 추가
    if (container.scrollHeight <= container.clientHeight) {
      messageEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }
  }, []);

  // --------

  const authenticate = async () => {
    try {
      // 로컬
      const response = await fetch("http://localhost:8080/api/authenticate", {
        // CLOUDFRONT 배포
        //const response = await fetch("https://web-pj.com/api/authenticate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: null }), // 2025/02/13 user -> null 수정. 현재는 Guest모드로, 차후 로그인 구현 시 수정할 것.
      });
      const data = await response.json();
      //debugger;
      const newToken = data.token;

      if (newToken) {
        localStorage.setItem("jwt", newToken);
        setToken(newToken);
      } else {
        //console.error("Failed to fetch JWT token.");
      }
    } catch (error) {
      //console.error("Error during authentication", error);
    }
  };

  let isAuthenticating = false; //2025/02/25 추가. 중복 인증 방지.

  const validateToken = useCallback(async () => {
    if (isAuthenticating) return;
    isAuthenticating = true;
    try {
      // 로컬
      const response = await fetch("http://localhost:8080/api/validate-token", {
        // CLOUDFRONT 배포
        //const response = await fetch("https://web-pj.com/api/validate-token", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        //console.error("Invalid or expired token. Reauthenticating...");
        await authenticate();
      } else {
        //console.log("Token is valid");
      }
    } catch (error) {
      //console.error("Invalid or expired token, re-authenticating...", error);
      await authenticate();
    } finally {
      isAuthenticating = false;
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
    <div className="flex flex-col items-center w-full h-screen p-4 bg-gray-100">
      {/* 헤더 */}
      <div className="flex items-center justify-between w-full p-1 bg-white shadow-md">
        <h1 className="pl-1 text-lg font-bold">ChatBot</h1>
        <div className="pr-1 text-green-500">
          <StatusIndicator isConnected={isConnected} />
        </div>
      </div>

      {/* 학습된 질문 목록 */}
      {/* <QuestionList onQuestionClick={(text) => setInput(text)} /> 2025/02/28 수정*/}
      <QuestionList
        onQuestionClick={(text) => {
          //setInput(text);
          handleQuestionClick(text);
        }}
      />

      {/* 질문 목록 . 나중에 textarea로 바꿀 것. input은 줄바꿈 x.*/}
      <div className="w-full h-screen p-4 mt-2 overflow-y-auto bg-white shadow-md">
        {/* 알림 추가 2025/02/28 */}
        {readMe.map((msg, index) => (
          <div className="p-2 my-2 font-medium text-blue-600 bg-blue-100 rounded-md">
            <p key={index}>{msg.text}</p>
          </div>
        ))}

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
        <div ref={messageEndRef} />
      </div>
      {/* 질문 입력 */}
      <div className="flex items-center w-full mt-4">
        <input
          ref={inputRef}
          type="text"
          value={input}
          className="flex-grow p-2 border rounded-md"
          placeholder="Type your question"
          onChange={(e) => setInput(e.target.value)}
          disabled={!isConnected}
          onKeyDown={handleKeyDown}
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
