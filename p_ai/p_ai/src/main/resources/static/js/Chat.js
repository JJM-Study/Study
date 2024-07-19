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





// Websocket 연결 및 STOMP 클라이언트 설정
var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log('connected: ' + frame);

    // 질문에 대한 답변을 받을 때의 처리
    stompClient.subscribe('/user/queue/answers', function(message) {
        var answer = JOSN.parse(message.body);
        showAnswer(answer);
    });
});

document.getElementById('send').addEventListener('click', function() {
    var questionInput = document.getElementById('questionInput');
    var question = question.value;
    if (question) {
        sendQuestion(question);
        questionInput.value = ''; // 질문 전송 후 입력 필드 비우기
    }
});

function sendQuestion(question) {
    
}

var answer = JSON.parse(messg);




stompClient.connect({}, function (frame) {
    setConnected(true);
    console.log('Connected:' + frame);
    stompClient.subscribe('/user/queue/answers', function (message) {
            console.log('Received:', JSON.parse(message.body));
        });
    });

    // 질문을 서버에 보내는 함수
    function saveQuestion(question) {
        stompClient.send("/app/question", {}, JSON.stringify(question));

}