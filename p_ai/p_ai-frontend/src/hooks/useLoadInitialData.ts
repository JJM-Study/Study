import { useEffect, useState } from "react";
import { QuestionWithAnswers } from "../types/types"; // 2025/01/28 추가

export const useLoadInitialData = () => {
  //const [initialMessages, setinitialMessages] = useState<>([]);
  const [initialMessages, setInitialMessages] = useState<QuestionWithAnswers[]>(
    []
  ); // 2025/01/28 수정
  const [isLoading, setIsLoading] = useState(true);
  const [jwt, setJwt] = useState<string | null>(localStorage.getItem("jwt")); // 2025/02/20 추가

  // 2025/02/20 추가
  useEffect(() => {
    const chgLocalStorage = () => {
      setJwt(localStorage.getItem("jwt"));
    };

    window.addEventListener("storage", chgLocalStorage);
    return () => {
      window.addEventListener("sotrage", chgLocalStorage);
    };
  }, []);

  useEffect(() => {
    const fetchinitialData = async () => {
      try {
        // 2025/02/20 추가
        if (!jwt) {
          return;
        }

        // axios로 바꿀 지는 나중에 고민. / Next.js를 학습할 일이 있으면?
        const response = await fetch(
          // 로컬
          "http://localhost:8080/api/Question-And-Answer",
          //"http://54.180.107.241:8080/api/Question-And-Answer", // CLOUDFRONT 배포
          {
            method: "GET",
            headers: {
              //Authorization: `Bearer ${localStorage.getItem("jwt")}`,
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
            credentials: "include",
          }
        );
        if (response.ok) {
          const data = await response.json(); // Message 단일 지정
          // 질문-답변 데이터 그룹핑
          // acc는 누적 데이터이고, msg는 전체 데이터라고 생각하면 쉽다.
          const formattedData: QuestionWithAnswers[] = data.reduce(
            (acc: QuestionWithAnswers[], msg: any) => {
              const existingQuestion = acc.find(
                (q) => q.questionId === msg.questionId // acc와 전체 데이터의 중복 방지
              );

              if (existingQuestion) {
                existingQuestion.answers = [
                  ...existingQuestion.answers, // spread 연산자
                  {
                    answerId: msg.answerId,
                    answerContents: msg.answerContents,
                  },
                ];
              } else {
                acc.push({
                  questionId: msg.questionId,
                  questionContents: msg.questionContents,
                  answers: msg.answerId
                    ? [
                        // 질문에 꼭 답변이 붙는 건 아니니, Optional로 답변이 있는 경우만 추가하도록 함.
                        {
                          answerId: msg.answerId,
                          answerContents: msg.answerContents,
                        },
                      ]
                    : [],
                });
              }
              return acc;
            },
            []
          );

          setInitialMessages(formattedData);
        } else {
          console.error("Failed to fetch initial data");
          return;
        }
      } catch (error) {
        console.error("Error fetching initial data:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchinitialData();
  }, [jwt]);

  return { initialMessages, isLoading };
};
