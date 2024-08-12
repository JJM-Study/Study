//document.getElementById('send').addEventListener('click', function(event) {
//event.preventDefault();
//
//var contents = document.querySelector('.a_TextBox').value;
//
//var data = {
//  contents: contents
//};
//
//fetch('/question', {
//  method: 'POST',
//  headers: {
//      "Content-Type": "application/json",
//  },
//  body: JSON.stringify(data)
//})
//.then(response => {
//  if (response.ok) {
//      alert(contents); // For Test
//      window.location.href = '/chat';
//  } else {
//      alert('Error submitting the question');
//  }
//  })
//  .catch(error => {
//    console.error('Error:', error);
//  });
//});



document.addEventListener("DOMContentLoaded", function() {


// Websocket 연결 및 STOMP 클라이언트 설정
var socket = new SockJS('/chat-ws');
var stompClient = Stomp.over(socket);
var boardScroll = document.getElementById('board'); // 질문 답변 div의 스크롤바

stompClient.connect({}, function(frame) {
    console.log('connected: ' + frame);


     // 질문 Send
    stompClient.subscribe('/user/queue/question', function(message) {

    var question = JSON.parse(message.body);
    console.Log('question test');
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
       console.error('STOMP connection error: ', error);
    });

//    // 저장 성공 메세지 수신
//    stompClient.subscribe('/user/queue/success/question', function(message) {
//        var qs_sucess = message.body;
//        confirm.log(qs_sucess);
//
//
//
//    });


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
   var questionContainer = document.getElementById('question');
   var questionElement = document.createElement('div');

   questionContainer.className = 'question';
   ///questionElement.innerHTML = `<p class="question_p">` + question + `</p><div class="answer"></div>`;
   questionElement.innerHTML = `<p class="question_p">` + 'TEST' + `</p><div class="answer"></div>`;
   //questionElement.innerHTML = `<p class="question_p" th:text="asdsadada"></p>`;


   questionContainer.appendChild(questionElement);



   if (boardScroll) {
       boardScroll.scrollTop = boardScroll.scrollHeight;
   }

}

// 저장 성공 질문 수신




// 답변 Display
function showAnswer(answer) {


    // 'answer' 요소들을 선택
    var answers = document.getElementsByClassName('answer');

    for (var i = 0; i < answers.length; i++) { 
        var answerContainer = answers[i];

        // data-question-id 속성에 접근
        var questionId = answerContainer.dataset.questionId;

        // 답변의 질문 ID와 일치하는지 확인
        if (questionId == answer.question.id) {
            var answerElement = document.createElement('p');
            answerElement.className = 'answer_p';
            answerElement.textContent = answer.contents;
            
            answerContainer.appendChild(answerElement);

            break;
         }


        }
    }

    if (boardScroll) {      // 로드 시 스크롤바 아래로 내리도록 함. 나중에 글 추가될 때 마다 스크롤바가 내려가도록 수정할 것.
        boardScroll.scrollTop = boardScroll.scrollHeight;
    }


});