// TrainingProvider.tsx
import React, { createContext, useContext } from "react";
import { useQuery, UseQueryOptions } from "@tanstack/react-query";
import { trainedQuestionWithAnswers } from "../types/types";

// 컨텍스트 인터페이스 정의
interface TrainingContextProps {
  loadTrainedQandA: trainedQuestionWithAnswers[] | undefined;
  isLoading: boolean;
  refetch: () => void;
}

// TrainingContext 생성
const TrainingContext = createContext<TrainingContextProps>({
  loadTrainedQandA: [],
  isLoading: true,
  refetch: () => {},
});

// 컨텍스트 사용을 위한 커스텀 훅
export const useTraining = () => useContext(TrainingContext);

// 토큰 값을 상태로 관리하기 위해, 애플리케이션 전역에서 인증 토큰을 별도 관리하거나
// localStorage에서 직접 읽어오는 방식으로 처리할 수 있습니다.
// 여기서는 간단히 localStorage를 읽어 query key에 반영합니다.
const fetchTrainedQandA = async (token: string) => {
  const response = await fetch(
    // 로컬
    "http://localhost:8080/api/Training-Question-And-Answer",
    //CLOUDFRONT 배포
    //"https://web-pj.com/api/Training-Question-And-Answer",
    {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      credentials: "include",
    }
  );
  if (!response.ok) {
    throw new Error("Failed to fetch data");
  }
  const data = await response.json();
  // 질문과 답변 데이터 그룹핑
  const formattedData: trainedQuestionWithAnswers[] = data.reduce(
    (acc: trainedQuestionWithAnswers[], msg: any) => {
      const existingQuestion = acc.find((q) => q.questionId === msg.questionId);
      if (existingQuestion) {
        existingQuestion.answers = [
          ...existingQuestion.answers,
          { answerId: msg.answerId, answerContents: msg.answerContents },
        ];
      } else {
        acc.push({
          questionId: msg.questionId,
          questionContents: msg.questionContents,
          answers: msg.answerId
            ? [{ answerId: msg.answerId, answerContents: msg.answerContents }]
            : [],
        });
      }
      return acc;
    },
    []
  );
  return formattedData;
};

export const TrainingProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  // localStorage에서 jwt를 읽어와 query key의 일부로 사용
  const jwt = localStorage.getItem("jwt");

  // jwt가 없으면 query는 중단되도록 enabled 옵션 사용
  const { data, isLoading, refetch } = useQuery({
    queryKey: ["trainedQandA", jwt],
    queryFn: async () => {
      if (!jwt) throw new Error("No JWT found");
      return fetchTrainedQandA(jwt);
    },
    enabled: !!jwt, // jwt가 있을 때만 실행
    refetchOnWindowFocus: false,
    staleTime: 1000 * 60 * 5, // 필요에 따라 staleTime 조정
  });

  return (
    <TrainingContext.Provider
      value={{ loadTrainedQandA: data, isLoading, refetch }}
    >
      {children}
    </TrainingContext.Provider>
  );
};
