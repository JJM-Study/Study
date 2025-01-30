// 공통 사용 유지보수 및 편의성 위해 분리함.
export type QuestionWithAnswers = {
  questionId: number;
  questionContents: string;
  //answerId: number;
  //answerContents: string;

  // 여러 개의 답변을 포함.
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
