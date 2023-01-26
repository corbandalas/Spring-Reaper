CREATE TABLE IF NOT EXISTS public.client
(
    id         SERIAL NOT NULL,
    client_name  varchar NOT NULL,

    CONSTRAINT client_pk PRIMARY KEY (id),
    CONSTRAINT name UNIQUE (client_name)
);

CREATE TABLE IF NOT EXISTS public.account
(
    id         SERIAL NOT NULL,
    account_name   varchar NULL,
    client_id BIGINT REFERENCES client(id),

    CONSTRAINT account_pk PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.transaction
(
    id         SERIAL NOT NULL,
    amount   bigint NULL,
    currency varchar(3) NOT NULL ,
    from_account_id BIGINT REFERENCES account(id),
    to_account_id BIGINT REFERENCES account(id),

    CONSTRAINT transaction_pk PRIMARY KEY (id)
);

-- DELETE FROM public.client;
-- INSERT INTO public.speakers (id, firstname, lastname, talkname, likes, created, updated) VALUES (1, 'John', 'Doe', 'Spring best practice',  0, now(), now());
-- DELETE FROM jpoint.history;