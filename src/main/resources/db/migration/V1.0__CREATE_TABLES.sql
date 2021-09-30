--- SEQUENCES ---
CREATE SEQUENCE EASYCHAT.USUARIO_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;
CREATE SEQUENCE EASYCHAT.DADOS_PESSOAIS_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;
CREATE SEQUENCE EASYCHAT.AVISOS_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;
CREATE SEQUENCE EASYCHAT.CONVERSA_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;
CREATE SEQUENCE EASYCHAT.MENSAGEM_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;
CREATE SEQUENCE EASYCHAT.FILE_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;

-- TABLES --

CREATE TABLE EASYCHAT.DADOS_PESSOAIS
(
    ID_DADOS_PESSOAIS BIGINT             NOT NULL PRIMARY KEY DEFAULT nextval('DADOS_PESSOAIS_SEQ'),
    NOME              VARCHAR(100),
    USERNAME          VARCHAR(20) UNIQUE NOT NULL,
    DT_NASCIMENTO     DATE,
    DT_CADASTRO       TIMESTAMP,
    FOTO              VARCHAR(50)
);

CREATE TABLE EASYCHAT.USUARIO
(
    ID_USUARIO        BIGINT              NOT NULL PRIMARY KEY DEFAULT NEXTVAL('USUARIO_SEQ'),
    EMAIL             VARCHAR(100) UNIQUE NOT NULL,
    PASSWORD          VARCHAR(60)         NOT NULL,
    ID_DADOS_PESSOAIS BIGINT              NOT NULL,
    CONSTRAINT FK_USUARIO_ID_DADOS_PESSOAIS FOREIGN KEY (ID_DADOS_PESSOAIS) REFERENCES EASYCHAT.DADOS_PESSOAIS (ID_DADOS_PESSOAIS)
);

CREATE TABLE EASYCHAT.AVISOS
(
    ID_AVISOS   BIGINT       NOT NULL PRIMARY KEY DEFAULT NEXTVAL('USUARIO_SEQ'),
    ID_USUARIO BIGINT       NOT NULL,
    ID_CONTATO BIGINT,
    TIPO       SMALLINT     NOT NULL,
    DESCRICAO  VARCHAR(255) NOT NULL,
    VISTO      BOOLEAN                           DEFAULT FALSE,
    CONSTRAINT FK_AVISOS_ID_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES EASYCHAT.USUARIO (ID_USUARIO),
    CONSTRAINT FK_AVISOS_ID_CONTATO FOREIGN KEY (ID_USUARIO) REFERENCES EASYCHAT.USUARIO (ID_USUARIO)
);

CREATE TABLE EASYCHAT.CONVERSA
(
    ID_CONVERSA BIGINT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('CONVERSA_SEQ'),
    NOME        VARCHAR(100)
);

CREATE TABLE EASYCHAT.USUARIO_CONVERSA
(
    ID_USUARIO  BIGINT NOT NULL,
    ID_CONVERSA BIGINT NOT NULL,
    CONSTRAINT PK_USUARIO_ID_USUARIO_ID_CONVERSA PRIMARY KEY (ID_USUARIO, ID_CONVERSA),
    CONSTRAINT FK_USUARIO_CONVERSA_ID_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES EASYCHAT.USUARIO (ID_USUARIO),
    CONSTRAINT FK_USUARIO_CONVERSA_ID_CONVERSA FOREIGN KEY (ID_CONVERSA) REFERENCES EASYCHAT.CONVERSA (ID_CONVERSA)
);


CREATE TABLE EASYCHAT.MENSAGEM
(
    ID_MENSAGEM      BIGINT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('MENSAGEM_SEQ'),
    CONTEUDO         VARCHAR(4000),
    DT_MENSAGEM      TIMESTAMP,
    ID_CONVERSA      BIGINT,
    ID_USUARIO_ENVIO BIGINT,
    CONSTRAINT FK_MENSAGEM_ID_CONVERSA FOREIGN KEY (ID_CONVERSA) REFERENCES EASYCHAT.CONVERSA (ID_CONVERSA),
    CONSTRAINT FK_MENSAGEM_ID_USUARIO_ENVIO FOREIGN KEY (ID_USUARIO_ENVIO) REFERENCES EASYCHAT.USUARIO (ID_USUARIO)
);

CREATE TABLE EASYCHAT.FILE
(
    ID_FILE     BIGINT       NOT NULL PRIMARY KEY DEFAULT NEXTVAL('FILE_SEQ'),
    NOME        VARCHAR(100) NOT NULL,
    OBJETO      VARCHAR(100) NOT NULL,
    ID_MENSAGEM BIGINT       NOT NULL,
    CONSTRAINT FK_FILE_ID_MENSAGEM FOREIGN KEY (ID_MENSAGEM) REFERENCES EASYCHAT.MENSAGEM (ID_MENSAGEM)
);

CREATE TABLE EASYCHAT.AMIGO_USUARIO
(
    ID_USUARIO       BIGINT NOT NULL,
    ID_USUARIO_AMIGO BIGINT NOT NULL,
    CONSTRAINT PK_AMIGO_USUARIO_ID_USUARIO PRIMARY KEY (ID_USUARIO, ID_USUARIO_AMIGO),
    CONSTRAINT FK_AMIGO_USUARIO_ID_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES EASYCHAT.USUARIO (ID_USUARIO),
    CONSTRAINT FK_AMIGO_USUARIO_ID_USUARIO_AMIGO FOREIGN KEY (ID_USUARIO_AMIGO) REFERENCES EASYCHAT.USUARIO (ID_USUARIO)
)