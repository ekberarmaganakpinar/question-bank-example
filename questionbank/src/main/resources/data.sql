insert into STUDENT(id,first_name,last_name,student_number) values (1001,'ahmed','hassan',758);
insert into TEST(id,test_name) values (3001,'Ilkogretim Matematik');
insert into QUESTION(id,test_id,correct_answer,options,question_text) values (2001,3001,'5','2,3,4','2+3 = ?');
insert into STUDENT_TEST(score,id,student_id,test_id) values (5,4001,1001,3001);
insert into STUDENT(id,first_name,last_name,student_number) values (1002,'sergen','yalcin',66);
insert into TEST(id,test_name) values (3002,'Ortaogretim Matematik');
insert into QUESTION(id,test_id,correct_answer,options,question_text) values (2002,3002,'6','2,3,4','7-1 = ?');
insert into STUDENT_TEST(score,id,student_id,test_id) values (0,4002,1002,3002);