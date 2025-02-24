import React, { useReducer, useState, useEffect } from "react";
//import { useFetchTrainedQandA } from "../hooks/useFetchTrainedQandA";
import { useToggle } from "../hooks/useToggle";
import { useTraining } from "../context/TrainingProvider";

interface Props {
  onQuestionClick: (text: string) => void;
}

type Action = { type: "SELECT_QUESTION"; payload: number | null };

// status는 개별 상태마다 별도 관리 필요. 따라서 useReducer 사용 시도
const reducer = (
  state: { selectedQuestion: number | null },
  action: Action
) => {
  switch (action.type) {
    case "SELECT_QUESTION":
      return { ...state, selectedQuestion: action.payload };
    default:
      return state;
  }
};

const QuestionList: React.FC<Props> = ({ onQuestionClick }) => {
  //const { loadTrainedQandA, isLoading: isTrainedQandALoading } =useFetchTrainedQandA();
  //const { loadTrainedQandA } = useFetchTrainedQandA();
  const { loadTrainedQandA, isLoading } = useTraining();
  const { isToggled, hasData, toggle, setData } = useToggle(
    false,
    loadTrainedQandA
  ); // useToggle
  const [state, dispatch] = useReducer(reducer, { selectedQuestion: null });
  const [jwt, setJwt] = useState<string | null>(localStorage.getItem("jwt")); // 2025/02/22 추가

  useEffect(() => {
    console.log(
      "useEffect 실행됨. loadTrainedQandA:",
      loadTrainedQandA,
      "jwt:",
      jwt
    );
    //setData(loadTrainedQandA);
    setData(loadTrainedQandA ?? []);
    // hasData 참조하라는 경고 떠서 추가함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
    //}, [loadTrainedQandA]);
  }, [loadTrainedQandA]); // 토큰 발급을 감지 못해서 렌더링이 안 되어서 추가. 2025/02/22 추가

  return (
    <div className="flex-col w-full pt-1 pb-1 pl-2 pr-2 mt-2 bg-gray-200 rounded-md shadow-md">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold">학습된 질문 목록</h2>
        <button onClick={toggle} className="text-sm text-gray-500 underline">
          {isToggled ? "축소" : "더보기"}
        </button>
      </div>
      {/* 2025/02/18 데이터 없을 시, 토글 실행되지 않도록 hasData 조건 추가 */}
      {/* 질문-답변쌍 목록 */}
      {!hasData ? (
        // isToggled && (
        <p className="mt-2 text-xs text-gray-500">
          데이터가 존재하지 않습니다.
        </p>
      ) : (
        // )
        <div
          className={`overflow-y-auto transition-[max-height] duration-300 ease-in-out min-h-[80px] ${
            isToggled ? "max-h-60" : "max-h-20"
          }`}
        >
          {/* {loadTrainedQandA.map((q) => ( */}
          {(loadTrainedQandA ?? []).map((q) => (
            <div key={q.questionId}>
              <div className="p-2 my-1 bg-white rounded-md shadow-md">
                <div className="flex justify-between itmes-center">
                  <p
                    className="font-semibold cursor-pointer"
                    onClick={() => onQuestionClick(q.questionContents)}
                  >
                    Q : {q.questionContents}
                  </p>
                  <button
                    className="text-xs text-blue-500 underline"
                    onClick={() =>
                      dispatch({
                        type: "SELECT_QUESTION",
                        payload:
                          state.selectedQuestion === q.questionId
                            ? null
                            : q.questionId,
                      })
                    }
                  >
                    {/* state.selectedQuestion === q.questionId ? null : q.questionId ? "답변 숨기기" : "답변 보기"*/}
                    {!q.questionId
                      ? null
                      : state.selectedQuestion === q.questionId
                      ? "답변 숨기기"
                      : "답변 보기"}
                  </button>
                </div>
                <div>
                  {state.selectedQuestion === q.questionId && (
                    <div className="p-2 text-sm text-green-700">
                      {q.answers.map((a, idx) => (
                        <p key={idx}>A: {a.answerContents}</p>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default QuestionList;
