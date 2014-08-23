<cfscript>
request = new com.oauth.Request()
consumer = new com.oauth.Consumer()
token = new com.oauth.Token()

consumer.setSecret(variables.BUZZSTREAM_SECRET)
consumer.setKey(variables.BUZZSTREAM_KEY)

</cfscript>

<cfoutput>
<cfinclude template="/Inc/html.cfm">
<cfinclude template="/Inc/body.cfm">

<cfinclude template="/Inc/foot.cfm">
<cfinclude template="/Inc/End.cfm">
</cfoutput>