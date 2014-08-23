<cfscript>
GoogleID = 'F655CFC7-D6AF-4091-B3C6-61AFE838B61A'
UsrGoogleID = '86365E2F-3C03-4DDE-9F01-34AC2F9B04EA'
LocationName = 'Two Rivers Tavern'
BootyName = 'PBR'
new V1().Save(GoogleID,LocationName,BootyName,UsrGoogleID)
</cfscript>

<cfoutput>
<cfinclude template="/Inc/html.cfm">
<cfinclude template="/Inc/body.cfm">

<cfinclude template="/Inc/foot.cfm">
<cfinclude template="/Inc/End.cfm">
</cfoutput>