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

stompClient.connect({}, function(frame) {
    console.log('connected: ' + frame);

    // 질문에 대한 답변을 받을 때의 처리
    stompClient.subscribe('/user/queue/answers', function(message) {
        var answer = JSON.parse(message.body);
        showAnswer(answer);
    });
});

document.getElementById('send').addEventListener('click', function() {
    var questionInput = document.getElementById('questionInput');
    var question = questionInput.value;
    if (question) {
        sendQuestion(question);
        questionInput.value = ''; // 질문 전송 후 입력 필드 비우기
    }
});

function sendQuestion(question) {
    stompClient.send("/app/question", {}, JSON.stringify({'contents' : question}));
}

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



});