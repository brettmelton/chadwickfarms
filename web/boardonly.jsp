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
<%@ page import="chadwickfarms.survey.SurveyAnswers" %>
<%@ page import="chadwickfarms.survey.Answer" %>

<!-- jsp useBeans -->
<jsp:useBean id="the_survey_answers" type="ArrayList" class="java.util.ArrayList" scope="request"/>


</head>

<body>

<jsp:include page="/common/layout/page_header.jsp" flush="true" >	
    <jsp:param name="curpage" value="Minutes" />	
</jsp:include>


<div id="pageborder">

<h1>Welcome Chadwick Farms Board Member</h1>
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
<table bgcolor="#000000" cellspacing="1" border="0" width="100%">
  <tr bgcolor="<%= strHeaderRowColor %>">
    <td width="10%" nowrap id="AccessListName" align="center">Unit</td>
    <td width="15%" nowrap id="AccessListName" align="center">IP</td>
    <td width="20%" nowrap id="AccessListName" align="center">Date</td>
    <td width="55%" nowrap id="AccessListName" align="center">Response</td>
  </tr>
<%
    ArrayList listAnswers = (ArrayList)surveyAnswers.getAnswers();
    for( int jnx=0; jnx<listAnswers.size(); ++jnx )
    {
    	Answer answer = (Answer)listAnswers.get(jnx);
    	String strUnit = answer.getUserId();
		String strUserIP = answer.getUserIp();
		String strDate = answer.getAnswerDate();
		String strValue = answer.getValue();
		String strRowColor = ( (jnx + 1) % 2 == 0 ? "#FFFFFF" : "#F5FFFA" );
%>
  <tr bgcolor="<%= strRowColor %>">
    <td nowrap id="AccessListItems" align="center"><%= strUnit %></td>
    <td nowrap id="AccessListItems" align="center"><%= strUserIP %></td>
    <td nowrap id="AccessListItems" align="center"><%= strDate %></td>
    <td nowrap id="AccessListItems" align="center"><%= strValue %></td>
  </tr>
<%
    } // end of for jnx<listAnswer
%>  

</table>
<br><br>
<%	
} // end of for inx<the_survey_answer
%>


</div> <!-- end of pageboarder -->

<jsp:include page="/common/layout/page_footer.jsp" flush="true" />	
	
</body>
</html>
