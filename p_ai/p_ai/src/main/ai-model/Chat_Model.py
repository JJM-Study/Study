import requests
import random
import json
import sqlite3
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neighbors import KNeighborsClassifier
from flask import Flask, request, jsonify


   # Spring Boot 서버 API 엔드포인트


class LeaningAI:

   def __init__(self):
      # 스프링 부트 기본 API
      self.sApi_url = "http://localhost:8080/api"

      self.questions = [] # 질문 데이터 저장
      self.answers = [] # 답변 데이터 저장
      self.vectorizer = TfidfVectorizer() # TF-IDF 벡터라이저
      self.model = None # KNN 모델 초기화

   # def fetch_data(self):
   #    # 질문과 답변 Get

   #    try:
   #       questions_response = requests.get(f"{self.sApi_url}/questions")
   #       answers_response = requests.get(f"{self.sApi_url}/answers")

   #       # API 요청이 성공했는지 확인
   #       if questions_response.status_code == 200 and answers_response.status_code == 200:
   #          # 질문과 답변 데이터를 JSON 형식으로 파싱하여 
   #          self.questions = [q['content'] for q in questions_response.json()]
   #          self.answers = [a['content'] for a in answers_response.json()]
   #       else:
   #          raise Exception("Failed to fetch data from API")
   #    except requests.exceptions.RequestException as e:
   #          print(f"Error occurred while fetching data: {e}")

   def train_model(self):
         """
         모델을 학습하는 함수. 질문과 답변을 기반으로 KNN 모델을 학습.
         """
         if not self.questions or not self.answers:
             print("Answers are not enough for traning. Default response will be used.")
             self.model = None   # 모델이 없는 상태로 유지
             return

         # 질문 데이터를 TF-IDF 벡터화
         X = self.vectorizer.fit_transform(self.questions)

         # KNN 모델을 학습
         self.model = KNeighborsClassifier(n_neighbors=1)
         self.model.fit(X, self.answers)

   def get_answer(self, user_question):
       """
       사용자 질문에 대한 답변을 반환하는 함수.
       """
       if self.model is None:
         return "Sorry, Don't have enough data to answer that right now."
   
         # 사용자 질문을 TF-IDF 벡터로 변환
       user_question_vec = self.vectorizer.transform([user_question])

         # 가장 유사한 질문을 찾고 해당 답변 반환
       answer = self.model.predict(user_question_vec)[0]

      #  # 생성된 답변을 스프링 부트 API로 전송
      #  payload = {'question': user_question, 'answer': answer}
      #  response = requests.post(f'{self.sApi_url}/save_answer', payload)

      #  if response.status_code == 200:
      #      print("Answer successfully send to Srping Boot API.")
      #  else:
      #      print(f"Failed to send answer. Status code: {response.status_code}")
      
       return answer

#Flask 웹 서버 서정



# 20240903 주석
# ai = LeaningAI()

# # 데이터 가져오기
# ai.fetch_data()

# # 모델 학습
# ai.train_model()