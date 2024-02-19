<h1>Spring FrameWork 계층형 게시판</h1>
---
CRUD와 

만든 목적

 * 기초 웹 개발 지식 및 JSP,스프링 프레임워크의 학습을 위한 게시판 구현
 * 


프로젝트 진행 방식

 * 개인 프로젝트

<h2>사용 기술 및 개발 환경</h2>
---
 * O/S : window 10
 * DB: MSSQL
 * Serve : Apache-Tomcat-9.0.46
 * Tool : Eclipse, Git(SourceTree), GitHub, SQL Server Management Studio
 * Language : Spring FrameWork, 

<h2>내용</h2>
---

pom.xml 중...

<code>
<dependency>
		   <groupId>commons-beanutils</groupId>
		   <artifactId>commons-beanutils</artifactId>
		   <version>1.8.0</version>
		</dependency>
		
		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
			<version>1.2.2</version>
		</dependency>
		
		<dependency>
			<groupId>cglib</groupId>
			<artifactId>cglib-nodep</artifactId>
			<version>2.2</version>
		</dependency>
		
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.2.7</version>
		</dependency>
		
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.2.2</version>
		</dependency>
		
		<dependency>
		 <groupId>com.microsoft.sqlserver</groupId>
		  <artifactId>mssql-jdbc</artifactId>
		  <version>11.2.0.jre11</version>
		</dependency>

		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-jdbc</artifactId>
		    <version>4.2.4.RELEASE</version>
		</dependency>
		
		<!-- file Upload -->
		
		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>2.6</version>
		</dependency>

		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.3.3</version>
		</dependency>
</code>
