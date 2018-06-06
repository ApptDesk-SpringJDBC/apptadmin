<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>

<link href="static/css/global.css?version=4.3" rel="stylesheet" type="text/css">
<link href="static/css/global-print.css" rel="stylesheet" type="text/css" media="print">
<link rel="stylesheet" type="text/css" media="all" href="static/css/jqModal.css?version=1.0" />
<link rel="stylesheet" type="text/css" media="all" href="static/css/jsDatePick_ltr.min.css" />
<link rel="stylesheet" type="text/css" media="all" href="static/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.2.4/css/buttons.dataTables.min.css">

<script type='text/javascript' src="static/js/default.js"></script>
<script type='text/javascript' src="static/js/common.js?version=4.3"></script>
<script type="text/javascript" src="static/js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="static/js/jquery/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="static/js/blockUI/jquery.blockUI.js"></script>

<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/buttons/1.2.4/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/buttons/1.2.4/js/buttons.flash.min.js"></script>
<script type="text/javascript" charset="utf8" src="http://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
<script type="text/javascript" charset="utf8" src="http:///cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
<script type="text/javascript" charset="utf8" src="http://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/buttons/1.2.4/js/buttons.html5.min.js"></script>
<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/buttons/1.2.4/js/buttons.print.min.js"></script>
<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/buttons/1.2.4/js/buttons.colVis.min.js"></script>


<script type="text/javascript" src="static/js/jquery/jsDatePick.jquery.min.1.3.js"></script>
<script type="text/javascript" src="static/js/jquery/jqModal.js"></script>
<script type="text/javascript" src="static/js/jquery/jquery-ui.js"></script> 

<script type='text/javascript' src="static/js/validation/commonPageValidations.js?version=8.8"></script>

<!-- <title><tiles:insertAttribute name="title" ignore="true" /></title> -->

<script src="static/js/idleTimeout/store.min.js" type="text/javascript"></script>
<script src="static/js/idleTimeout/jquery-idleTimeout.min.js?version=1.0" type="text/javascript"></script>

<script type='text/javascript' src="static/js/validation/menu/menuHelper.js?version=5.2"></script>

<c:if test="${!homeBean.selectedKeepMeLoggedIn}">
	<script src="static/js/idleTimeout/idle-timeout-config.js" type="text/javascript"></script>
</c:if>
<script type='text/javascript'>
$(document).ready(function(){ 
    $(window).scroll(function(){ 
        if ($(this).scrollTop() > 100) { 
            $('#scroll').fadeIn(); 
        } else { 
            $('#scroll').fadeOut(); 
        } 
    }); 
    $('#scroll').click(function(){ 
        $("html, body").animate({ scrollTop: 0 }, 600); 
        return false; 
    }); 
});
</script>
<style type="text/css">
/* BackToTop button css */
#scroll {
    position:fixed;
    right:10px;
    bottom:10px;
    cursor:pointer;
    width:50px;
    height:50px;
    background-color:#3498db;
    text-indent:-9999px;
    display:none;
    -webkit-border-radius:60px;
    -moz-border-radius:60px;
    border-radius:60px
}
#scroll span {
    position:absolute;
    top:50%;
    left:50%;
    margin-left:-8px;
    margin-top:-12px;
    height:0;
    width:0;
    border:8px solid transparent;
    border-bottom-color:#ffffff
}
#scroll:hover {
    background-color:#e74c3c;
    opacity:1;filter:"alpha(opacity=100)";
    -ms-filter:"alpha(opacity=100)";
}
</style>
</head>
<body>	
	<!-- BackToTop Button -->
	<a href="javascript:void(0);" id="scroll" title="Scroll to Top" style="display: none;">Top<span></span></a>
	
	<input type="hidden" id="customerLabel" value="${displayNames.customerName}">
	
	<input type="hidden" id="locationLabel" value="${displayNames.locationName}">
	<input type="hidden" id="resourceLabel" value="${displayNames.resourceName}">
	<input type="hidden" id="serviceLabel" value="${displayNames.serviceName}">
	
	<input type="hidden" id="locationsLabel" value="${displayNames.locationsName}">
	<input type="hidden" id="resourcesLabel" value="${displayNames.resourcesName}">
	<input type="hidden" id="servicesLabel" value="${displayNames.servicesName}">
	
	<input type="hidden" id="locPageSelLocId" value="0">
	<input type="hidden" id="resPageSelResId" value="0">
	<input type="hidden" id="serPageSelSerId" value="0">
	
	<tiles:insertAttribute name="header"/>
	<div id="navbar">
		<tiles:insertAttribute name="menu"/>
	</div>
	<div id="iconbar">
		<tiles:insertAttribute name="iconbar" />
	</div>
	<div style="color:blue" id="sucessesMsgDivId"><b>${sucessesMessage}</b></div>
	<div class="required"  id="errorMsgDivId"><b>${errorMessage}</b></div>		
	 
	<div id="main">
		<tiles:insertAttribute name="body" />
	</div>	
	
</body>


</html>
