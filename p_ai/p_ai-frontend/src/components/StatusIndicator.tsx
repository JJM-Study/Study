import React from "react";

const StatusIndicator: React.FC<{ isConnected: boolean }> = ({
  isConnected,
}) => {
  return (
    <div className="flex items-center">
      <div
        className={`w-3 h-3 rounded-full ${
          isConnected ? "bg-green-500" : "bg-red-500"
        }`}
      ></div>
      <span
        className={`text-xs ${isConnected ? "text-green-500" : "text-red-500"}`}
      >
        &nbsp;{isConnected ? "연결됨" : "연결 끊김"}
      </span>
    </div>
  );
};

export default StatusIndicator;
