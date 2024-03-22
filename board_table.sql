--drop table board

create table board(
  --postNO int identity(1,1) primary key nonclustered
	postNO int primary key
,	arr int
,   parentNO int
,	del int
,	title varchar(100)
,	content varchar(4000)
,	writeDate datetime not null default getdate()
,   imageFileName varchar(100)
,	id varchar(10)
);


/* insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (1, 1,0, 'aaa 답',	'test', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (1, 1,0, 'aaa 답2',	'te2', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (2, 0,0, 'bbb',	'testbbbbbb', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (3, 0,0, 'ccc',	'ㅁㅇㄻㄹ', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (4, 0,0, 'ddd',	'dsfaasfd', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (5, 0,0, 'tetatat',	'gadsag', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (6, 0,1, 'dfasdf',	'hasdg', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (3, 1,0, 'ccc 답',	'fadsasdf', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (7, 0,0, 'eee',	'fdsafas', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (8, 0,0, 'fff',	'assss', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (9,0,1, 'ggg',	'ddddd', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (10, 0,0, 'hhh',	'hhhhh', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (11, 0,0, 'iii',	'ht', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (12, 0,1, 'zza',	'tht', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (13, 0,0, 'wer',	'ttttt', getdate(), null, 'test')
insert into board (arr, parentNO, del, title, content, writeDate, imageFileName, id) values (14, 0,0, 'dfasdfasdfas',	'ttt', getdate(), null, 'test') */


insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (1, 1, 1,0, 'aaa 답',	'test', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (2, 1, 1,0, 'aaa 답2',	'te2', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (3, 2, 0,0, 'bbb',	'testbbbbbb', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (4, 3, 0,0, 'ccc',	'ㅁㅇㄻㄹ', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (5, 4, 0,0, 'ddd',	'dsfaasfd', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (6, 5, 0,0, 'tetatat',	'gadsag', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (7, 6, 0,1, 'dfasdf',	'hasdg', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (8, 3, 1,0, 'ccc 답',	'fadsasdf', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (9, 7, 0,0, 'eee',	'fdsafas', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (10, 8, 0,0, 'fff',	'assss', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (11, 9,0,1, 'ggg',	'ddddd', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (12, 10, 0,0, 'hhh',	'hhhhh', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (13, 11, 0,0, 'iii',	'ht', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (14, 12, 0,1, 'zza',	'tht', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (15, 13, 0,0, 'wer',	'ttttt', getdate(), null, 'test')
insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values (16, 14, 0,0, 'dfasdfasdfas',	'ttt', getdate(), null, 'test')
--insert into board (postNO, arr, parentNO, del, title, content, writeDate, imageFileName, id) values ((select max(postNO) + 1 from board), (select max(postNO) + 1 from board), 0,0, 'dfasdfasdfas',	'ttt', getdate(), null, 'test')


select * from board

