<cfscript>
GoogleID = 'E409F57C-106A-4E70-8DE7-85AC90FC60AE'
response = new V2().WhereGoogleID(GoogleID)
</cfscript>

<cfoutput>
<cfinclude template="/Inc/html.cfm">
<cfinclude template="/Inc/body.cfm">
<cfdump var="#response#">
<cfinclude template="/Inc/foot.cfm">
<cfinclude template="/Inc/End.cfm">
</cfoutput>