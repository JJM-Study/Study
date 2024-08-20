document.addEventListener("DOMContentLoaded", function() {


// Websocket 연결 및 STOMP 클라이언트 설정
var socket = new SockJS('/chat-ws');
var stompClient = Stomp.over(socket);
var boardScroll = document.getElementById('board'); // 질문 답변 div의 스크롤바
var questionElement; // 2024/08/19 추가

stompClient.connect({}, function(frame) {
    console.log('Socket connected: ' + frame);

    // 질문 Send
    //stompClient.subscribe('/user/queue/question', function(message) {
    stompClient.subscribe('/user/queue/question/confirmation', function(message) {
    var question = JSON.parse(message.body);
    console.log('Received confirmation:', question);

    if (question && question.contents) {
            try {
                 showQuestion(question);

            } catch (e) {
                 console.error('Error parsing message:', e);
            }
       }

    });

    // 질문에 대한 답변을 받을 때의 처리
    stompClient.subscribe('/user/queue/answers', function(message) {
        var answer = JSON.parse(message.body);

        showAnswer(answer);

    });

    }, function(error) {
       console.log('STOMP connection error: ', error);
    });


document.getElementById('send').addEventListener('click', function() {
    var questionInput = document.getElementById('questionInput');
    var question = questionInput.value;
    if (question) {
        sendQuestion(question);
        questionInput.value = ''; // 질문 전송 후 입력 필드 비우기

        //showQuestion();
    }
});

function sendQuestion(question) {
    stompClient.send("/app/question", {}, JSON.stringify({'contents' : question}));

}


// 2024 08 08 진행 중 ...

// 질문 Display
function showQuestion(question) {
   var questionContainer = document.getElementById('questions');
   //var questionElement = document.createElement('div');
   questionElement = document.createElement('div'); // 2024/08/19 수정

   questionContainer.appendChild(questionElement);
   questionElement.className = 'question';
   //questionElement.innerHTML = `<p class="question_p">` + question + `</p>`;
   questionElement.innerHTML = `<p class="question_p" data-question="${question.id}">${question.contents}</p>`; // 2024/08/16 수정. (data- 추가)


   questionContainer.appendChild(questionElement);

   if (boardScroll) {
       boardScroll.scrollTop = boardScroll.scrollHeight;
   }

}

// 저장 성공 질문 수신



// 답변 Display
function showAnswer(answer) {


    // 'answer' 요소들을 선택
    //var answers = document.getElementsByClassName('answer'); // 2024/08/16 주석
    var answerContainer = document.createElement('div'); // 2024/08/19 수정
    var qsId = document.getElementsByClassName('question_p');

    //for (var i = 0; i < answers.length; i++) {  // 2024/08/16 주석
    // var answerContainer = answers[i];  // 2024/08/16 주석

    // answer.question.id 속성에 접근 2024/08/16 추가
    var questionId = answer.questionId;

    // 답변의 질문 ID와 일치하는지 확인
//    if (questionId == qsId) {

        var answerElement = document.createElement('p');

        answerElement.className = 'answer_p';
        answerElement.textContent = answer.contents;

        questionElement.appendChild(answerContainer);
        answerContainer.appendChild(answerElement);

        if(boardScroll) {
            boardScroll.scrollTop = boardScroll.scrollHeight;
        }

//    }
 // }
 }

    if (boardScroll) {      // 로드 시 스크롤바 아래로 내리도록 함. 나중에 글 추가될 때 마다 스크롤바가 내려가도록 수정할 것.
        boardScroll.scrollTop = boardScroll.scrollHeight;
    }


});