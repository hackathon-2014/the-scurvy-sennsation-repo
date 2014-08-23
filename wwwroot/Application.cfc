component {
this.Name = 'Hackathon'
this.DataSource = 'Hackathon'
this.SessionManagement = true
this.ScriptProtect = 'all'
this.mappings['Inc'] = 'C:\home\MattSenn.com\wwwroot\Hackathon\Inc'
this.mappings['com'] = 'C:\home\MattSenn.com\wwwroot\Hackathon\com'

function onApplicationStart() {
}

function onSessionStart() {
	session.fw.msg = ''
	session.fw.mod = 'label-info'
}

function onRequestStart(LogCFCName) {
	StructAppend(form,url,false)
	if (IsDefined('form.onApplicationStart')) {
		onApplicationStart()
		onSessionStart()
	}
	if (IsDefined('form.onSessionStart')) {
		onSessionStart()
	}
	request.fw = Duplicate(session.fw)
	request.fw.name='Hackathon'
	request.fw.home = '/Hackathon' // without a trailing slash
	request.fw.css = true
	if (IsDefined('form.css')) {
		request.fw.css = form.css
	}
	request.fw.js = true
	if (IsDefined('form.js')) {
		request.fw.js = form.js
	}
	request.fw.jQueryUI = 'ui-lightness'
	request.fw.bootstrap = true
	request.fw.try = false
	if (IsDefined('form.try')) {
		request.fw.try = form.try
	}
	request.fw.navbar = 'navbar-fixed-top' // none | navbar-fixed-top | navbar-fixed-bottom | navbar-static-top | ''
	request.fw.container = true
	request.fw.hidden = false
	if (IsDefined('url.logout')) {
		StructDelete(session,'Person')
	}
	if (IsDefined('url.ID')) {
		local.Person = new Index().Login(url.ID)
		if (local.Person.recordCount) {
			session.Person = Duplicate(local.Person)
		}
	}
	setting showDebugOutput=false;
/*	
	if (IsDefined('session.Person')) {
		request.PersonPath = request.fw.home & '/People/' & Replace(session.Person.PersonName,' ','','all')
	} else {
		dump(session)
		abort;
		request.fw.msg = 'You have been logged out.'
		request.fw.mod = 'label-danger'
		include '/Inc/Login.cfm'
		return false
	}
*/
}

function onRequestEnd(LogCFCName) {
	session.fw.msg = ''
	session.fw.mod = 'label-info' // 'none', or the Bootstrap modifiers
}
}
