<cfscript>

</cfscript>

<cfoutput>
<cfinclude template="/Inc/html.cfm">
<cfinclude template="/Inc/body.cfm">
<p>
<label for="UsrGoogleID">Friend:</label>
<select name="UsrGoogleID">
	<option value="86365E2F-3C03-4DDE-9F01-34AC2F9B04EA">Michael Senn</option>
	<option value="E409F57C-106A-4E70-8DE7-85AC90FC60AE">Matthew Senn</option>
	<option value="F655CFC7-D6AF-4091-B3C6-61AFE838B61A">Phillip Senn</option>
</select>
</p>
<label for="LocationName">Location:</label>
<input name="LocationName" value="Dog and Duck">
<label for="BootyName">Booty:</label>
<input name="BootyName" autofocus value="PBR">
<p>
	<button type="button" name="Save">Save</button>
</p>
<input type="hidden" name="GoogleID" value="F655CFC7-D6AF-4091-B3C6-61AFE838B61A">
<cfinclude template="/Inc/foot.cfm">
<script src="V1.js"></script>
<cfinclude template="/Inc/End.cfm">
</cfoutput>