import { useEffect, useState } from "react";
import { QuestionWithAnswers } from "../types/types"; // 2025/01/28 추가

export const useLoadInitialData = () => {
  //const [initialMessages, setinitialMessages] = useState<>([]);
  const [initialMessages, setInitialMessages] = useState<QuestionWithAnswers[]>(
    []
  ); // 2025/01/28 수정
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchinitialData = async () => {
      try {
        const response = await fetch(
          "http://localhost:8080/api/Question-And-Answer",
          {
            headers: { Authorization: `Bearer ${localStorage.getItem("jwt")}` },
          }
        );

        if (response.ok) {
          const data = await response.json(); // Message 단일 지정

          // // 질문-답변 데이터 그룹핑
          const formattedData: QuestionWithAnswers[] = data.reduce(
            (acc: QuestionWithAnswers[], msg: any) => {
              const existingQuestion = acc.find(
                (q) => q.questionId === msg.questionId
              );

              if (existingQuestion) {
                existingQuestion.answers.push({
                  answerId: msg.answerId,
                  answerContents: msg.answerContents,
                });
              } else {
                acc.push({
                  questionId: msg.questionId,
                  questionContents: msg.questionContents,
                  answers: [
                    {
                      answerId: msg.answerId,
                      answerContents: msg.answerContents,
                    },
                  ],
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
  }, []);

  return { initialMessages, isLoading };
};
