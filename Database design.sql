DROP DATABASE IF EXISTS banca_sanguina;
CREATE DATABASE banca_sanguina;
USE banca_sanguina;

CREATE TABLE locatii
(id INTEGER AUTO_INCREMENT NOT NULL UNIQUE PRIMARY KEY,
nume VARCHAR(100) NOT NULL,
adresa VARCHAR(256) NOT NULL,
ora_deschidere TIME NOT NULL,
ora_inchidere TIME NOT NULL,
numar_maxim_recoltari INTEGER NOT NULL
);

CREATE TABLE doctori
(id INTEGER AUTO_INCREMENT NOT NULL UNIQUE PRIMARY KEY,
nume VARCHAR(50) NOT NULL,
prenume VARCHAR(50) NOT NULL,
email VARCHAR(100) NOT NULL,
cnp CHAR(13) NOT NULL,
id_locatie INTEGER NOT NULL,
FOREIGN KEY (id_locatie) REFERENCES locatii(id) ON DELETE CASCADE
);

CREATE TABLE programari
(id INTEGER AUTO_INCREMENT NOT NULL UNIQUE PRIMARY KEY,
id_locatie INTEGER NOT NULL,
data_programare DATE NOT NULL,
confirmare BOOLEAN NOT NULL,
FOREIGN KEY (id_locatie) REFERENCES locatii(id)
);

CREATE TABLE donatori
(id INTEGER AUTO_INCREMENT NOT NULL UNIQUE PRIMARY KEY,
nume VARCHAR(50) NOT NULL,
prenume VARCHAR(50) NOT NULL,
grupa_sanguina INTEGER NOT NULL
);

CREATE TABLE utilizatori
(username VARCHAR(50) NOT NULL UNIQUE PRIMARY KEY,
sare CHAR(16) NOT NULL,
hash_parola CHAR(128) NOT NULL,
is_admin BOOLEAN NOT NULL,
id_doctor INTEGER,
id_donator INTEGER,
FOREIGN KEY (id_doctor) REFERENCES doctori(id) ON DELETE CASCADE,
FOREIGN KEY (id_donator) REFERENCES donatori(id) ON DELETE CASCADE
);

CREATE TABLE donatori_programari
(id_donator INTEGER NOT NULL,
id_programare INTEGER NOT NULL,
PRIMARY KEY (id_donator, id_programare),
FOREIGN KEY (id_donator) REFERENCES donatori(id) ON DELETE CASCADE,
FOREIGN KEY (id_programare) REFERENCES programari(id) ON DELETE CASCADE
);

#Locatii
INSERT INTO locatii VALUES 
(null, "Regina Maria", "Strada Observatorului", "08:00:00", "16:00:00", 50);

#Admin (username = admin, password = admin)
INSERT INTO utilizatori VALUE ("admin", "ozu429NXrJscl9MI", "AE˥����*�D�3k", 1, null, null);

#Doctor (username = doctor, password = doctor)
INSERT INTO doctori VALUES (null, "Ionica", "Georgeta", "georgeta.ionica@gmail.com", "6020506204962", 1);
INSERT INTO utilizatori VALUES ("doctor", "Jof4B8J4y6tyueCR", "?xQg�(kشz&��u", false, 1, null);

#Donator (iusername = donator, password = donator)
INSERT INTO donatori VALUES (null, "Ionel", "Georgel", 1);
INSERT INTO utilizatori VALUES ("donator", "Yejs1t740apS7l23", "�{皔4�D�V�f", false, null, 1);

#Programare
INSERT INTO programari VALUES (null, 1, "2023-03-20", false);
INSERT INTO donatori_programari VALUES (1, 1);

-- SELECT * FROM utilizatori u 
-- JOIN donatori d ON u.id_donator = d.id
-- JOIN donatori_programari da ON d.id = da.id_donator
-- JOIN programari p ON da.id_programare = p.id
-- JOIN locatii l ON p.id_locatie = l.id;
-- SELECT * FROM utilizatori u 
-- JOIN doctori d ON u.id_doctor = d.id
-- JOIN locatii l ON d.id_locatie = l.id;
