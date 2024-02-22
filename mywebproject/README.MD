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
 * Language : Spring FrameWork, JSP, JavaScript, CSS

<h2>내용</h2>
---


**회원가입**

![Image](https://github.com/JJM-Study/jjm/blob/173f10c7a6fb728939400b4f4b8aae500e1f30b8/Repositoiry%20Resources/singup.png)

비동기 처리 방식을 통한 데이터 INSERT


**글쓰기**


![Image](https://github.com/JJM-Study/jjm/blob/173f10c7a6fb728939400b4f4b8aae500e1f30b8/Repositoiry%20Resources/Writing%20-%201.png)


**파일 첨부**

![Image](https://github.com/JJM-Study/jjm/blob/173f10c7a6fb728939400b4f4b8aae500e1f30b8/Repositoiry%20Resources/File%20Upload.jpg)


**글 등록**

![Image](https://github.com/JJM-Study/jjm/blob/494cb5754aa506b560bd07e089eeaecd00a587a5/Repositoiry%20Resources/Writing%20-%202.jpg)

** 게시글 클릭 및 답글**


![Image](https://github.com/JJM-Study/jjm/blob/46269574a9aede481663ea9600df0d6a623f7d09/Repositoiry%20Resources/Reply.png)


2번 글에 대한 답글 시 POST NO 2를 부모로 하는 답글 포스팅 생성.



게시판 조회 프로시저

```
ALTER proc [dbo].[list_board]

	@page_num int,
	@page_row_cnt int

as

	declare
		@max_post_num int,
		@begin int,
		@end int,
		@end_ck int,
		@cnt int

set @max_post_num = (select count(arr) from board where del = 0); -- 2023/06/19 수정
set	@begin = @max_post_num - ((@page_num) * @page_row_cnt);
set @end = @max_post_num - ((@page_num - 1) * @page_row_cnt);
set @end_ck = case when @end > 0 then 0 else 1 end;
set @begin = case when @end > 0 then @begin else 1 end;
set @end = case when @end > 0 then @end else @page_row_cnt end;


IF OBJECT_ID('tempdb..#Temp') IS NOT NULL
BEGIN
       DROP TABLE #Temp
END


	;with t_recursion_board(level, postNO, arr, parentNO, del, title, content, writedate, imageFileName, id, sort)
	as
	(
		select 0 as level, postNO, arr, parentNO, del, title, content, writedate, imageFileName, id, cast(postNO as varchar(8000)) as sort
		from board as a
		where parentNO = 0
		and del = 0
		--and rowNum between @begin and @end

		union all

		select b.level + 1 as level, a.postNO, a.arr, a.parentNO, a.del, a.title, a.content, a.writedate, a.imageFileName, a.id, cast(b.sort as varchar(4000)) + cast(a.postNO as varchar(4000))
		from board as a inner join t_recursion_board as b
		on a.parentNO = b.postNO
		where a.del = 0
		--and rowNum between @begin and @end
	)

		select top(@page_row_cnt)
		a.level,
		a.postNO,
		a.arr,
		C.arrNum,
		a.parentNO,
		a.del,
		a.title,
		a.content,
		--writedate,
		convert(varchar(10), writedate, 23) as writeDate,
		imageFileName,
		id,
		case when parentNO > 0 then '[' + cast(parentNO as varchar) + ']' else '' end + cast(replicate(' RE: ', level) + cast(title as varchar) as varchar(250)) as tree_title
		from t_recursion_board a
		FULL OUTER JOIN (select postNO, del, ROW_NUMBER() OVER(ORDER BY arr asc) as arrNum from board where del = 0) as C on C.postNO = a.postNO
		where C.arrNum Between @begin and @end
		and C.del = 0
		order by
			arrNum desc,
			case when level = 0 then '0' else sort end
			option(maxdop 1);
```





