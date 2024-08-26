import requests
import random
import json
import sqlite3


   # Spring Boot 서버 API 엔드포인트


class LeaningAI:


   
   def __init__(self):
      # 스프링 부트 기본 API
      self.sApi_url = "http//localhost:8080/api"

      # 질문과 답변 데이터 저장
      self.questions = []
      self.answers = []
      
      # TF-IDF 벡터라이저와 KNN 모델 초기화
      self.vectorizer = TfidfVectorizer()
      self.model = None

   def fetch_data(self):
      # 질문과 답변 Get

      try:
         questions_response = requests.get(f"{self.sApi_url}/questions")
         answers_response = requests.get(f"{self.sApi_url}/answers")


         if questions_response.status_code == 200 and answers_response.status_code == 200:
            self.questions = [q['content'] for q in questions_response.json()]
            self.answers = [a['content'] for a in answers_response.json()]
         else:
            raise Exception("Failed to fetch data from API")
      except requests.exceptions.RequestException as e:
            print(f"Error occurred while fetching data: {e}")

   def train_model(self):
         """
         모델을 학습하는 함수. 질문과 답변을 기반으로 KNN 모델을 학습.
         """
         if self.questions or self.answers

         



         
         else:
            print(f"Failed to fetch data. Status code: {response.status_code}")
            return None, None
            


   def train(self, input_text, output_text):
      """ 사용자 입력에 대한 응답을 학습 """
      if input_text not in self.responses:
         self.responses[input_text] = []
      self.responses[input_text].append(output_text)

   def get_response(self, input_text):
       """ H2 데이터베이스에서 질문에 해당하는 응답을 찾는다. """
       self.cursor.execute("SELECT contents FROM AI_ANSWER WHERE QUESTION_ID = ?", (input_text)),
       result = self.cursor.fetchone()

       if result:
          return result[0]
       else:
          # 새로운 입력에 대한 임의의 기본 응답
          return "새로운 질문이네요! 더 배우기 위해 알려주세요."

   def receive_feedback(self, input_text, user_feedback):
         """사용자로부터 피드백을 받아 스프링 부트 API로 전송하는 함수 
         
         :param input_text : 사용자가 입력한 텍스트
         :param user_feedback : 사용자가 제공한 피드백
         :return: 스프링 부트 API 요청의 상태 코드 (정상 처리 여부를 확인 가능)
         """

         # 이 부분은 스프링 부트가 처리하도록 하며, 파이썬에서 따로 관리할 필요는 없다.
         # 데이터베이스에 피드백 저장 호출 함수 추가 필요 (스프링 부트 API 호출)

   # 1. 전송할 데이터(playload) 생성
   playload = {
       'input_text': input_text,  # 사용자가 입력한 텍스트
       'user_facebook': user_feedback  # 사용자의 피드백
   }

   # 2. POST 요청을 통해 스프링 부트 API에 데이터 전송
   response = requests.post(f'{self.api_url}/feedback', json=payload)


   #

   def generate_answer(self, user_input): 
         """질문을 생성하고 스프링 부트로 전송"""
         # 사용자 입력을 기반으로 질문을 생성, 스프링 부트로 전송하는 로직 구현
         # 스프링 부트 API 호출을 통해 질문 저장

         # 1. 사용자 입력을 기반으로 답변 생성 (단순 예시로 구현)
         question = f"질문: {user_input}" 
         
         # 2. 전송할 데이터(payload) 생성
         playload = {'question' : question}  # 생성된 질문을 playload에 포함

         # 3. POST 요청을 통해 스프링 부트 API에 답변 데이터 전송
         response = requests.post(f'{self.api_uri}/answers', json=playload)

         # 4. API 요청의 응답 상태 코드 반환
         return response.status_code

# 예시 사용법
ai = LeaningAI()

# AI가 학습하는 부분
ai.train("안녕하세요", "안녕하세요! 무엇을 도와드릴까요?")
ai.train("비즈니스 관리 방법이 궁금해요", "효율적인 비즈니스 관리에는 전략적 계획, 재무 관리, 고객 중심의 접근 등이 필요합니다.")

# 사용자와의 상호작용
user_input = input("사용자 : ")
response = ai.get_response(user_input)
print(f"AI: {response}")

# 사용자 피드백 받기
user_feedback = input("피드백을 입력하세요.")
ai.receive_feedback(user_input, user_feedback)

# 학습 데이터 저장
ai_training_data('leanring_data.json')

