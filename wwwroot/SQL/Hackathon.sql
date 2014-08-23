USE Hackathon
set nocount on
set statistics time off
set statistics io off
SET ANSI_NULL_DFLT_OFF ON -- All columns default to NOT NULL
--
-- Tables
--
if exists (select * from sysobjects where id = object_id(N'Person') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Person
if exists (select * from sysobjects where id = object_id(N'Booty') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Booty
if exists (select * from sysobjects where id = object_id(N'Location') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Location
if exists (select * from sysobjects where id = object_id(N'Treasure') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Treasure
GO
CREATE TABLE Person(
PersonID Int Identity(10,1) Primary Key NONCLUSTERED
,PersonName Varchar(255) default ''
,GoogleID Varchar(255)
)
GO
CREATE TABLE Booty(
BootyID Int Identity(100,1) Primary Key NONCLUSTERED
,BootyName Varchar(255)
)
GO
CREATE TABLE Location(
LocationID Int Identity(1000,1) Primary Key NONCLUSTERED
,LocationName Varchar(255)
)
GO
CREATE TABLE Treasure(
TreasureID Int Identity(10000,1) Primary Key NONCLUSTERED
,Treasure_PersonID Int
,Treasure_UsrID Int
,Treasure_BootyID Int
,Treasure_LocationID Int
)
GO
--
-- Views
--
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'PersonView'))
DROP VIEW PersonView
GO
CREATE VIEW PersonView AS
SELECT *
FROM Person
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'BootyView'))
DROP VIEW BootyView
GO
CREATE VIEW BootyView AS
SELECT *
FROM Booty
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'LocationView'))
DROP VIEW LocationView
GO
CREATE VIEW LocationView AS 
SELECT *
FROM Location
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'TreasureView'))
DROP VIEW TreasureView
GO
CREATE VIEW TreasureView AS
SELECT Treasure.*
,Person.*
,Booty.*
,Location.*
,Usr.PersonName AS UsrName
,Usr.GoogleID AS UsrGoogleID
FROM Treasure
JOIN Person
ON Treasure_PersonID = Person.PersonID
JOIN Booty
ON Treasure_BootyID = BootyID
JOIN Location
ON Treasure_LocationID = LocationID
JOIN Person Usr
ON Treasure_UsrID = Usr.PersonID
GO
--
-- Data
--
DECLARE @PersonID Int
DECLARE @UsrID Int
DECLARE @LocationID Int
DECLARE @BootyID Int
INSERT INTO Location(LocationName) VALUES('Daniel Island club')
INSERT INTO Location(LocationName) VALUES('Four IV Stones Restrobar')
INSERT INTO Location(LocationName) VALUES('Daniel Island Grille')
INSERT INTO Location(LocationName) VALUES('Tailgator''s Grill')
INSERT INTO Location(LocationName) VALUES('Madra Rua Irish Pub')
INSERT INTO Location(LocationName) VALUES('De''Ja Vu II')
INSERT INTO Location(LocationName) VALUES('Two Rivers Tavern')
INSERT INTO Location(LocationName) VALUES('Mill')
INSERT INTO Location(LocationName) VALUES('Dog and Duck')
INSERT INTO Location(LocationName) VALUES('The Sparrow')
SELECT @LocationID = Scope_Identity()


INSERT INTO Person(PersonName,GoogleID) VALUES('Phillip Senn','F655CFC7-D6AF-4091-B3C6-61AFE838B61A')
INSERT INTO Person(PersonName,GoogleID) VALUES('Michael Senn','86365E2F-3C03-4DDE-9F01-34AC2F9B04EA')
SELECT @UsrID = Scope_Identity()
INSERT INTO Person(PersonName,GoogleID) VALUES('Matthew Senn','E409F57C-106A-4E70-8DE7-85AC90FC60AE')
SELECT @PersonID = Scope_Identity()

INSERT INTO Booty(BootyName) VALUES('PBR')
SELECT @BootyID = Scope_Identity()

INSERT INTO Treasure(Treasure_PersonID,Treasure_UsrID,Treasure_BootyID,Treasure_LocationID) VALUES(@PersonID,@UsrID,@BootyID,@LocationID)


--
-- SELECT
--
/*
SELECT * FROM Person
SELECT * FROM Booty
SELECT * FROM Location
*/
SELECT PersonName,UsrName,BootyName,LocationName,* FROM TreasureView
select * from treasure


