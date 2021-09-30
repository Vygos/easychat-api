INSERT INTO DADOS_PESSOAIS (nome, username, dt_nascimento, dt_cadastro)
VALUES ('Gojou Satoru', 'gojou', '2000-01-02', '2020-10-12 12:20'),
       ('Todou', 'todou', '2000-01-02', '2020-10-12 12:20'),
       ('Kakashi Uchiha', 'kakashi', '2000-01-02', '2020-10-12 12:20');

INSERT INTO USUARIO (email, password, id_dados_pessoais)
VALUES ('gojou@gmail.com', '$2a$10$LxAyO1TGAcjNpPZvHsKW9en39IYoX.3nUcxSMf3u0mKtOX7gW.nOW', 1),
       ('todou@gmail.com', '$2a$10$ecKDYsAtgk5RvT1a.TRo2eCnM1INkZIRqYotDdjYSEy4lcD2meURm', 2),
       ('kakashi@gmail.com', '$2a$10$ETeeP2reqUIkp3shEbpFYOakCNtAuMOCrJzzqnexWTYM0sHKaS6uK', 3);
