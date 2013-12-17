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
<div id="ltshadow">
<div id="rtshadow">
<div id="wrapper">

<div id="headerlinks">
Requires authentication:&nbsp;
<a href="/m" class=<%= "Minutes".equals(curpage) ? "navon" : "nav" %>>Resident Information</a>
</div>

<div class="header">
&nbsp;
</div>

<div id="nav">
<a href="/" class='<%= "Home".equals(curpage) ? "navon" : "nav" %>'>Home</a>
<span class="spacer">|</span>
<a href="/d" class=<%= "Directions".equals(curpage) ? "navon" : "nav" %>>Directions</a>
<span class="spacer">|</span>
<a href="/h" class=<%= "History".equals(curpage) ? "navon" : "nav" %>>History</a>
<span class="spacer">|</span>
<a href="/p" class=<%= "Photos".equals(curpage) ? "navon" : "nav" %>>Photos</a>
<span class="spacer">|</span>
<a href="/c" class=<%= "Contact".equals(curpage) ? "navon" : "nav" %>>Contact Us</a>
</div>

<!-- END page_header.jsp -->
