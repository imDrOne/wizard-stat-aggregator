-- liquibase formatted sql

-- changeset Andrey Tikholoz:20260225190139
-- comment: add_set_code_to_card_limited_stats

ALTER TABLE card_limited_stats
    ADD COLUMN set_code VARCHAR(10) NOT NULL DEFAULT '';

UPDATE card_limited_stats SET set_code = '' WHERE set_code IS NULL;

ALTER TABLE card_limited_stats
    ALTER COLUMN set_code DROP DEFAULT;

DROP INDEX IF EXISTS uq_card_stats_mtga_id_match_type;

ALTER TABLE card_limited_stats
    ADD CONSTRAINT uq_card_limited_stats_mtga_id_set_code_match_type
        UNIQUE (mtga_id, set_code, match_type);
