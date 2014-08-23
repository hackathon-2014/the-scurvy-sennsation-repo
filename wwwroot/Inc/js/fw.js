(function() {
	// Just in case there's a JavaScript error before getting to the bottom.
	setTimeout(3000,showMain)
	function showMain() {
		$('main').removeAttr('hidden')
	}
})()

window.log = function(arg) { // http://www.paulirish.com/2009/log-a-lightweight-wrapper-for-consolelog/
	if (this.console) {
		console.log(arg)
	} else {
		$('body').append('<pre>' + arg + '<br>' + $(arg).text() + '</pre>') // Bootstrap only.  A multipage app like jqm would require it on every page.
	}
}

function Int(x) {
	return parseInt(x,10)
}

function Right(str,n) {
	if (n <= 0) return ''
	if (n >= str.length) return str
	return str.substring(str.length-n)
}

;(function() {
	$(document).on('mouseenter', 'button[name="Delete"]', function() {
		$(this).addClass('btn-danger')
	})
	$(document).on('mouseleave', 'button[name="Delete"]', function() {
		$(this).removeClass('btn-danger')
	})
	$('table').not('.no-table').addClass('table')
	$('table').not('.no-table,.no-hover').addClass('table-hover')
	$('table').not('.no-table,.no-striped').addClass('table-striped')
	$('table').not('.no-table,.no-bordered').addClass('table-bordered')
	$('table').not('.no-table,.no-condensed').addClass('table-condensed')
	$('table').not('.no-sortable').addClass('sortable')	
	$('textarea,input:text,input:password,input[type=email],select').addClass('form-control')
	$('form').attr('role','form')
	$('form>div').addClass('form-group')

	$('button,.btn-lg,.btn-block,.btn-default,.btn-primary,.btn-success,.btn-info,.btn-warning,.btn-danger,.btn-link').addClass('btn')
	$('.btn').addClass('btn-lg')
	$('.no-lg').addClass('btn').removeClass('btn-lg')
	$('#Save,button[name=Save]').addClass('btn-primary')
	$('.btn').not('.btn-success,.btn-primary,.btn-info,.btn-warning,.btn-danger').addClass('btn-default')

	$('header,section,article,footer').addClass('row')

	function Railo() { // (pronounced Rhylo)
		$('a[name=cfdebug_templates]').find('font').hide()
		$('table.cfdebug').addClass('table table-striped table-hover table-bordered table-condensed')
	}
	setTimeout(Railo,100)
	// $('table').not('.no-table').addClass('table').wrap('<div class="table-responsive"></div>')
	// $('img').not('[hidden]').addClass('img-responsive') // img-rounded
})()

$('form').attr('method','post')
