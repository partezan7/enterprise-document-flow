INSERT INTO STATUS (ID, VERSION, NAME)
VALUES (1, 1, 'Работа в офисе'),
       (2, 1, 'Удалённая работа'),
       (3, 1, 'Командировка'),
       (4, 1, 'Отпуск'),
       (5, 1, 'Отпуск по уходу за ребёнком'),
       (6, 1, 'Больничный');
INSERT INTO DEPARTMENT (ID, VERSION, NAME)
VALUES (7, 1, 'Отдел программного обеспечения'),
       (8, 1, 'Руководство'),
       (9, 1, 'Бухгалтерия'),
       (10, 1, 'Канцелярия'),
       (11, 1, 'Конструкторский отдел'),
       (12, 1, 'ПТО');
INSERT INTO EMPLOYEE (ID, VERSION, EMAIL, FIRST_NAME, LAST_NAME, DEPARTMENT_ID, STATUS_ID)
VALUES (13, 1, 'd.savitskiy@oogis.ru', 'Денис', 'Савицкий', 7, 1),
       (14, 1, 'popovich@oogis.ru', 'Василий', 'Попович', 8, 1),
       (15, 1, 'trunov@oogis.ru', 'Эдуард', 'Трунов', 12, 3),
       (16, 1, 'prokaev@oogis.ru', 'Александр', 'Прокаев', 11, 2);
INSERT INTO USR (ID, ACTIVE, PASSWORD, USERNAME)
VALUES (17, true, '$2a$12$DYsvtjM5pot0vkhG042qHueo/Jl84EUqF9vzWZ6Bxkl4USLgrRV6q', 'user'),
       (18, true, '$2a$12$s6IOarPlx9Ge7r5w6akpqOP/ss6ktpVcbwdkgdB/ftHi1x31rmdtK', '123'),
       (19, true, '$2a$12$SqtwdNgRN2kE7L0ec4.8ze16Sd7mN.hMQ77fq0U461t8Ikkr2LhFa', 'admin');
INSERT INTO USER_ROLE (USER_ID, ROLES)
VALUES (17, 'USER'),
       (18, 'ADMIN'),
       (18, 'USER'),
       (19, 'ADMIN');