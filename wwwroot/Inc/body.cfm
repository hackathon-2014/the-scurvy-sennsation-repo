</head>
<body>
<cfoutput>
<cfif request.fw.navbar NEQ "none">
	<nav class="nav navbar-default #request.fw.navbar#">
		<div class="navbar-inverse">
			<cfif IsDefined("session.Person")>
				<div class="container">
					<div class="navbar-header">
						<a class="navbar-brand glyphicon glyphicon-home" href="#request.fw.home#"
						data-toggle="tooltip" data-placement="bottom" title="Arrr..."></a>
					</div>
					<div class="navbar-collapse collapse">
						<div class="navbar-right">
							<ul class="nav navbar-nav">
								<li class="dropdown">
									<a href="##" class="dropdown-toggle" data-toggle="dropdown">#session.Person.PersonName# <span class="caret"></span></a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="#request.fw.home#/Profile/Profile.cfm">Profile</a></li>
										<li class="divider"></li>
										<li><a href="#request.fw.home#?logout">Logout</a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			<cfelse>
				<div class="container">
					<div class="navbar-header">
						<a class="navbar-brand glyphicon glyphicon-home" href="http://www.sparcedge.com/"
						data-toggle="tooltip" data-placement="bottom" title="Sparc"></a>
					</div>
				</div>
			</cfif>
		</div>
		<cfif request.fw.mod NEQ "none">
			<div class="msg container<cfif request.fw.msg NEQ ''> #request.fw.mod#</cfif>">
				#request.fw.msg#
			</div>
		</cfif>
	</nav>
</cfif>
<main role="main" class="<cfif request.fw.container>container<cfelse>container-fluid</cfif>"
<cfif request.fw.hidden AND request.fw.js AND request.fw.css>hidden</cfif>>
</cfoutput>
