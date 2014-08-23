<cfscript>
PersonPath = request.fw.home & '/People/' 
	& Replace(Person.PersonName,' ','','all')
</cfscript>
