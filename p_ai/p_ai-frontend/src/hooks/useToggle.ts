import { useReducer } from "react";
import { trainedQuestionWithAnswers } from "../types/types";

type State = {
  isToggled: boolean;
  hasData: boolean;
};

type Action = { type: "TOGGLE" } | { type: "SET_DATA"; payload: boolean };

const toggleReducer = (state: State, action: Action) => {
  switch (action.type) {
    case "TOGGLE":
      return { ...state, isToggled: !state.isToggled };
    case "SET_DATA":
      return { ...state, hasData: action.payload };
    default:
      return state;
  }
};

export const useToggle = (
  initialState: boolean = false,
  initialMessages: trainedQuestionWithAnswers[] = []
) => {
  const [state, dispatch] = useReducer(toggleReducer, {
    isToggled: initialState,
    hasData: initialMessages.length > 0,
  });

  const setData = (messages: trainedQuestionWithAnswers[]) => {
    dispatch({ type: "SET_DATA", payload: messages.length > 0 });
  };

  const toggle = () => {
    dispatch({ type: "TOGGLE" });
  };

  return {
    isToggled: state.isToggled,
    hasData: state.hasData,
    toggle,
    setData,
  };
};
