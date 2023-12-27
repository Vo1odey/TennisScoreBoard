CREATE TABLE Matches
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Player1 INT,
    Player2 INT,
    Winner INT,
    FOREIGN KEY (Player1) REFERENCES Players(ID),
    FOREIGN KEY (Player2) REFERENCES Players(ID),
    FOREIGN KEY (Winner) REFERENCES Players(ID)
);