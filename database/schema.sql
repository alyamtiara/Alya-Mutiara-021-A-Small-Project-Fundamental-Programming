CREATE DATABASE game_project;
USE game_project;

CREATE TABLE players (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL,
wins INT DEFAULT 0,
losses INT DEFAULT 0,
draws INT DEFAULT 0,
score INT DEFAULT 0
);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Ahmad', '12345', 0, 0, 0, 0);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Raras', '12345', 0, 0, 0, 0);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Sasa', '12345', 0, 0, 0, 0);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Nadia', '12345', 0, 0, 0, 0);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Farhan', '12345', 0, 0, 0, 0);
