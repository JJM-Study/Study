<h1>Spring FrameWork 계층형 게시판</h1>

<h3>만든 목적<h3>

 * 기초 웹 개발 지식 및 JSP,스프링 프레임워크의 학습을 위한 게시판 구현


<h3>프로젝트 진행 방식<h3>

 * 개인 프로젝트

<h2>사용 기술 및 개발 환경</h2>

 * O/S : window 10
 * DB: MSSQL
 * Server : Apache-Tomcat-9.0.46
 * Tool : Eclipse, Git(SourceTree), GitHub, SQL Server Management Studio
 * Language : Spring FrameWork, JSP, JavaScript, CSS

<br>

<h2>내용</h2>

<h3>회원가입</h3>
<hr>
<br>

![Image](https://github.com/JJM-Study/jjm/blob/173f10c7a6fb728939400b4f4b8aae500e1f30b8/Repositoiry%20Resources/singup.png)

비동기 처리 방식을 통한 데이터 INSERT

<br>

<h3>글쓰기</h3>
<hr>

![Image](https://github.com/JJM-Study/jjm/blob/173f10c7a6fb728939400b4f4b8aae500e1f30b8/Repositoiry%20Resources/Writing%20-%201.png)

<br>

<h3>파일 업로드 및 다운로드</h3>
<hr>

![Image](https://github.com/JJM-Study/jjm/blob/173f10c7a6fb728939400b4f4b8aae500e1f30b8/Repositoiry%20Resources/File%20Upload.jpg)

업로드

<br>

![Image](https://github.com/JJM-Study/jjm/blob/7e9e751d948bc2b536a92a3f26a4f49362ce82db/Repositoiry%20Resources/Download.png)

다운로드

<br>

<h3>글 등록</h3>
<hr>

![Image](https://github.com/JJM-Study/jjm/blob/494cb5754aa506b560bd07e089eeaecd00a587a5/Repositoiry%20Resources/Writing%20-%202.jpg)

<br>

<h3> 게시글 클릭 </h3>

![Image](https://github.com/JJM-Study/jjm/blob/da8714e33c56846bc507ac328f4f523d1aa49115/Repositoiry%20Resources/Detail2.png)

게시글 클릭 시 수정, 삭제 및 답글 작성 가능​

<br>

<h3> 답글 </h3>
<hr>

<br>

![Image](https://github.com/JJM-Study/jjm/blob/71f26328c02c42da5b30c21816b6078651a7d2f0/Repositoiry%20Resources/Reply%20Reply%20Reply.png)

2번 글에 대한 답글 시 POST NO 2를 부모로 하는 3번 포스팅을 생성하고, 3번 글에 대한 답글 시
2번을 부모로 하는 게시글이 추가됩니다.

답글은 [2]와 같이 답변한 게시글 번호가 붙고 계층형 답글을 할 때마다 RE: 가 추가 됩니다.

<br>
<br>

이상 스프링 프레임워크로 구현한 게시판이며, 간단한 CURD 게시판을 구현하며 스프링 부트의 사용 방법을 익혔습니다.

<br>

https://github.com/JJM-Study/Study/blob/master/Board/ReadMe.md
