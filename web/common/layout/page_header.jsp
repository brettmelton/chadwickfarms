<!-- START page_header.jsp -->

<%@ page import="javax.servlet.http.HttpSession" %>

<%
	String curpage = request.getParameter("curpage");
	if( null == curpage )
		curpage = "";

    HttpSession theSession = request.getSession(true);
	String strFirstName = (String)theSession.getAttribute("session_fname");

	if( null == strFirstName || strFirstName.trim().length() < 1 )
	{
    	strFirstName = chadwickfarms.action.ChadwickFarmsAction.getCookieValue( request, "name" );
	}
%>
<link href="/inc/css/entranceview.css" rel="stylesheet" type="text/css">

<div id="ltshadow">
<div id="rtshadow">
<div id="wrapper">

<div id="headerlinksnav">
<nav id="headerlinks">
Requires authentication:&nbsp;
<a href="/m" class=<%= "Minutes".equals(curpage) ? "navon" : "nav" %>>Resident Information</a>
</nav>
</div>

<div id="headertransition" class="view view-cfimage">
  <img src="/images/cf_entrance.png" height="280px" width="800px" />
  <div class="mask">
    <h2>Chadwick Farms</h2>
    <p>A wonderful townhouse community of 36 units located in north Kirkland.</p>
    <a href="/p" class="info">Pictures</a>
  </div>
</div>

<!--[if lte IE 8]>
<script type="text/javascript">
  alert("Your version of IE is ancient and is not supported by this site. Please consider upgrading your browser.");
</script>
<![endif]-->

<div id="menulinksnav">
<nav id="nav">
<a href="/" class='<%= "Home".equals(curpage) ? "navon" : "nav" %>'>Home</a>
<span class="spacer">|</span>
<a href="/d" class=<%= "Directions".equals(curpage) ? "navon" : "nav" %>>Directions</a>
<span class="spacer">|</span>
<a href="/h" class=<%= "History".equals(curpage) ? "navon" : "nav" %>>History</a>
<span class="spacer">|</span>
<a href="/p" class=<%= "Photos".equals(curpage) ? "navon" : "nav" %>>Photos</a>
<span class="spacer">|</span>
<a href="/c" class=<%= "Contact".equals(curpage) ? "navon" : "nav" %>>Contact Us</a>
</nav>
</div>

<p>&nbsp;
<p>
<!-- END page_header.jsp -->
