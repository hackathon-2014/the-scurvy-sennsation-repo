component {
header name='Access-Control-Allow-Origin' value='*';

remote function Save(GoogleID,UsrGoogleID,LocationGUID,BootyName) returnformat='json' {
	include '/Inc/newQuery.cfm'
	include '/Inc/newQuery.cfm'
	sql = '
	DECLARE @BootyName Varchar(255) = ?
	IF NOT EXISTS(SELECT * FROM Booty WHERE BootyName = @BootyName) BEGIN
		INSERT INTO Booty(BootyName) VALUES(@BootyName)
	END
	SELECT *
	FROM Booty
	WHERE BootyName = @BootyName
	'
	params = [
		arguments.BootyName
	]
	include '/Inc/queryExecute.cfm'
	local.BootyID = result.BootyID




	include '/Inc/newQuery.cfm'
	sql = '
	DECLARE @GoogleID varchar(255) = ?
	DECLARE @UsrGoogleID Varchar(255) = ?
	DECLARE @BootyID Int = ?
	DECLARE @LocationID Int = (
		SELECT LocationID
		FROM Location
		WHERE LocationGUID = ?
	)
	DECLARE @PersonID Int = (
		SELECT PersonID
		FROM Person
		WHERE GoogleID = @GoogleID
	)
	DECLARE @UsrID Int = (
		SELECT PersonID
		FROM Person
		WHERE GoogleID = @UsrGoogleID
	)
	INSERT INTO Treasure(Treasure_PersonID,Treasure_UsrID,Treasure_BootyID,Treasure_LocationID) VALUES(@PersonID,@UsrID,@BootyID,@LocationID)
	SELECT *
	FROM Treasure
	WHERE TreasureID = Scope_Identity()
	'
	params = [
		 arguments.GoogleID
		,arguments.UsrGoogleID
		,local.BootyID
		,arguments.LocationGUID
	]
	include '/Inc/queryExecute.cfm'
	return result
}
}