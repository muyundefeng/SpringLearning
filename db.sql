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

CREATE TABLE `article` (
`article_id` varchar(255) not null  PRIMARY KEY,
  `article_title` varchar(255) NOT NULL,
  `article_content` longtext NOT NULL,
`create_time` Date NOT NULL,
`article_orgina` varchar(255))
ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE student(
sno INT(15) NOT NULL PRIMARY KEY,
classId int(15) not null,
sname VARCHAR(50),
sex CHAR(2) CHECK(sex IN('男','女')),
dept VARCHAR(25),
birth DATE,
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE class(
classId INT(15) NOT NULL PRIMARY KEY,
className varchar(255) not null
)ENGINE=INNODB DEFAULT CHARSET=utf8;