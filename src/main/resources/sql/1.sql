CREATE TABLE `CSALADOK` (
  `ID`     INT(11) NOT NULL AUTO_INCREMENT,
  `EMAIL1` VARCHAR(100),
  `EMAIL2` VARCHAR(100),
  PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `DIAKOK` (
  `ID`        INT(11)      NOT NULL AUTO_INCREMENT,
  `NEV`       VARCHAR(100) NOT NULL,
  `OSZTALY`   VARCHAR(10)  NOT NULL,
  `CSALAD_ID` INT(11)      NOT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`CSALAD_ID`) REFERENCES CSALADOK(`ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `SZERZODESEK` (
  `ID`                                       INT(11)      NOT NULL AUTO_INCREMENT,
  `TAMOGATO`                                 VARCHAR(100) NOT NULL,
  `MUKODESI_KOLTSEG_TAMOGAS`                 INT(11)      NOT NULL,
  `EPITESI_HOZZAJARULAS`                     INT(11)      NOT NULL,
  `CSALAD_ID`                                INT(11)      NOT NULL,
  `EPITESI_HOZZAJARULAS_INDULO_EGYENLEG`     INT(11)      NOT NULL,
  `MUKODESI_KOLTSEG_TAMOGAS_INDULO_EGYENLEG` INT(11)      NOT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`CSALAD_ID`) REFERENCES CSALADOK(`ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `BEFIZETESEK` (
  `ID`                  INT(11)      NOT NULL AUTO_INCREMENT,
  `IMPORT_FORRAS`       VARCHAR(100) NOT NULL,
  `IMPORT_IDOPONT`      DATETIME     NOT NULL,
  `KONYVELESI_NAP`      DATE         NOT NULL,
  `BEFIZETO_NEV`        VARCHAR(100) NOT NULL,
  `BEFIZETO_SZAMLASZAM` VARCHAR(100) NOT NULL,
  `OSSZEG`              INT          NOT NULL,
  `KOZLEMENY`           VARCHAR(256),
  `STATUSZ`             VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `JOVAIRAS` (
  `ID`              INT(11)      NOT NULL AUTO_INCREMENT,
  `SZERZODES_ID`    INT(11)      NOT NULL,
  `MEGNEVEZES`      VARCHAR(256) NOT NULL,
  `TIPUS`           VARCHAR(100) NOT NULL,
  `OSSZEG`          INT          NOT NULL,
  `BEFIZETES_ID`    INT(11),
  `KONYVELESI_NAP`  DATE         NOT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`SZERZODES_ID`) REFERENCES SZERZODESEK(`ID`),
  FOREIGN KEY (`BEFIZETES_ID`) REFERENCES BEFIZETESEK(`ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;