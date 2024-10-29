-- Alterando o tipo da coluna "data" na tabela "transacao" para timestamp(6)

ALTER TABLE transacaoV2__Alter_table_transacao_column_data_to_timestamp.sql
ALTER COLUMN data TYPE timestamp(6) USING data::timestamp(6);
