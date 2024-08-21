import random
import json
import sqlite3

class LeaningAI:
  def __init__(self):
      self.responses = {}  # 과거 대화 저장
      self.training_data = []   # 사용자 피드백 저장

  def train(self, input_text, output_text):
      """ 사용자 입력에 대한 응답을 학습 """
      if input_text not in self.responses:
         self.responses[input_text] = []
      self.responses[input_text].append(output_text)

  def get_response(self, input_text):
     """사용자 입력에 대한 응답 제공"""
     if input_text in self.responses:
        return random.choice(self.responses[input_text])
     
     else:
        # 새로운 입력에 대한 임의의 기본 응답
        return "새로운 질문이네요! 더 배우기 위해 알려주세요."
     
  def receive_feedback(self, input_text, user_feedback):
         """사용자로부터 피드백을 받아 학습 데이터에 추가"""
         self.training_data.append((input_text, user_feedback))
         self.train(input_text, user_feedback)

  def save_training_data(self, filename):
        """학습 데이터를 파일에 저장"""
        with open(filename, 'w') as file:
            json.dump(self.responses, file)

  def load_training_data(self, filename):
      """학습 데이터를 파일에서 로드"""
      try:
           with open(filename, 'r') as file:
                self.response = json.load(file)
      except FileNotFoundError:
           print(f"{filename} 파일이 없습니다.")

# 예시 사용법
ai = LeaningAI()

# AI가 학습하는 부분
ai.train("안녕하세요", "안녕하세요! 무엇을 도와드릴까요?")
ai.train("비즈니스 관리 방법이 궁금해요", "효율적인 비즈니스 관리에는 전략적 계획, 재무 관리, 고객 중심의 접근 등이 필요합니다.")

# 사용자와의 상호작용
user_input = input("사용자 : ")
response = ai.get_response