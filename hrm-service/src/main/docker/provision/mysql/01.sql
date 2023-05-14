## HRM USER
CREATE USER 'hrm-user'@'%' IDENTIFIED BY 'hrm-password';
GRANT ALL PRIVILEGES ON `hrm-database`.* TO 'hrm-user'@'%';

## QUERYDB USER
CREATE USER 'querydb-user'@'%' IDENTIFIED BY 'querydb-password';
GRANT ALL PRIVILEGES ON `querydb`.* TO 'querydb-user'@'%';

CREATE DATABASE IF NOT EXISTS `querydb`;
CREATE DATABASE IF NOT EXISTS `hrm-database`;
