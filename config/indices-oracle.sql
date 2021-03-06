DROP TABLE T_INDEX CASCADE CONSTRAINTS;
DROP TABLE T_METADATA CASCADE CONSTRAINTS;
DROP TABLE T_META_MUSIC CASCADE CONSTRAINTS;
DROP TABLE T_META_TEXT CASCADE CONSTRAINTS;
DROP TABLE T_META_VID CASCADE CONSTRAINTS;



CREATE TABLE T_INDEX (
TRG_ID VARCHAR(3) NOT NULL , 
META_ID NUMBER NOT NULL, 
PRIMARY KEY (TRG_ID,META_ID)
);

CREATE TABLE T_METADATA (
META_ID NUMBER PRIMARY KEY NOT NULL,
FILENAME VARCHAR(30) NOT NULL,
FILEPATH VARCHAR(120) NOT NULL,
PERMISSION NUMBER(3) NOT NULL,
LAST_MOD DATE NOT NULL
);

ALTER TABLE T_INDEX ADD FOREIGN KEY (META_ID) REFERENCES T_METADATA(META_ID);

CREATE TABLE T_META_MUSIC (
ARTISTE VARCHAR(20) NOT NULL,
TITRE VARCHAR(40) NOT NULL,
ALBUM VARCHAR(20),
ANNEE NUMBER(4),
META_ID NUMBER NOT NULL,
FOREIGN KEY (META_ID) REFERENCES T_METADATA(META_ID)
);

CREATE TABLE T_META_TEXT (
AUTEUR VARCHAR(20) NOT NULL,
TITRE VARCHAR(40) NOT NULL,
EXTENSION VARCHAR(3) NOT NULL,
META_ID NUMBER NOT NULL,
FOREIGN KEY (META_ID) REFERENCES T_METADATA(META_ID)
);

CREATE TABLE T_META_VID (
REALISATEUR VARCHAR(20) NOT NULL,
TITRE VARCHAR(40),
ANNEE NUMBER(4),
META_ID NUMBER NOT NULL,
FOREIGN KEY (META_ID) REFERENCES T_METADATA(META_ID)
);