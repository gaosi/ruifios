UPDATE `mysql`.`user` SET `Password`=PASSWORD('***') WHERE `User`='ruifios';

grant all privileges on ruifios.* to 'ruifios'@'localhost' identified by 'ruifios';

FLUSH PRIVILEGES;
REPAIR table `mysql`.`db`, `mysql`.`user`;

-- select user, host, password from  `mysql`.`user` ;