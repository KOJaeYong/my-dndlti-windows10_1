insert into user (id,user_id,password,name,email) values(1,'mhanaro', '1234', 'dndlti', 'naver@m.com');
insert into user (id,user_id,password,name,email) values(2,'111', '111', '111', 'm@m.com');

insert into question (id,writer_id,title,contents,create_date, count_of_answer) values(1,1,'첫번째 질문','자신이 원하는 것은 무엇인가?',current_timestamp(),0);
insert into question (id,writer_id,title,contents,create_date, count_of_answer) values(2,2,'두번째 질문','오늘 날씨는',current_timestamp(),0);