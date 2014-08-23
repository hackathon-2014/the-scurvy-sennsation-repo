USE OneToMany
set nocount on
set statistics time off
set statistics io off
SET ANSI_NULL_DFLT_OFF ON -- All columns default to NOT NULL
--
-- Tables
--
if exists (select * from sysobjects where id = object_id(N'Lay') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Lay

if exists (select * from sysobjects where id = object_id(N'Treasure') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Treasure

if exists (select * from sysobjects where id = object_id(N'Location') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Location

if exists (select * from sysobjects where id = object_id(N'Booty') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Booty

if exists (select * from sysobjects where id = object_id(N'Person') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE Person

GO
CREATE TABLE Person(
PersonID Int Identity(10,1) Primary Key NONCLUSTERED
,PersonName Varchar(255) default ''
,GoogleID UNIQUEIDENTIFIER
)
GO
CREATE UNIQUE CLUSTERED INDEX GoogleID ON Person(GoogleID)
GO
CREATE TABLE Booty(
BootyID Int Identity(100,1) Primary Key NONCLUSTERED
,BootyName Varchar(255)
)
GO
CREATE TABLE Location(
LocationID Int Identity(1000,1) Primary Key NONCLUSTERED
,LocationName Varchar(255)
,LocationGUID UNIQUEIDENTIFIER default newid()
)
GO
CREATE TABLE Treasure(
TreasureID Int Identity(10000,1) Primary Key NONCLUSTERED
,Treasure_PersonID Int
,Treasure_BootyID Int
,Treasure_LocationID Int
,Claimed smalldatetime null
)
GO
CREATE CLUSTERED INDEX Person ON Treasure(Treasure_PersonID)
GO
ALTER TABLE Treasure WITH CHECK
ADD CONSTRAINT Treasure_PersonID FOREIGN KEY(Treasure_PersonID)
REFERENCES Person
ALTER TABLE Treasure CHECK CONSTRAINT Treasure_PersonID

ALTER TABLE Treasure WITH CHECK
ADD CONSTRAINT Treasure_BootyID FOREIGN KEY(Treasure_BootyID)
REFERENCES Booty
ALTER TABLE Treasure CHECK CONSTRAINT Treasure_BootyID

ALTER TABLE Treasure WITH CHECK
ADD CONSTRAINT Treasure_LocationID FOREIGN KEY(Treasure_LocationID)
REFERENCES Location
ALTER TABLE Treasure CHECK CONSTRAINT Treasure_LocationID
GO
CREATE TABLE Lay(
LayID Int Identity(100000,1) PRIMARY KEY NONCLUSTERED
,Lay_TreasureID Int
,Lay_PersonID Int
)
GO
ALTER TABLE Lay WITH CHECK
ADD CONSTRAINT Lay_TreasureID FOREIGN KEY(Lay_TreasureID)
REFERENCES Treasure
ALTER TABLE Lay CHECK CONSTRAINT Lay_TreasureID
GO

ALTER TABLE Lay WITH CHECK
ADD CONSTRAINT Lay_PersonID FOREIGN KEY(Lay_PersonID)
REFERENCES Person
ALTER TABLE Lay CHECK CONSTRAINT Lay_PersonID
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
SELECT *
FROM Treasure
JOIN Person
ON Treasure_PersonID = Person.PersonID
JOIN Booty
ON Treasure_BootyID = BootyID
JOIN Location
ON Treasure_LocationID = LocationID
GO
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'LayView'))
DROP VIEW LayView
GO
CREATE VIEW LayView AS
SELECT Lay.*
,TreasureView.*
,Person.PersonName AS UsrName
,Person.GoogleID AS UsrGoogleID
FROM Lay
JOIN TreasureView
ON Lay_TreasureID = TreasureID
JOIN Person
ON Lay_PersonID = Person.PersonID
GO
--
-- Data
--
DECLARE @PersonID Int
DECLARE @UsrID Int
DECLARE @LocationID Int
DECLARE @BootyID Int
INSERT INTO Location(LocationName,LocationGUID) VALUES('Daniel Island club','5400BA70-57BF-472B-858A-C1691F9ECBD9')
INSERT INTO Location(LocationName,LocationGUID) VALUES('Four IV Stones Restrobar','70928F84-DA9D-40B1-8B4D-57BA62F08CC3')
INSERT INTO Location(LocationName,LocationGUID) VALUES('Daniel Island Grille','3E65C2FA-FE3F-4A64-9B2E-1413FC8EC1C4')
INSERT INTO Location(LocationName,LocationGUID) VALUES('Tailgator''s Grill','CE877810-5148-4628-A765-41177437B514')
INSERT INTO Location(LocationName,LocationGUID) VALUES('Madra Rua Irish Pub','A8A2F524-37EE-4771-BB5D-0B8FE40F0336')
INSERT INTO Location(LocationName,LocationGUID) VALUES('De''Ja Vu II','028C1B75-C725-469F-9287-070A859EEB1D')
INSERT INTO Location(LocationName,LocationGUID) VALUES('Two Rivers Tavern','AFA211D5-1D2E-4776-870F-D4A53B273C9B')
INSERT INTO Location(LocationName,LocationGUID) VALUES('Mill','39BDB862-AECC-4D48-8766-7522FD9F3922')
INSERT INTO Location(LocationName,LocationGUID) VALUES('Dog and Duck','02E0476C-8117-455A-8388-04DAD9794B87')
INSERT INTO Location(LocationName,LocationGUID) VALUES('The Sparrow','0B4FF9B2-AF45-4CF7-BE57-B7FE6D5F1AF3')
SELECT @LocationID = Scope_Identity()


INSERT INTO Person(PersonName,GoogleID) VALUES('Thomas Anstis','A114FCB5-EB05-4556-80A3-9073340374D7')
INSERT INTO Person(PersonName,GoogleID) VALUES('Captain Avery','52191C20-7223-4B6C-810E-BF15D5128AAA')
INSERT INTO Person(PersonName,GoogleID) VALUES('Barthelemy','83AD492F-7EE4-4FB4-B075-9088CB5E297B')
INSERT INTO Person(PersonName,GoogleID) VALUES('Michael le Basque','3109C264-7480-41BF-A0D9-61028591F189')
INSERT INTO Person(PersonName,GoogleID) VALUES('Samuel Bellamy','39C07126-E854-4A35-9DBE-FBB583F178DA')
INSERT INTO Person(PersonName,GoogleID) VALUES('Blackbeard','B2D6A60D-BE4C-4C40-8974-21CCD9C404EF')
INSERT INTO Person(PersonName,GoogleID) VALUES('Major Stede Bonnet','B41BDCAB-2CF2-4F27-B0EE-A77092DCA9C9')
INSERT INTO Person(PersonName,GoogleID) VALUES('Anne Bonny','F1F3618D-66D9-4C0B-8834-FBD6B969B75C')
INSERT INTO Person(PersonName,GoogleID) VALUES('Samuel Bradish','A7BD7354-DDC0-421D-B26E-53948F1F18A9')
INSERT INTO Person(PersonName,GoogleID) VALUES('Captain Bradley','C5255382-5597-45A3-A6BE-A2CF48CE5545')
INSERT INTO Person(PersonName,GoogleID) VALUES('Alexandre Bras-de-Fer','7FCE85F7-F035-488D-8F29-71E4E2A62CD5')
INSERT INTO Person(PersonName,GoogleID) VALUES('Captain Condent','371154EE-27E0-437F-9EEC-D3550118193D')
INSERT INTO Person(PersonName,GoogleID) VALUES('Captain Howel Davis','531FCD75-838A-4BAE-8CDA-FF49F77B3E86')
INSERT INTO Person(PersonName,GoogleID) VALUES('John Davis','2E20C7D3-D5C2-4363-99F0-A3961FE8E243')
INSERT INTO Person(PersonName,GoogleID) VALUES('Edward England','4800E04C-CAE3-40A5-8637-69AFE0E56C3A')
INSERT INTO Person(PersonName,GoogleID) VALUES('John Evans','A50E1055-31BB-4B7A-B952-4239947D1FF6')
INSERT INTO Person(PersonName,GoogleID) VALUES('Captain Fly','82AF2D7C-2058-4311-A51D-D61CE897CBE1')
INSERT INTO Person(PersonName,GoogleID) VALUES('Phillip Senn','F655CFC7-D6AF-4091-B3C6-61AFE838B61A')
INSERT INTO Person(PersonName,GoogleID) VALUES('Michael Senn','86365E2F-3C03-4DDE-9F01-34AC2F9B04EA')
SELECT @PersonID = Scope_Identity()
INSERT INTO Person(PersonName,GoogleID) VALUES('Matthew Senn','E409F57C-106A-4E70-8DE7-85AC90FC60AE')
SELECT @UsrID = Scope_Identity()

INSERT INTO Booty(BootyName) VALUES('One')
INSERT INTO Booty(BootyName) VALUES('Two')
INSERT INTO Booty(BootyName) VALUES('Three')
INSERT INTO Booty(BootyName) VALUES('Four')
INSERT INTO Booty(BootyName) VALUES('Five')
INSERT INTO Booty(BootyName) VALUES('Six')
INSERT INTO Booty(BootyName) VALUES('Seven')
INSERT INTO Booty(BootyName) VALUES('Eight')
INSERT INTO Booty(BootyName) VALUES('Nine')
INSERT INTO Booty(BootyName) VALUES('Ten')

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,100,1000,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,101,1001,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,102,1002,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,103,1003,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,104,1004,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,105,1005,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,106,1006,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,107,1007,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,108,1008,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)

INSERT INTO Treasure(Treasure_PersonID,Treasure_BootyID,Treasure_LocationID,Claimed) VALUES(@PersonID,109,1009,getdate())
INSERT INTO Lay(Lay_TreasureID,Lay_PersonID) values(Scope_Identity(),@UsrID)
--
-- SELECT
--
/*
SELECT * FROM Person
SELECT * FROM Booty
SELECT * FROM Location
SELECT PersonName,UsrName,BootyName,LocationName,* FROM TreasureView
select * from treasure order by claimed desc
*/



select *
from Lay

