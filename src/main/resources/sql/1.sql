CREATE TABLE `SZERZODESEK` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TAMOGATO_NEVE` varchar(100) NOT NULL,
  `MUKODESI_KOLTSEG_TAMOGAS` int(11) NOT NULL,
  `EPITESI_HOZZAJARULAS` int(11) NOT NULL,
  `CSALAD_ID` int(11) NOT NULL,
  `EPITESI_HOZZAJARULAS_INDULO_EGYENLEG` int(11) NOT NULL,
  `MUKODESI_KOLTSEG_TAMOGAS_INDULO_EGYENLEG` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `DIAKOK` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NEV` varchar(100) DEFAULT NULL,
  `OSZTALY` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `CSALADOK` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL1` varchar(100) DEFAULT NULL,
  `EMAIL2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



