<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<input  type="hidden"  id="accessLevel"  value="${homeBean.userLoginResponse.accessLevel}"/>
<!-- Header starts -->
<header id="branding">
	<div class="top-line">
		<div class="top-link">
			Welcome: ${homeBean.userLoginResponse.userName} | Logged in as
			${homeBean.userLoginResponse.accessLevel}<br> 
			Last Log-in IP: ${homeBean.userLoginResponse.lastLoginIP} 
			@ ${homeBean.userLoginResponse.lastLoginDateTime}
		</div>
	</div>
	<div id="logo"><a href="javascript:"><img  src="./static/images/logo.png" height="60"></a></div>

	<div class="right_bar">
		<a href="log-out.html" class="logout">Logout</a> 
		<a href="support.html" class="support">Support</a>
		<!--
        <div style="float:right; margin-right:8px;">
		-->
		<!-- live2support.com tracking codes starts -->
		<!--
		<div id="l2s_trk" style="z-index:99;"><a href="http://live2support.com" style="font-size:1px;">Live support chat software</a></div><script type="text/javascript">   var l2s_pht=escape(location.protocol); if(l2s_pht.indexOf("http")==-1) l2s_pht='http:'; var off2="http://apps.telappoint.com/support_image.gif"; var on2="http://apps.telappoint.com/support_image.gif"; (function () { document.getElementById('l2s_trk').style.visibility='hidden'; var l2scd = document.createElement('script'); l2scd.type = 'text/javascript'; l2scd.async = true; l2scd.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 's01.live2support.com/js/lsjs1.php?stid=26082&cmi=1'; var l2sscr = document.getElementsByTagName('script')[0]; l2sscr.parentNode.insertBefore(l2scd, l2sscr); })();  </script>
		-->
		<!-- live2support.com tracking codes closed -->
		<!--
		</div>
        -->
		<!--
        <div style="float:right; margin-right:8px;"><a id='__imgButtonClickToCall' href='#'  onclick="window.open('http://acctmgr.evoice.com/ACCTMGR/ClickToCall/ClickToConnectSession.aspx?key=4d8aa884-ba0b-4ead-9b83-011b4dd38cfd','','width=570,height=280, left=40, top=100, resizable=no,scroll=yes,status=no,titlebar=no,toolbar=no,addressbar=no,copyhistory=no,navigationtoolbar=no')"  style='cursor:pointer;border:none'><img  src='http://acctmgr.evoice.com/ACCTMGR/Brands/eVoice/Images/ClickToCall/SystemImages7_h.jpg' height='28' alt='Click to open call me page' id='__imgClickToCall' style='border:0px;' /></a></div>
		-->
        
        
	</div>
	<div class="banner"><center>${homeBean.clientName}</center></div>

</header>
<!-- Header ends -->
