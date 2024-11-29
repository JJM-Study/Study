import requests
import random
import json
import sqlite3
import os
import logging
import pickle
from flask_cors import CORS
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neighbors import KNeighborsClassifier
from flask import Flask, request, jsonify

# 로깅 설정 초기화
logger = logging.getLogger()
logger.setLevel(logging.DEBUG)

console_handler = logging.StreamHandler()
console_handler.setLevel(logging.DEBUG)

formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
console_handler.setFormatter(formatter)

logger.addHandler(console_handler)


# Spring Boot 서버 API 엔드포인트
app = Flask(__name__)
#CORS(app, resources={r"/*": {"origins": ["http://localhost:8080", "http://localhost:3000"]}}) // 개발 중에는, CORS 설정을 너무 엄격히 할 시 여러 오류가 발생할 수 있음.
CORS() # 따라서, 개발 중에는 모든 오리진(모든 출저)을 허용하는 방식으로 수정. 2024/11/28 수정

class LeaningAI:

   def __init__(self):
      # 스프링 부트 기본 API
      # self.sApi_url = "http://localhost:8080/api"
      self.session = requests.Session()   # 세션 객체 생성
      self.questions = [] # 전체 질문 데이터 저장
      self.answers = [] # 전체 답변 데이터 저장
      self.vectorizer = TfidfVectorizer # TF-IDF 벡터라이저
      self.model = None # KNN 모델 초기화
      self.c_question = None # 현재 질문 데이터 저장
      self.model_path = os.path.join(os.getcwd(), "trained_model.pkl")  # "C:/Users/user/git/Study/p_ai/p_ai/src/main/ai-model/ 모델 경로
      self.jwt_token = None
      self.load_model() # 기존 학습된 모델 불러오기
      self.initialize_jwt_token() # 서버 시작 시 JWT 토큰 초기화

   def initialize_jwt_token(self):
        """
        서버 초기화 시 JWT 토큰을 미리 발급 받도록.
        """
        # JWT 발급 요청
        auth_url = "http://localhost:8080/api/authenticate"
        payload = {"username": "user"}
        #response = self.session.get(auth_url, json=payload)   # 자동으로 JWT 발급 요청

        try:    
            response = self.session.post(auth_url, json=payload)   # 자동으로 JWT 발급 요청
            if response.status_code == 200:
                # JWT 토큰을 성공적으로 발급받음
                self.jwt_token = response.json().get("token")
                if not self.jwt_token:
                    raise Exception("JWT Token not found in response")
                print("JWT token obtained successfully.")
            else:
                raise Exception(f"Failed to obtain JWT: {response.text}")
        except Exception as e:
            print(f"Error during JWT token initialization: {e}")
            self.jwt_token = None


   def fetch_data(self):
        try:
            if not self.jwt_token:
                raise Exception("JWT Token is not initialized.")
        
            # Authorization 헤더에 JWT 토큰 설정
            headers = {"Authorization": f"Bearer {self.jwt_token}"}

            # 질문/답변 전체 데이터를 API를 통해 가져오기 / 현재로서는 사용 X / 확장성 고려.
            questions_response = self.session.get("http://localhost:8080/api/questions", headers=headers)
            questions_response.raise_for_status()

            answers_response = self.session.get("http://localhost:8080/api/answers", headers=headers)  # 현재는 사용 X. 확장성을 고려, 임시 추가. 2024/11/29
            answers_response.raise_for_status()

            #데이터 저장
            self.questions = [q['contents'] for q in questions_response.json()] # JPA finaAll 매핑으로, 질문과 답변 전체를 가져오는 로직.
            self.answers = [a['contents'] for a in answers_response.json()] # 현재는 사용 X. 확장성을 고려, 임시 추가. 2024/11/29

            print(f"check answers : ", self.c_question) # 최신 데이터 제대로 가져옴을 확인.

        except requests.exceptions.RequestException as e:
            print(f"Request exception occurred: {e}")
            raise Exception("Failed to fetch data from API")
        except Exception as e:
            print(f"Error occurred: {e}")
            raise Exception("An internal error occurred")

   def train_model(self):
        """
        모델을 학습하는 함수. 질문과 답변을 기반으로 KNN 모델을 학습.
        """
        if not self.questions or not self.answers:
            print("Not enough data to train the model, adding default data")
            #self.model = None   # 모델이 없는 상태로 유지
            self.add_default_data()  # 기본 데이터를 추가하여 모델 학습 준비

        if self.questions and self.answers:
            try:
                self.vectorizer = TfidfVectorizer()           
                # 질문 데이터를 TF-IDF 벡터화
                X = self.vectorizer.fit_transform(self.questions)
                # KNN 모델을 학습
                self.model = KNeighborsClassifier(n_neighbors=1)
                self.model.fit(X, self.answers)

                # 학습된 모델을 Pickle을 사용하여 저장
                os.makedirs(os.path.dirname(self.model_path), exist_ok=True) # 디렉토리 없을 시 생성
                with open(self.model_path, "wb") as model_file:
                        pickle.dump((self.vectorizer, self.model), model_file) # 매번 학습하는 건 비효율적일 수 있으므로 다른 방법을 생각해볼 것. / 학습이 완료된 이후에만 모델을 저장하게 한다거나.
                print("Model saved successfully")

            except Exception as e:
                print(f"Error during model training: {e}")
                self.model = None

   def load_model(self):
        print("directory : " + os.getcwd())

        """
        학습된 모델을 파일에서 불러오는 함수. 모델 파일이 없으면 새로 학습하도록 설정.
        """
        try:
            if os.path.exists(self.model_path):
                #Pickle을 사용하여 학습된 모델 불러오기
                with open(self.model_path, "rb") as model_file:
                    self.vectorizer, self.model = pickle.load(model_file)
                print("Model loaded succesfully.")
            else:
                print("No trained model found. Training is required.")
                self.model = None
        except FileNotFoundError:
            print("No trained model found. Training is required.")
            self.model = None
            
   def get_answer(self, user_question):
       """
       사용자 질문에 대한 답변을 반환하는 함수.
       """
       if self.model is None:
          print("No model found, Please train the model first.")
          #self.add_default_data() //
          #self.train_model()
          #if self.model is None:
          return "Sorry, Don't have enough data to answer that right now."
   
       # 사용자 질문을 TF-IDF 벡터로 변환
       try:
          user_question_vec = self.vectorizer.transform([user_question])
          # 가장 유사한 질문을 찾고 해당 답변 반환
          answer = self.model.predict(user_question_vec)[0]
       except Exception as e:
           print(f"Prediction error occurred: {e}")
           return "Error occured while creating an answer." # 문자열 반환    

       return answer

   # 기본적인 질문과 답변 데이터를 설정하는 함수. (초기 학습 시 사용)
   def add_default_data(self):
       self.questions = ["기본 질문입니다."]
       self.answers = ["기본 답변입니다."]

# 글로벌 변수로 모델 인스턴스를 초기화하지 않고, 요청이 들어올 때마다 초기화
leaning_ai = LeaningAI()

@app.route('/generate_answer', methods=['POST'])
def generate_answer():
    try:
        print("Received a request to /generate_answer")  # 요청이 들어왔는지 로그로 출력

        logging.info("API endpoint '/generate_answer' called")  # 확인용 로그
        logging.info("file path: " + os.getcwd())
        
        data = request.get_json()

        print(f"Request data: {data}")

        if 'questions' not in data:
            return jsonify({'error': 'No questions provided'}), 400
        
        question_data = data['questions']

        # 모델이 없는 경우에만 학습  // 모델이 없는 경
        #if os.path.exists() 

        # 스프링 부트 API에서 질문/답변 데이터를 가져오고 학습
        leaning_ai.fetch_data()

        # #만약 데이터가 없으면 기본 데이터를 추가
        # if not leaning_ai.questions or not leaning_ai.answers:
        #     leaning_ai.add_default_data()

        leaning_ai.train_model()

        # 모델 학습 후 질문에 대한 답변 생성
        answer = leaning_ai.get_answer(question_data)

        return jsonify({'answers': [answer]})

    except requests.exceptions.HTTPError as e:
        print(f"HTTP error occurred: {e}")
        return jsonify({'error': 'An unexpected error occurred', 'details': str(e)}), 500
    except Exception as e:
        print(f"An unexpected error occurred: {e}")
        return jsonify({'error': 'An unexpected error occurred', 'details': str(e)}), 500

# # 데이터 부족 시, 임의로 질문, 답변 데이터를 추가해주기 위함.
# @app.route('/add_data', method=['POST'])
# def add_data():
#     try:
#         data = request.get_json()

#         if 'question' in data and 'answer' in data:
#             question = data['question']
#             answer = data['answer']

#             # 새 데이터를 추가하고 저장
#             leaning_ai.questions.append(question)
#             leaning_ai.answers.append(answer)

#             return jsonify({'message': 'Data added successfully!'}), 200
#         else:
#             return jsonify({'error': 'Invalid data provided'}), 400
#     except Exception as e:
#         print(f"An error occurred while adding data: {e}")
#         return jsonify({'error': 'An error occurred', 'details': str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)


# 20240903 주석
# ai = LeaningAI()

# # 데이터 가져오기
# ai.fetch_data()

# # 모델 학습
# ai.train_model()