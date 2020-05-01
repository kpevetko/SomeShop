insert into products (name,description,cost,number) values ('пища','похоже на еду',10,50000);
insert into products (name,description,cost,number) values ('яблоко','все еще похоже на еду',5,200);
insert into products (name,description,cost,number) values ('велосипед','возможно, на этом можно прокатиться',300,200);
insert into products (name,description,cost,number) values ('вилка','этим можно есть',20,10000);
insert into products (name,description,cost,number) values ('ложка','этим можно есть',20,10000);
insert into products (name,description,cost,number) values ('пова','это же человек, лучше его не есть',20,100);
insert into products (name,description,cost,number) values ('трактор','на этом можно уехать...',1000,5);
insert into users (username, password, enabled) values ('admin','admin',true);
insert into users (username, password, enabled) values ('user','user',true);
insert into authorities (username, authority) values ('admin','ROLE_ADMIN');
insert into authorities (username, authority) values ('user','ROLE_USER');



