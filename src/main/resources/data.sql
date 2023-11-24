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
