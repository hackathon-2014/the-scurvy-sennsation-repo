component {

function Save(arg) {
	include '/Inc/newQuery.cfm'
	sql = '
	UPDATE Person SET
	 PersonName = ?
	WHERE PersonID = ?
	'
	params = [
		 arg.PersonName
		,session.Person.PersonID
	]
	include '/Inc/queryExecute.cfm'
}