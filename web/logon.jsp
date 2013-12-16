
<jsp:useBean id="account_error" type="java.lang.String" class="java.lang.String" scope="request"/>

<%
if ( request.getRemoteUser() == null && !(session.getAttribute("j_username") == null) && !(session.getAttribute("un") == null) && !(session.getAttribute("pw") == null) )
{
%>
	<html>
	<head>
	<title>Chadwick Farms Login</title>
	<META HTTP-EQUIV="Expires" CONTENT="0"/>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
	</head>
	<body marginheight="0" marginwidth="0" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" text="#000000" onload="document.frmLogin.submit();">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" bgcolor="#FFFFFF" valign="top">
			<table width="100%" border="0" cellpadding="10" cellspacing="5">
				<tr>
					<td valign="middle">    
						<h2>Authenticating ... one moment, please.</h2>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<form name="frmLogin" action="j_security_check" method="POST">
			<input type="hidden" name="j_username" value="<%= session.getAttribute("un") %>">
			<input type="hidden" name="j_password" value="<%= session.getAttribute("pw") %>">
			</form>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	</body>
	</html>
<%
	session.removeAttribute("j_username");
}
else
{
	String strReferer = (String)request.getHeader("Referer");
	String strBaseURL = "://" + request.getServerName() + request.getContextPath();

	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");
	if ( request.getProtocol().equalsIgnoreCase("HTTP/1.1") )
	{
	    response.setHeader("Cache-Control","no-cache");
	}
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Chadwick Farms</title>
<link href="/inc/css/melton.css" rel="stylesheet" type="text/css">

<META HTTP-EQUIV="Expires" CONTENT="0"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<META NAME="keywords" CONTENT="chadwick farms">

<script>
<!--
function checkLogin(frm)
{
 if ( frm.un.value == "" || frm.pw.value == "" )
 {
  alert("In order to access this section, you must enter\nthe Username and Password.");
  return false;
 }
}
 // -->
</script>

</head>

<body>

<jsp:include page="/common/layout/page_header.jsp" flush="true" >	
   <jsp:param name="curpage" value="Home" />	
</jsp:include>

<div id="pageborder">

<!-- sidebar content -->

<div id="leftcolumn_home">

<!-- Highlight Box -->


<div class="whatsnew">


 <h1>Chadwick Farms Login Form</h1>
 <br>You will need to log in before you can access this section.<br><br>
  <form name="chadwicklogin" method="post" action="/Login" onsubmit="return checkLogin(this);">
  <input type="hidden" name="nextpage" value="/proxy.jsp">
  <input type="hidden" name="orig" value="/m">
  <input type="hidden" name="forgotpw" value="false">
  <%
    String strun = "", strpw;
    if( null != (strun = (String)session.getAttribute("un")) &&
        null != (strpw = (String)session.getAttribute("pw")) &&
        strun.trim().length() > 0 && strpw.trim().length() > 0 )
    {
  %>
      <span style="color:#CC0000;font-weight:bold">Invalid un or pw</span>
  <%
    }
  %>
 Username:<br>
 &nbsp;&nbsp;<input type="text" name="un" size="25" value="<%= ( null == strun ? "" : strun ) %>"> <br>
 Password:<br>
 &nbsp;&nbsp;<input type="password" name="pw" size="15"> <p>
 <input type="button" value="sign in" onclick="document.chadwicklogin.submit();">
 </form>

<br>

Please send an <a href="mailto:webmaster@chadwickfarms.net?Submit=Request%20Login%20Credentials" target="_top">email</a> if you need further information.

<br>
<br>

</div>

<br>
<br>
<!-- END -->

</div>
<!-- END sidebar -->
		
</div>

<jsp:include page="/common/layout/page_footer.jsp" flush="true" />	
	
</body>
</html>

<%
}
%>