delete from transaction;
delete from account;
delete from client;

insert into client (id, client_name) VALUES (10, 'Steve');

insert into account(id, account_name, client_id) VALUES
(100, 'From account', 10);

insert into transaction(amount, currency, from_account_id, to_account_id)
values (100, 'USD', 100, null);

insert into transaction(amount, currency, from_account_id, to_account_id)
values (200, 'USD', 100, null);

insert into transaction(amount, currency, from_account_id, to_account_id)
values (599, 'USD', null, 100);
