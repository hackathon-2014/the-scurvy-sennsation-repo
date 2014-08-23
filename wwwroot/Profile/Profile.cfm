<cfscript>

</cfscript>

<cfoutput>
<cfinclude template="/Inc/html.cfm">
<cfinclude template="/Inc/body.cfm">
<form method="post">
	<label for="PersonName">Name:</label>
	<input name="PersonName" autofocus>
	
	<p>
		<button type="submit" name="Save">Save</button>
	</p>
</form>
<cfinclude template="/Inc/foot.cfm">
<cfinclude template="/Inc/End.cfm">
</cfoutput>