-- http://databaser.net/moniwiki/wiki.php/%EA%B3%84%EC%B8%B5%ED%98%95%EA%B2%8C%EC%8B%9C%ED%8C%90 참고


-- set nocount on -- 쿼리문 또는 프로시저의 영향을 받은 행 수를 나타내는 메세지가 결과 집합의 일부로 반환되지 않도록 한다.
-- set statistics io off -- ON으로 할 시 통계 정보가 표시


/* create proc list_board
	@page_num int,
	@page_row_cnt int

as
 */

	declare
		@max_post_num int,
		@begin int,
		@end int,
		@end_ck int,
		@cnt int,
		@page_num int,
		@page_row_cnt int

set @page_num = 1;
set @page_row_cnt = 50;
set @max_post_num = (select max(postNO) from board);
set	@begin = @max_post_num - ((@page_num) * @page_row_cnt);
set @end = @max_post_num - ((@page_num - 1) * @page_row_cnt);
set @end_ck = case when @end > 0 then 0 else 1 end;
set @begin = case when @end > 0 then @begin else 1 end;
set @end = case when @end > 0 then @end else @page_row_cnt end;


IF OBJECT_ID('tempdb..#Temp') IS NOT NULL
BEGIN
       DROP TABLE #Temp
END

if @end_ck = 0 -- 마지막페이지가 아니면 ...
begin
	with t_recursion_board(level, postNO, parentNO, del, title, content, writedate, imageFileName, id, sort)
	as
	(
		--Root
		select 0 as level, postNO, parentNO, del, title, content, writedate, imageFileName, id, cast(postNO as varchar(8000)) as sort
		from board
		where parentNO = 0 -- root의 조건
		and postNO between @begin and @end

		union all

		select b.level + 1 as level, a.postNO, a.parentNO, a.del, a.title, a.content, a.writedate, a.imageFileName, a.id, cast(b.sort as varchar(4000)) + cast(a.postNO as varchar(4000))
		from board as a inner join t_recursion_board as b -- 재귀 쿼리
		on a.parentNO = b.postNO
		and a.postNO between @begin and @end
	)
	select top(@page_row_cnt)
		level,
		postNO,
		parentNO,
		del,
		title,
		content,
		writedate,
		imageFileName,
		id,
		case when parentNO > 0 then '[' + cast(parentNO as varchar) + ']' else '' end + cast(replicate(' RE: ', level) + cast(title as varchar) as varchar(250)) as tree_title
		from t_recursion_board as a
		order by
			postNO desc,
			case when level = 0 then '0' else sort end
			option(maxdop 1);
end else -- 마지막 페이지일 때
begin
	with t_recursion_board(level, postNO, parentNO, del, title, content, writedate, imageFileName, id, sort)
	as
	(
		select 0 as level, postNO, parentNO, del, title, content, writedate, imageFileName, id, cast(postNO as varchar(8000)) as sort
		from board
		where parentNO = 0 -- root의 조건
		and postNO between @begin and @end

		union all

		select b.level + 1 as level, a.postNO, a.parentNO, a.del, a.title, a.content, a.writedate, a.imageFileName, a.id, cast(b.sort as varchar(4000)) + cast(a.postNO as varchar(4000))
		from board a inner join t_recursion_board as b -- 재귀 쿼리
		on a.parentNO = b.postNO
		and a.postNO between @begin and @end
	)

	select
		level,
		postNO,
		parentNO,
		del,
		title,
		content,
		writedate,
		imageFileName,
		id,
		case when parentNO > 0 then '[' + cast(parentNO as varchar) + ']' else '' end + cast(replicate(' RE: ', level) + cast(title as varchar) as varchar(250)) as tree_title,
		row_number() over(order by postNO desc, case when level = 0 then '0' else sort end) rownum
	into #temp
	from t_recursion_board as a
	order by postNO, case when level = 0 then '0' else sort end option(maxdop 1);

	set @cnt = @@rowcount;

	select
		level,
		postNO,
		parentNO,
		del,
		title,
		content,
		writedate,
		imageFileName,
		id
	from #temp
	where rownum > (@cnt / @page_row_cnt) * @page_row_cnt;
end
go