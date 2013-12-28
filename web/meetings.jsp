<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Chadwick Farms</title>
<link href="/inc/css/melton.css" rel="stylesheet" type="text/css">

<META HTTP-EQUIV="Expires" CONTENT="0"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<META NAME="keywords" CONTENT="chadwick farms, townhouse community">

<!-- java imports -->
<%@ page import="java.lang.String" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>

<%@ page import="chadwickfarms.survey.SurveyOptions" %>
<%@ page import="chadwickfarms.survey.Option" %>

<!-- jsp useBeans -->
<jsp:useBean id="surveys" type="ArrayList" class="java.util.ArrayList" scope="request"/>

<jsp:useBean id="the_survey_id" type="String" class="java.lang.String" scope="request" />
<jsp:useBean id="the_option_id" type="String" class="java.lang.String" scope="request" />
<jsp:useBean id="the_user_id" type="String" class="java.lang.String" scope="request" />

</head>

<body>

<jsp:include page="/common/layout/page_header.jsp" flush="true" >	
    <jsp:param name="curpage" value="Minutes" />	
</jsp:include>


<div id="pageborder">


<h2>This section is intended for those that call Chadwick Farms home</h2>
Please keep in mind that the password may change periodically.   We will announce any password changes
at board meetings but if you need to have access to the password, please feel free to send an email
to <a href="mailto:webmaster@chadwickfarms.net?subject=Request%20Password">webmaster@chadwickfarms.net</a>
and the password will be sent to you.   Please make sure to include your unit number in the email.
<br><br>

<center>
<img src="/images/divider.png">
</center>

<br><br>

<div id="rightcolumn_home">

<h2>Documents</h2>
<a href="/documents/bylaws11.pdf" target="_blank">Bylaws Part 1</a><br>
<a href="/documents/bylaws21.pdf" target="_blank">Bylaws Part 2</a><br>
<a href="/documents/ChadwickFireplaceLightingInstructions.pdf" target="_blank">Fireplace Lighting</a><br>
<a href="/documents/WinterStormList.pdf" target="_blank">Winter Storm List</a><br>
<a href="/documents/BoardMemberToolKit.pdf" target="_blank">Board Member tool kit</a><br>

<br><br>

<h2>Surveys</h2>
<i>You must provide your unit number in order for your vote to be saved</i><br><br>
<%
for( int inx=0; inx < surveys.size(); ++inx )
{
	SurveyOptions survey = (SurveyOptions)surveys.get(inx);
	int iSurveyId = survey.getId();
	String strSurveyName = survey.getName();
	String strSurveyQuestion = survey.getQuestion();
	
	ArrayList surveyOptions = (ArrayList)survey.getOptions();
	String strButtonDisabled = ((StringUtils.isNotBlank(the_survey_id) && iSurveyId == Integer.parseInt(the_survey_id)) ? "disabled" : "" );
%>

<div class="form_question">
<form action="/m" method="POST">
<input type="hidden" name="submit_survey" value="true">
<input type="hidden" name="survey_id" value="<%= iSurveyId %>">
<b><%= strSurveyName %></b> <br> <i><%= strSurveyQuestion %></i> <br><br>
Unit Number: <input type="text" name="user_id" maxlength="4" size="4" value="<%= the_user_id %>"> <br><br>

<%
for( int jnx=0; jnx<surveyOptions.size(); ++jnx )
{
	Option surveyOption = (Option)surveyOptions.get(jnx);
	int iSurveyOptionId = surveyOption.getId();
	String strSurveyOptionValue = surveyOption.getValue();
	
	String strChecked = ((StringUtils.isNotBlank(the_option_id) && iSurveyOptionId == Integer.parseInt(the_option_id)) ? "checked" : "" );
%>
<input name="question<%= iSurveyId %>" type="radio" value="<%= iSurveyOptionId %>" <%= strChecked %>> <%= strSurveyOptionValue %> <br>
<%
} // end of for loop - surveyOptions
%>

<br>

<input type="submit" value="Submit" onclick="javascript:alert('Thank you for voting.');" <%= strButtonDisabled %>>
</form>
</div>

<br>

<%
} // end of for loop - surveys
%>

<!--
<div class="form_question">
<form action="javascript:alert('just a sample survey');">
<b>This is a sample question</b> <br> <i>Do you like milk?</i> <br><br>
Unit Number: <input type="text" maxlength="4" size="4"> <br><br>
<input name="question1" type="radio" value="Y"> YES
<input name="question1" type="radio" value="N"> NO<br><br>
<input type="submit" value="Submit">
</form>
</div>
// -->

<br><br><br>
</div>
<!-- END sidebar -->
		
<!-- Content Start -->
<div class="text_left">

<h2>Chadwick's List</h2>
Okay, not as big as Craigslist but it could be.  Have something to sell or give away.  Let your board know and we'll post it here.  FREE AD SPACE

<br><br>

<h2>Meeting Minutes Archive</h2>
nothing to see yet.

<br><br><br>
</div>
</div>

<jsp:include page="/common/layout/page_footer.jsp" flush="true" />	
	
</body>
</html>
