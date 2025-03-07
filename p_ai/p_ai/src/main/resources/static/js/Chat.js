document.addEventListener("DOMContentLoaded", function() {
    let jwtToken = null;
    var stompClient = null;
    var boardScroll = document.getElementById('board'); // 질문 답변 div의 스크롤바
    var questionElement; // 질문 요소 (전역 변수로 설정)

    // 자동 JWT 발급 요청
    fetch('/api/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "username": "user"
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        jwtToken = data.token;
        connectWebSocket(jwtToken);
    })
    .catch(error => console.error('Error fetching token:', error));

    function connectWebSocket(token) {
        // WebSocket 연결 및 STOMP 클라이언트 설정
        //var socket = new SockJS('/chat-ws');
        var socket = new SockJS('/chat-ws?token=' + token); // 2024/11/19 수정
        stompClient = Stomp.over(socket);

        stompClient.connect(
            {},
            function(frame) {
                console.log('Socket connected: ' + frame);

            // 질문에 대한 확인 메시지를 수신하는 부분
            stompClient.subscribe('/user/queue/question/confirmation', function(message) {
                var question = JSON.parse(message.body);
                console.log('Received confirmation:', question);
                if (question && question.contents) {
                    showQuestion(question);
                }
            });

            // 질문에 대한 답변을 받을 때의 처리
            stompClient.subscribe('/user/queue/answers', function(message) {
                const answer = JSON.parse(message.body);
                showAnswer(answer);
            });

        }, function(error) {
            console.log('STOMP connection error: ', error);
        });
    }

    document.getElementById('send').addEventListener('click', function() {
        if (stompClient && stompClient.connected) { // STOMP 클라이언트 연결 상태 체크
            var questionInput = document.getElementById('questionInput');
            var question = questionInput.value;
            if (question) {
                sendQuestion(question);
                questionInput.value = ''; // 질문 전송 후 입력 필드 비우기
            } 
        } else {
            console.error("STOMP client is not connected yet");
        }
    });

    function sendQuestion(question) {
        if (stompClient && stompClient.connected) { // 연결 여부 재확인 2024/11/19 추가
            stompClient.send("/app/question", {}, JSON.stringify({'contents' : question}));
        } else {
            console.error("STOMP client is not connected yet.");
        }
    }

    // 질문 Display
    function showQuestion(question) {
        var questionContainer = document.getElementById('questions');
        questionElement = document.createElement('div');

        questionElement.className = 'question';
        questionElement.innerHTML = `<p class="question_p" data-question="${question.id}">${question.contents}</p>`;
        questionContainer.appendChild(questionElement);

        if (boardScroll) {
            boardScroll.scrollTop = boardScroll.scrollHeight;
        }
    }

    // 답변 Display
    function showAnswer(answer) {
        var answerContainer = document.createElement('div');
        var answerElement = document.createElement('p');

        answerElement.className = 'answer_p';
        answerElement.textContent = answer.contents;

        questionElement.appendChild(answerContainer);
        answerContainer.appendChild(answerElement);

        if(boardScroll) {
            boardScroll.scrollTop = boardScroll.scrollHeight;
        }
    }

    if (boardScroll) {      // 로드 시 스크롤바 아래로 내리도록 함. 나중에 글 추가될 때 마다 스크롤바가 내려가도록 수정할 것.
        boardScroll.scrollTop = boardScroll.scrollHeight;
    }
});
