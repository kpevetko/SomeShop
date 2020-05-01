# SomeShop

Небольшой магазинчик

PostgreSQL, Hibernate, Log4j, Spring (Boot, Data, Security, MVC)

Тут не все так хорошо, например, взаимодействие с разными html по разному (как из кода на страницу так и обратно), ОНО работает и может я когда-то это исправлю, но пока что нет такой необходимости, главное чтобы работало.

Для начала работы не забудьте настроить PostgreSQL, не забудьте подключить Postgresql

    Логин: postgres
    Пароль: 0000
    Host: localhost
    Port: 5432

#Создайте и заполните таблицы через sql-файлы AddNewSomeThingInDB.sql и CreateAllMyTableInDB.sql расположенные в resources\forDB\.

Логи, по умолчанию, сохраняются в пааку D://runApp, для большей информации, смотри файл resources\application.properties
