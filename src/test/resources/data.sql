insert into person(`id`,`name`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (1,'martin',1991,4,25);
insert into person(`id`,`name`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (2,'david',1992,7,21);
insert into person(`id`,`name`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (3,'dennis',1993,10,15);
insert into person(`id`,`name`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (4,'sophia',1994,8,31);
insert into person(`id`,`name`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (5,'benny',1995,12,23);

insert into person(`id`,`name`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`,`job`,`hobby`,`phone_number`,`address`)
     VALUES (6,'tony',1991,7,10,'officer','reading','010-2222-5555','서울');
insert into person(`id`,`name`,`deleted`)
     VALUES (7,'andrew',true);


-- insert into block(`id`,`name`) values (1,'dennis');
-- insert into block(`id`,`name`) values (2,'sophia');
--
-- update person set block_id = 1 WHERE id = 3;
-- UPDATE person
--    SET block_id = 2
--   WHERE id = 4;

--ctrl + F -> alt + enter  == 다중선택
--데이터가 복잡해질 경우 각 테스트에서 데이터를 만들어서 test, rollback 을 하여 테스트
