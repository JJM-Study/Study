import requests
import random
import json
import sqlite3
import os
import logging
import pickle
import time
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
      #self.c_question = None # 현재 질문 데이터 저장
      self.session = requests.Session()   # 세션 객체 생성
      self.questions = [] # 전체 질문 데이터 저장
      self.answers = [] # 전체 답변 데이터 저장
      self.question_and_answer = [] # 전체 질문-답변 데이터쌍 저장
      self.vectorizer = TfidfVectorizer # TF-IDF 벡터라이저
      self.model = None # KNN 모델 초기화
      self.model_path = os.path.join(os.getcwd(), "trained_model.pkl")  # "C:/Users/user/git/Study/p_ai/p_ai/src/main/ai-model/ 모델 경로
      self.jwt_token = None
      self.load_model() # 기존 학습된 모델 불러오기
      self.initialize_jwt_token() # 서버 시작 시 JWT 토큰 초기화


   def initialize_jwt_token(self, max_retries=5, retry_delay=2):
        """
        서버 초기화 시 JWT 토큰을 미리 발급 받도록.
        """
        # JWT 발급 요청
        auth_url = "http://localhost:8080/api/authenticate"
        payload = {"username": "user"}
        #response = self.session.get(auth_url, json=payload)   # 자동으로 JWT 발급 요청

        for attempt in range(max_retries):
            try:    
                response = self.session.post(auth_url, json=payload)   # 자동으로 JWT 발급 요청
                print("JWT token obtained successfully.")
                if response.status_code == 200:
                    # JWT 토큰을 성공적으로 발급받음
                    self.jwt_token = response.json().get("token")
                    print(f"현재 Flask JWT 토큰 값: {self.jwt_token}")
                    if not self.jwt_token:
                        raise Exception("JWT Token not found in response")
                    print("JWT token obtained successfully.")
                    return
                else:
                    raise Exception(f"Failed to obtain JWT: {response.text}")
            except Exception as e:
                print(f"Error during JWT token initialization attempt {attempt + 1}: {e}")
                self.jwt_token = None
            
            #재시도 간격 대기
            time.sleep(retry_delay)

        raise Exception("JWT Token could not be initialized after multiple attempts.")
            

   def fetch_data(self):

        if not self.jwt_token:
            print("JWT Token is not initialized. Reinitailizing...")     # 토큰 재발급 받도록 수정 중 ..
            try:  # 토큰 중간에 끊기는 경우가 있어서, 재발급 받도록 설정.
                #raise Exception("JWT Token is not initialized.")
                self.initialize_jwt_token()
            except Exception as e:
                raise Exception("JWT Token could not be initialized JWT Token before fetching data.")

        # Authorization 헤더에 JWT 토큰 설정
        headers = {"Authorization": f"Bearer {self.jwt_token}"}
        try:
            # 질문/답변 전체 데이터를 API를 통해 가져오기 / 현재로서는 사용 X / 확장성 고려.
            questions_response = self.session.get("http://localhost:8080/api/questions", headers=headers)
            questions_response.raise_for_status()

            answers_response = self.session.get("http://localhost:8080/api/answers", headers=headers)  # 현재는 사용 X. 확장성을 고려, 임시 추가. 2024/11/29
            answers_response.raise_for_status()

            # q_and_a_response = self.session.get("http://localhost:8080/api/Question-And-Answer", headers=headers)
            q_and_a_response = self.session.get("http://localhost:8080/api/Training-Question-And-Answer", headers=headers) # 2025/02/04 기존 사용자 질문-답변 테이블에서 트레이닝용 테이블로 수정.
            q_and_a_response.raise_for_status()

            #데이터 저장
            self.questions = [q['contents'] for q in questions_response.json()] # JPA finaAll 매핑으로, 질문과 답변 전체를 가져오는 로직.
            self.answers = [a['contents'] for a in answers_response.json()] # 현재는 사용 X. 확장성을 고려, 임시 추가. 2024/11/29
            self.question_and_answer = [(c['questionContents'], c['answerContents']) for c in q_and_a_response.json()]  # 현재는

            if not self.question_and_answer or any(not q or not a for q, a in self.question_and_answer):
                print("Some questions or answers are missing. Training aborted.")
                return False

            return True

        except requests.exceptions.RequestException as e:
            print(f"Request exception occurred: {e}")
            raise Exception("Failed to fetch data from API")
        # except Exception as e:
        #     print(f"Error occurred: {e}")
        #     raise Exception("An internal error occurred")

   def train_model(self):
        """
        모델을 학습하는 함수. 질문과 답변을 기반으로 KNN 모델을 학습.
        """
        #if not self.questions or not self.answers: # 직접 파이썬에서 DB에 예시 데이터들을 넣거나, 따로 DB에 넣으라고 하는 등 조치 할 것. 2024/12/05 수정
        if not self.question_and_answer:
            print("Not enough data to train the model, adding default data")
            #self.model = None   # 모델이 없는 상태로 유지
            # self.add_default_data()  # 기본 데이터를 추가하여 모델 학습 
            return None

        #if self.questions and self.answers:
        else:
            try:
                self.vectorizer = TfidfVectorizer()
                #self.vectorizer = TfidfVectorizer(ngram_range=(1, 2)) #2025/02/04 수정 // 유니그램(1) + 바이그램(2) 사용으로, 단어 단위가 아니라 좀 더 폭넓은 단위로 벡터화
                
                # 질문 데이터를 추출하여 벡터화
                #X = self.vectorizer.fit_transform(self.questions)
                local_questions = [q for q, _ in self.question_and_answer]
                X = self.vectorizer.fit_transform(local_questions) # 2024/12/05 수정

                # 답변 데이터를 추출
                local_answers = [a for _, a in self.question_and_answer]

                # KNN 모델을 학습
                # self.model = KNeighborsClassifier(n_neighbors=1) // 1일 경우 가장 가까운 한 개 질문만 참고, 답변 반환.
                self.model = KNeighborsClassifier(n_neighbors=1) # 2025/02/04 수정 // 이 수치가 높아질수록 다수결 원칙 적용, 좀 더 정확한 답변이 나올 가능성이 높아짐.
                self.model.fit(X, local_answers)

                # 학습된 모델을 Pickle을 사용하여 저장
                self.create_model_file()
                return True
            except Exception as e:
                print(f"Error during model training: {e}")
                self.model = None
                return False

   def create_model_file(self):  # 학습된 모델  Pickle을 사용하여 저장
        os.makedirs(os.path.dirname(self.model_path), exist_ok=True) # 디렉토리 없을 시 생성
        with open(self.model_path, "wb") as model_file:
            pickle.dump((self.vectorizer, self.model), model_file)
        print("Model saved successfully")


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
          return "Sorry, Don't have enough data to answer that right now."
   
       # 사용자 질문을 TF-IDF 벡터로 변환
       try:
          user_question_vec = self.vectorizer.transform([user_question])

        #   #가장 가까운 질문과의 거리 측정
        #   distance, indices = self.model.kneighbors(user_question_vec)
        #   #유사도가 너무 낮으면 모름 처리
        #   if distance[0][0] > 1:
        #       return "질문을 이해하지 못했습니다. 다시 질문해주세요."

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

@app.route('/generate-answer', methods=['POST'])
def generate_answer():
    try:

        print("Received a request to /generate-answer")  # 요청이 들어왔는지 로그로 출력

        logging.info("API endpoint '/generate-answer' called")  # 확인용 로그
        logging.info("file path: " + os.getcwd())
        
        data = request.get_json()

        print(f"Request data: {data}")

        if 'questions' not in data:
            return jsonify({'error': 'No questions provided'}), 400
        
        question_data = data['questions']

        # 질문에 대한 답변 생성
        answer = leaning_ai.get_answer(question_data)
        return jsonify({'answers': [answer]})

    except requests.exceptions.HTTPError as e:
        print(f"HTTP error occurred: {e}")
        return jsonify({'error': 'An unexpected error occurred', 'details': str(e)}), 500
    except json.JSONDecodeError as e:
        print(f"JSON decode error occurred: {e}")
        return jsonify({'error': 'Failed to decode JSON', 'details': str(e)}), 500
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

# Health check endpoint

@app.route('/training_mdoel', methods=['GET'])
def training_mdoel():
    if not leaning_ai.fetch_data():
        print("Training aborted: No data fetched.")
        return jsonify({'message': 'Training aborted due to lack of data'}), 400
    
    if leaning_ai.train_model():
        return jsonify({'message': 'Model training completed successfully'}), 200
    else:
        return jsonify({'message': 'Training aborted: No data available or an error occurred.'}), 400

@app.route('/health', methods=['GET'])
def health_check():
    return jsonify({"status": "healthy"}), 200

# Root endpoint
@app.route('/', methods=['GET'])
def root():
    return jsonify({"message": "Flask server is running"}), 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)