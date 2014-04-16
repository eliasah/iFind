-- nettoyage des tables
DROP TABLE t_index CASCADE;
DROP TABLE t_metadata CASCADE;

-- création des tables
CREATE TABLE t_metadata (filepath VARCHAR(120) PRIMARY KEY, extension VARCHAR(4), permission VARCHAR(9) NOT NULL, last_mod DATE NOT NULL);

CREATE TABLE t_index (trg_id VARCHAR(3) NOT NULL,meta_id VARCHAR(120) NOT NULL REFERENCES t_metadata(filepath) ON UPDATE CASCADE, PRIMARY KEY(trg_id, meta_id));
