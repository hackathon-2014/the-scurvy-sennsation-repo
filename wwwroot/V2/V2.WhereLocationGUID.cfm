<cfscript>
GoogleID = 'E409F57C-106A-4E70-8DE7-85AC90FC60AE' // Matthew
LocationGUID = '39BDB862-AECC-4D48-8766-7522FD9F3922'
response = new V2().WhereLocationGUID(GoogleID,LocationGUID)
</cfscript>

<cfoutput>
<cfinclude template="/Inc/html.cfm">
<cfinclude template="/Inc/body.cfm">
<cfdump var="#response#">
<cfinclude template="/Inc/foot.cfm">
<cfinclude template="/Inc/End.cfm">
</cfoutput>