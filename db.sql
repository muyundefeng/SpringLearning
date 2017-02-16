  CREATE TABLE `student` (
`ID` varchar(40) not null  PRIMARY KEY,
  `name` varchar(40) NOT NULL,
  `age` varchar(255) NOT NULL,
`address` varchar(255) NOT NULL,
`score` varchar(255) NOT NULL)
ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
  CREATE TABLE `personinfo` (
`ID` varchar(40) not null  PRIMARY KEY,
  `home_address` varchar(40) NOT NULL,
  `family_number` varchar(255) NOT NULL,
`language` varchar(255) NOT NULL)
ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;