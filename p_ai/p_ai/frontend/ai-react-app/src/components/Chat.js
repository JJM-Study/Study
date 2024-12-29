<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Chat</title>
    <link th:href="@{/css/Chat.css}" rel="stylesheet">
</head>
<body>
<h1 th:text="'Chat'"></h1>
<p th:text="${question != null ? #lists.size(question) : 0}"></p>
<div id="board">
    <div id="questions">
        <div class="question" th:each="question : ${question}">   <!--    &lt;!&ndash; each를 돌아가며 질문을 하나씩 띄우고, 그 질문 아이디에 딸린 answer의 개수가 2개 이상일 경우, 일단은 답변 1, 답변2 와 같은 식으로 순차대로 띄우도록. &ndash;&gt;-->
            <p class="question_p" th:text="${question.contents}">/p>
            <div class = "answer" th:each="answer : ${answer}" th:if="${answer.question.id == question.id}" data-question-id="${question.id}">
             <p class="answer_p" th:text="${answer.contents}"></p>
            </div>
        </div>
    </div>
</div>
<div id="chat_input">
    <textarea id="questionInput" class="a_TextBox" placeholder="Talking to AI"></textarea>
    <button type="button" id="send">SEND</button>
</div>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
 <script type="text/javascript" th:src="@{/js/Chat.js}"></script>
</body>
</html>