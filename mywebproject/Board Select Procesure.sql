USE [Project_Spring]
GO
/****** Object:  StoredProcedure [dbo].[list_board]    Script Date: 2024-02-22 오후 4:21:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- select * from board
-- exec list_board 1, 15
-- select * from board where del = 0
-- set nocount on
-- set statistics io off


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

set @max_post_num = (select count(arr) from board where del = 0);
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
