-- Criação da tabela Usuario
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Criação da tabela Categoria
CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    usuario_id BIGINT,
    CONSTRAINT fk_categoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Criação da tabela Transacao
CREATE TABLE transacao (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    data DATE NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    usuario_id BIGINT,
    categoria_id BIGINT,
    CONSTRAINT fk_transacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_transacao_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- Criação da tabela MetaFinanceira
CREATE TABLE meta_financeira (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    valor_alvo NUMERIC(10, 2) NOT NULL,
    prazo DATE NOT NULL,
    progresso_atual NUMERIC(10, 2),
    usuario_id BIGINT,
    CONSTRAINT fk_meta_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
