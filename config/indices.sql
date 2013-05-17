-- nettoyage des tables
DROP TABLE t_meta_music CASCADE;
DROP TABLE t_meta_text CASCADE;
DROP TABLE t_meta_vid CASCADE;
DROP TABLE t_index CASCADE;
DROP TABLE t_metadata CASCADE;

-- création des tables
CREATE TABLE t_metadata (meta_id INT PRIMARY KEY, filename VARCHAR(30) NOT NULL, filepath VARCHAR(120) NOT NULL, permission INT NOT NULL, last_mod DATE NOT NULL);

CREATE TABLE t_index (trg_id VARCHAR(3) NOT NULL,meta_id INT NOT NULL REFERENCES t_metadata(meta_id), PRIMARY KEY(trg_id, meta_id));

CREATE TABLE t_meta_text (auteur VARCHAR(20),titre VARCHAR(40) NOT NULL, extension VARCHAR(3)) INHERITS(t_metadata);

ALTER TABLE t_meta_text ADD PRIMARY KEY(meta_id);

CREATE TABLE t_meta_music (artiste VARCHAR(20),titre VARCHAR(40) NOT NULL, album VARCHAR(20), annee INT) INHERITS(t_metadata);

ALTER TABLE t_meta_music ADD PRIMARY KEY(meta_id);

CREATE TABLE t_meta_vid (realisateur VARCHAR(20), titre VARCHAR(40) NOT NULL,annee INT,meta_id INT NOT NULL) INHERITS(t_metadata);

ALTER TABLE t_meta_vid ADD PRIMARY KEY(meta_id);
