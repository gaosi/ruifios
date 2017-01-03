UPDATE `mysql`.`user` SET `Password`=PASSWORD('cybersky.db.ROOT@305') WHERE `User`='cybersky';

grant all privileges on cybersky.* to 'cybersky'@'localhost' identified by 'cybersky';

FLUSH PRIVILEGES;
REPAIR table `mysql`.`db`, `mysql`.`user`;

-- select user, host, password from  `mysql`.`user` ;