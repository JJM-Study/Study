// 공통 사용 유지보수 및 편의성 위해 분리함.
export type QuestionWithAnswers = {
  questionId: number;
  questionContents: string;
  //answerId: number;   // 질문 1 : 답변 N인 걸 감안해서 수정했음.
  //answerContents: string;

  // 여러 개의 답변을 포함.
  answers: {
    answerId: number;
    answerContents: string;
  }[];
  pending?: boolean; // Optional 선언
};

// 2025/02/19 추가 / 훈련된 데이터 불러오기 For QuestionList
export type trainedQuestionWithAnswers = {
  questionId: number;
  questionContents: string;
  answers: {
    answerId: number;
    answerContents: string;
  }[];
};

export type AnswerMessage = {
  id: number;
  contents: string;
  questionId: number;
};
