<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Chadwick Farms</title>
<link href="/inc/css/melton.css" rel="stylesheet" type="text/css">

<META NAME="keywords" CONTENT="chadwick farms, townhouse community">

<!-- java imports -->
<%@ page import="java.lang.String" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>

<%@ page import="chadwickfarms.survey.SurveyOptions" %>
<%@ page import="chadwickfarms.survey.Option" %>
<%@ page import="chadwickfarms.survey.SurveyAnswers" %>
<%@ page import="chadwickfarms.survey.Answer" %>

<!-- jsp useBeans -->
<jsp:useBean id="the_survey_answers" type="ArrayList" class="java.util.ArrayList" scope="request"/>

<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="/inc/js/chadwickfarms.js"></script>
<script src="/inc/js/cf_board.js"></script>

</head>

<body>

<jsp:include page="/common/layout/page_header.jsp" flush="true" >	
    <jsp:param name="curpage" value="Minutes" />	
</jsp:include>


<div id="pageborder">

<h1>Welcome Chadwick Farms Board Member</h1>
<br><br>
Plain  text file for all Chadwick Farms Owner Email Addresses.  <a href="/documents/CF-OwnerEmail.txt" target="_blank">Click Here</a> for file download.
<br><br>
<h2>Survey Responses</h2>
<br>
<%
for( int inx=0; inx<the_survey_answers.size(); ++inx )
{
	SurveyAnswers surveyAnswers = (SurveyAnswers)the_survey_answers.get(inx);
	String strSurveyName = surveyAnswers.getName();
	String strSurveyQuestion = surveyAnswers.getQuestion();
	String strHeaderRowColor = surveyAnswers.isActive() ? "#FFFFE0" : "#FCB8B8";
%>
<b><%= strSurveyName %>:</b> <i><%= strSurveyQuestion %></i><br>
<table class="surveyresults" bgcolor="#000000" cellspacing="1" border="0" width="100%">
 <thead>
  <tr bgcolor="<%= strHeaderRowColor %>">
    <td width="10%" nowrap class="AccessListName" align="center">Unit</td>
    <td width="15%" nowrap class="AccessListName" align="center">IP</td>
    <td width="20%" nowrap class="AccessListName" align="center">Date</td>
    <td width="15%" nowrap class="AccessListName" align="center">Response</td>
    <td width="40%" nowrap class="AccessListName" align="center">Comments</td>
  </tr>
 </thead>
 <tbody>
<%
    ArrayList listAnswers = (ArrayList)surveyAnswers.getAnswers();
    for( int jnx=0; jnx<listAnswers.size(); ++jnx )
    {
    	Answer answer = (Answer)listAnswers.get(jnx);
    	String strUnit = answer.getUserId();
		String strUserIP = answer.getUserIp();
		String strDate = answer.getAnswerDate();
		String strValue = answer.getValue();
		String strComments = answer.getComments();
		if( null == strComments )
		    strComments = "";
%>
  <tr>
    <td nowrap align="center"><%= strUnit %></td>
    <td nowrap align="center"><%= strUserIP %></td>
    <td nowrap align="center"><%= strDate %></td>
    <td nowrap align="center"><%= strValue %></td>
    <td align="center"><%= strComments %></td>
  </tr>
<%
    } // end of for jnx<listAnswer
%>  
 </tbody>
</table>
<br><br>
<%	
} // end of for inx<the_survey_answer
%>


</div> <!-- end of pageboarder -->

<jsp:include page="/common/layout/page_footer.jsp" flush="true" />	
	
</body>
</html>
