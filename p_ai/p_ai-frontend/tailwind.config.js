/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx,html}", // src 폴더 내 파일 모두 감지
    "./src/styles/**/*.{css}", // css 파일 감지
  ],
  theme: {
    extend: {}, // 필요하면 여기에 테마 확장 추가
  },
  plugins: [require("daisyui")], // DaisyUI 플러그인 추가
  daisyui: {
    themes: ["light", "dark", "cupcake"], // DaisyUI 기본 테마만 사용
  },
};
