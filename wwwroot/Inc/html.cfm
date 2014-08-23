<cfoutput>
<!doctype html>
<html lang="en" class="no-js">
<head>
<title>#request.fw.name#</title>
<cfif request.fw.css>
	<meta charset="utf-8">
	<meta content="Phillip Senn, Michael Senn, Matthew Senn" name="author">
	<!---
	<meta name="viewport" content="width=device-width, initial-scale=1, minimal-ui">
	--->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<cfif request.fw.bootstrap>
		<link rel="stylesheet" href="//cdn.jsdelivr.net/bootstrap/latest/css/bootstrap.css">
		<link rel="stylesheet" href="//cdn.jsdelivr.net/bootstrap/latest/css/bootstrap-theme.css">
	</cfif>
	<link rel="stylesheet" href="#request.fw.home#/Inc/css/fw.css">
	<link rel="stylesheet" href="#request.fw.home#/Inc/css/Hackathon.css">
</cfif>
<cfif request.fw.js>
	<script src="//cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
</cfif>
</cfoutput>