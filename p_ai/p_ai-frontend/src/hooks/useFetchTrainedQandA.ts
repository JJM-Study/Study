import { useState, useEffect } from "react";
import { trainedQuestionWithAnswers } from "../types/types";

export const useFetchTrainedQandA = () => {
  const [loadTrainedQandA, setLoadTrainedQandA] = useState<
    trainedQuestionWithAnswers[]
  >([]);
  const [isLoading, setIsLoading] = useState(true);
  const [jwt, setJwt] = useState<string | null>(localStorage.getItem("jwt")); // 2025/02/20 추가

  // 참고 https://week-book.tistory.com/entry/React-fetch-%EC%82%AC%EC%9A%A9%EB%B2%95-LoadingErrorGETPOST
  // https://velog.io/@dev_cecy/React-fetch-%ED%95%A8%EC%88%98-%EC%82%AC%EC%9A%A9%ED%95%B4%EC%84%9C-Token-%EB%B0%9B%EA%B8%B0, https://m.blog.naver.com/dlaxodud2388/223176497318
  const fetchTrainedQandA = async () => {
    console.log("fetchTrainedQandA 실행됨.");
    try {
      const jwt = localStorage.getItem("jwt"); // 2025/02/24 추가함. 토큰 변화를 실시간으로 못 읽어서 /
      if (!jwt) {
        console.error("No JWT found");
        return;
      }
      const response = await fetch(
        "http://localhost:8080/api/Training-Question-And-Answer",
        //"https://web-pj.com/api/Training-Question-And-Answer", // CLOUDFRONT 배포
        {
          method: "GET",
          headers: {
            //Authorization: `Bearer ${localStorage.getItem("jwt")}`,
            Authorization: `Bearer ${jwt}`, // 직접 로컬 스토리지에서 읽어오면 최신 token을 반영 못할 수도 있다고 함. 2025/02/20 수정
            "Content-Type": "application/json",
          },
          credentials: "include",
        }
      );

      // https://velog.io/@tosspayments/%EC%98%88%EC%A0%9C%EB%A1%9C-%EC%9D%B4%ED%95%B4%ED%95%98%EB%8A%94-awaitasync-%EB%AC%B8%EB%B2%95
      // await : 비동기 요청 중 서버 응답을 기다리게 해주는 것.
      if (response.ok) {
        const data = await response.json();
        console.log("서버에서 받은 데이터:", data);
        // 질문과 답변 데이터 그룹핑
        const formattedData: trainedQuestionWithAnswers[] = data.reduce(
          (acc: trainedQuestionWithAnswers[], msg: any) => {
            const existingQuestion = acc.find(
              (q) => q.questionId === msg.questionId
            );

            if (existingQuestion) {
              existingQuestion.answers = [
                ...existingQuestion.answers,
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
        console.log("formattedData:", formattedData);
        setLoadTrainedQandA(formattedData);
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

  useEffect(() => {
    // 2025/02/24 fetchTrainedQandA를 감싸던 훅을 빼내옴.
    fetchTrainedQandA();
  }, []);

  return { loadTrainedQandA, isLoading, fetchTrainedQandA };
};
