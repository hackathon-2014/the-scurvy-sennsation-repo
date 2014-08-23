component {

function Login(ID) {
	if (Len(arguments.ID) != 36) return;
	if (Mid(arguments.ID,09,1) != '-') return;
	if (Mid(arguments.ID,14,1) != '-') return;
	if (Mid(arguments.ID,19,1) != '-') return;
	if (Mid(arguments.ID,24,1) != '-') return;

	for (var i=1; i <= Min(36,Len(arguments.ID)); i++) {
		if (!Find(Mid(arguments.ID,i,1),'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-')) return;
	}
	if (Find('--',arguments.ID)) return;

	include '/Inc/newQuery.cfm'
	sql = '
	SELECT *
	FROM PersonView
	WHERE GoogleID = ?
	'
	params = [
		arguments.ID
	]
	include '/Inc/queryExecute.cfm'
	return result
}

}
