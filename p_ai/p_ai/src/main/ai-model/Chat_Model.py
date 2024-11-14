import requests
import random
import json
import sqlite3
from flask_cors import CORS
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neighbors import KNeighborsClassifier
from flask import Flask, request, jsonify


   # Spring Boot 서버 API 엔드포인트

app = Flask(__name__)

class LeaningAI:

   def __init__(self):
      # 스프링 부트 기본 API
      # self.sApi_url = "http://localhost:8080/api"

      self.session = requests.Session()   # 세션 객체 생성
      self.questions = [] # 질문 데이터 저장
      self.answers = [] # 답변 데이터 저장
      self.vectorizer = TfidfVectorizer() # TF-IDF 벡터라이저
      self.model = None # KNN 모델 초기화

   def fetch_data(self):
    
    try:

            # 로그인 정보
            login_url = 'http://localhost:8080/login'
            payload = {'username': 'user', 'password': 'password'}

            # 로그인 요청
            response = self.session.post(login_url, data=payload) # 세션을 통해 로그인 요청

            print(f"Login response status code: {response.status_code}")
            print(response.cookies)

            if response.status_code == 200:
                print("Login successful")
            else:
                print("Login failed:", response.text)

            questions_response = self.session.get("http://localhost:8080/api/questions") # 현재 에러 발생지 2024/11/01
            questions_response.raise_for_status()
            answers_response = self.session.get("http://localhost:8080/api/answers")
            answers_response.raise_for_status()

            self.questions = [q['contents'] for q in questions_response.json()]
            self.answers = [a['contents'] for a in answers_response.json()]
    
    except requests.exceptions.HTTPError as err:
        print(f"HTTP error occurred: {err}")
        raise Exception("Failed to fetch data from API")
    except Exception as e:
        print(f"Error occurred: {e}")
        raise Exception("An internal error occurred")

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
   

# 글로벌 변수로 모델 인스턴스를 초기화하지 않고, 요청이 들어올 때마다 초기화
leaning_ai = LeaningAI()

@app.route('/generate_answer', methods=['POST'])
def generate_answer():
    try:
        data = request.get_json()

        if 'questions' in data:
            question_data = data['questions']
        
            # # 질문과 답변 데이터를 설정
            # leaning_ai.questions = [question_data]
            # leaning_ai.answers = ["Dummy Answer"]  # 실제 데이터로 대체

            # 스프링 부트 API에서 질문/답변 데이터를 가져오고 학습
            leaning_ai.fetch_data()

            leaning_ai.train_model()

            # 질문에 대한 답변 생성
            answer = leaning_ai.get_answer(question_data)
            return jsonify({'answers': [answer]})
        else:
            return jsonify({'error': 'No questions provided'}), 400
    except Exception as e:
        print(f"Error occurred: {e}")
        return jsonify({'error': 'An internal error occurred'}), 500

if __name__ == '__main__':
    app.run(port=5000)

CORS(app)

# 20240903 주석
# ai = LeaningAI()

# # 데이터 가져오기
# ai.fetch_data()

# # 모델 학습
# ai.train_model()