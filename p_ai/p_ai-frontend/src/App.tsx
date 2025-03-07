import React from "react";
import {
  useQuery,
  QueryClient,
  QueryClientProvider,
} from "@tanstack/react-query";
import { TrainingProvider } from "./context/TrainingProvider";
import "./App.css";
import Chat from "./components/Chat";

const queryClient = new QueryClient();

const App: React.FC = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <TrainingProvider>
        <div className="flex items-center justify-center w-screen h-screen bg-grey-200">
          <Chat />
        </div>
      </TrainingProvider>
    </QueryClientProvider>
  );
};

export default App;
