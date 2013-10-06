CREATE DATABASE PUBLICINSTITUTIONCHAT;

USE PUBLICINSTITUTIONCHAT;

SET GLOBAL event_scheduler = ON;

CREATE TABLE INSTITUTIONS( id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100) NOT NULL, PRIMARY KEY ( id ) );

CREATE TABLE CAMPUS( id INT NOT NULL AUTO_INCREMENT, institution_id INT NOT NULL, name VARCHAR(100) NOT NULL, geolatitude1 FLOAT(15) NOT NULL, geolongitude1 FLOAT(15) NOT NULL, geolatitude2 FLOAT(15) NOT NULL, geolongitude2 FLOAT(15) NOT NULL, PRIMARY KEY ( id ), FOREIGN KEY (institution_id) REFERENCES INSTITUTIONS(id) ON DELETE CASCADE );

CREATE TABLE CHANNELS( id INT NOT NULL AUTO_INCREMENT, institution_id INT NOT NULL, name VARCHAR(30) NOT NULL, PRIMARY KEY ( id ), FOREIGN KEY (institution_id) REFERENCES INSTITUTIONS(id) ON DELETE CASCADE );

CREATE TABLE POSTS( id INT NOT NULL AUTO_INCREMENT, institution_id INT NOT NULL, creator VARCHAR(100) NOT NULL, content VARCHAR(250) NOT NULL, picture LONGBLOB, dateToKill DATETIME NOT NULL, PRIMARY KEY ( id ), FOREIGN KEY ( institution_id ) REFERENCES INSTITUTIONS( id ) ON DELETE CASCADE );

DROP EVENT `e_posts_deleteOldPosts` ;

CREATE DEFINER =  `root`@`localhost` EVENT `e_posts_deleteOldPosts` ON SCHEDULE EVERY 10 SECOND STARTS '2013-10-05 22:04:17' ON COMPLETION NOT PRESERVE ENABLE DO DELETE FROM PUBLICINSTITUTIONCHAT.POSTS WHERE dateToKill < TIMESTAMPADD( DAY , -1, NOW( ) ) ;
