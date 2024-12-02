ALTER TABLE transacao
ADD COLUMN quantidade_parcelas INT NOT NULL,
ADD COLUMN periodicidade VARCHAR(50) NOT NULL,
ADD COLUMN data_vencimento DATE NOT NULL;

CREATE TABLE parcela (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_parcela INT NOT NULL,
    valor_parcela DECIMAL(15, 2) NOT NULL,
    data_vencimento DATE NOT NULL,
    transacao_id BIGINT,
    CONSTRAINT fk_transacao
        FOREIGN KEY (transacao_id) REFERENCES transacao(id)
        ON DELETE CASCADE
);