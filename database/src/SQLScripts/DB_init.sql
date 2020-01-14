/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Alex
 * Created: 14 janv. 2020
 */
---------------------------TABLE CREATION----------------------------
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Messages;

---------------------------TABLE CREATION----------------------------
--
-- Users
--
CREATE TABLE Users (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    login TEXT NOT NULL,
    password TEXT NOT NULL
);
--
-- Messages
--
create table Messages (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    idUserSrc INTEGER NOT NULL,
    idUserDest INTEGER NOT NULL,
    message TEXT NOT NULL,
    FOREIGN KEY(idUserSrc) REFERENCES Users(id),
    FOREIGN KEY(idUserDest) REFERENCES Users(id)
);

-------------------------VALUES INSERTION---------------------------
--
-- Users
--
INSERT INTO Users (login, password) VALUES
    ('mael', 'mael'),
    ('alex', 'alex'),
    ('cou', 'cou');

--
-- Messages
--
INSERT INTO Messages (idUserSrc, idUserDest, message) VALUES
    (1, 2, 'salut ça va ?'),
    (2, 1, 'ça va et toi ?'),
    (1, 3, 'jambono'),
    (2, 3, 'dlhdjdvhdhdghj');
