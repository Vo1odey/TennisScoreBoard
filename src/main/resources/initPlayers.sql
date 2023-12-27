CREATE TABLE Players
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(128)
);
CREATE INDEX search_name ON Players (Name);

