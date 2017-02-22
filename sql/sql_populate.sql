create database if not exists demo;

use demo;

drop table if exists employees;
drop table if exists history;

CREATE TABLE employees (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(64) NOT NULL,
  department varchar(64) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE history (
  id int(11) NOT NULL AUTO_INCREMENT,
  employee_id int(11) NOT NULL,
  startJob datetime,
  endJob datetime,
  PRIMARY KEY (id),
  FOREIGN KEY (employee_id) REFERENCES employees(id) on update cascade on delete cascade
) ENGINE=InnoDB AUTO_INCREMENT=1;


INSERT INTO employees (id, name, department) VALUES (1,'Петров В.В.','IT');
INSERT INTO employees (id, name, department) VALUES (2,'Мочалкин В.П.','IT');
INSERT INTO employees (id, name, department) VALUES (3,'Козявкин А.В.','IT');
INSERT INTO employees (id, name, department) VALUES (4,'Сухарев А.А.','HR');
INSERT INTO employees (id, name, department) VALUES (5,'Рыбаков У.Е.','HR');

INSERT INTO history(id, employee_id, startJob, endJob) VALUES (1, 1, '2017-02-17 09:00:00', '2017-02-17 19:00:00');
INSERT INTO history(id, employee_id, startJob, endJob) VALUES (2, 2, '2017-02-17 09:05:00', '2017-02-17 18:20:00');
INSERT INTO history(id, employee_id, startJob, endJob) VALUES (3, 1, '2017-02-18 09:00:00', '2017-02-18 18:40:00');
INSERT INTO history(id, employee_id, startJob, endJob) VALUES (4, 1, '2017-02-19 09:00:00', '2017-02-19 18:00:00');
INSERT INTO history(id, employee_id, startJob, endJob) VALUES (5, 3, '2017-02-19 09:01:00', '2017-02-19 18:10:00');
INSERT INTO history(id, employee_id, startJob, endJob) VALUES (6, 5, '2017-02-19 09:02:00', '2017-02-19 18:50:00');