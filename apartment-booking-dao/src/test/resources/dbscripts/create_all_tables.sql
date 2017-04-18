CREATE TABLE if not EXISTS USERS (
  	U_ID INT(10) NOT NULL AUTO_INCREMENT,
	U_EMAIL VARCHAR(64) NOT NULL,
	U_NAME VARCHAR(64) NOT NULL,
	U_SURNAME VARCHAR(64) NOT NULL,
	U_PASSWORD VARCHAR(100) NOT NULL,
	U_BIRTHDAY DATE,
		PRIMARY KEY (U_ID)
); 

--ALTER TABLE USERS ADD CONSTRAINT U_ID_UNIQUE UNIQUE(U_ID);
--ALTER TABLE USERS ADD CONSTRAINT U_EMAIL_UNIQUE UNIQUE(U_EMAIL);

CREATE TABLE if not EXISTS LOCATIONS (
  LO_ID INT(10) NOT NULL AUTO_INCREMENT,
  LO_COUNTRY VARCHAR(64) NOT NULL,
  LO_CITY VARCHAR(64) NOT NULL, 
  LO_STREET VARCHAR(64) NOT NULL,
  LO_BUILDING_NO VARCHAR(10), 
  		PRIMARY KEY (LO_ID)
);

--ALTER TABLE LOCATIONS ADD CONSTRAINT LO_ID_UNIQUE UNIQUE(LO_ID);

CREATE TABLE if not EXISTS TYPE_APARTMENTS (
  TY_ID INT(10) NOT NULL AUTO_INCREMENT,
  TY_TYPE VARCHAR(20) NOT NULL,
  TY_DESCRIPTION VARCHAR(256),
		PRIMARY KEY (TY_ID)
);

--ALTER TABLE TYPE_APARTMENTS ADD CONSTRAINT TY_ID_UNIQUE UNIQUE(TY_ID);

CREATE TABLE if not EXISTS APARTMENTS (
  AP_ID INT(10) NOT NULL AUTO_INCREMENT, 
  AP_NAME VARCHAR(128) NOT NULL,
  AP_PRICE DECIMAL, 
  AP_MAX_GUEST_NUMBER INT(10), 
  AP_TYPE_ID INT(10), 
  AP_LOCATION_ID INT(10), 
		PRIMARY KEY (AP_ID)
);

--ALTER TABLE APARTMENTS ADD CONSTRAINT AP_ID_UNIQUE UNIQUE(AP_ID);

CREATE TABLE if not EXISTS BOOKINGS (	
	BO_ID INT(10) NOT NULL AUTO_INCREMENT,  
	BO_USER INT(10) NOT NULL, 
	BO_APARTMENT INT(10) NOT NULL, 
	BO_START DATE NOT NULL, 
	BO_END DATE NOT NULL, 
	 PRIMARY KEY (BO_ID)
	 --,
	-- CONSTRAINT "BOOKINGS_USERS_FK1" FOREIGN KEY (BO_USER)
	--  REFERENCES USERS (U_ID) ON DELETE CASCADE, 
	-- CONSTRAINT "BOOKINGS_APARTMENTS_FK1" FOREIGN KEY (BO_APARTMENT)
	 -- REFERENCES APARTMENTS (AP_ID) ON DELETE CASCADE
   ) ;

   --ALTER TABLE BOOKINGS ADD CONSTRAINT BO_ID_UNIQUE UNIQUE(BO_ID);