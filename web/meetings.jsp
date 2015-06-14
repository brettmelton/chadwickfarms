<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Chadwick Farms</title>
<link href="/inc/css/melton.css" rel="stylesheet" type="text/css">
<link href="/inc/css/minutes.css" rel="stylesheet" type="text/css">

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
<jsp:useBean id="the_user_comments" type="String" class="java.lang.String" scope="request" />

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script src="/inc/js/chadwickfarms.js"></script>
<script src="/inc/js/cf_meetings.js"></script>

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

<h2>Helpful Maintenance</h2>
<a href="/documents/FiresideHomeSolutionsFireplace.pdf" target="_blank">Fireside Home Solutions</a><br>
<a href="/documents/ChadwickFireplaceLightingInstructions.pdf" target="_blank">Fireplace Lighting</a><br>
<a href="/documents/WinterStormList.pdf" target="_blank">Winter Storm List</a><br>
<br><u>Fireplace Repair &amp; Service</u><br>
B and C Comfort<br>
21127 164th Drive SE, Monroe 98272<br>
425-402-4456
<br> &nbsp; &nbsp; - or - <br>
Fireside Home Solutions<br>
13200 NE 20th St, Bellevue 98005<br>
425-747-3473<br>

<br>

<h2>Documents</h2>
<a href="/documents/cf-hoa-dues.html" target="_blank">2015 HOA Dues</a><br>
<a href="/documents/CFMoveInProtocol.pdf" target="_blank">Move In Protocol</a><br>
<a href="/documents/CF-RulesRegs.pdf" target="_blank">Community Rules &amp; Regulations</a><br>
<a href="/documents/CF-Declaration.pdf" target="_blank">Community Declaration</a><br>
<a href="/documents/BoardMemberToolKit.pdf" target="_blank">Board Member tool kit</a><br>
<br>

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
<i>comments (optional)</i>
<textarea name="survey_comments" rows="4" cols="25" scrollbar="true"><%= the_user_comments %></textarea><br>
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
Have something to sell or give away?  Let your board know.  FREE AD SPACE!!<br>
1. Take a photo of your item<br>
2. Determine a price if applicable<br>
3. Send the description and photo to the board so that it can be shown.<br>
<br>
<br>

<section id="minutes">

<h2>Meeting Minutes Archive</h2>
<h3>March 2015</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20150309.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Fire and Sprinkler Testing</li>
<li>Window Cleaning</li>
<li>Wetland Tree trimming & removal</li>
</ul>
</div>
<h3>January 2015</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20150112.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Board positions and terms announced</li>
<li>Leaking Roof and Vent Issue</li>
</ul>
</div>
<h3>2013 Annual Meeting</h3>
<div>
<a href="/documents/minutes/MeetingMinutesAnnual201312.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Budget Ratification</li>
<li>Board Membership</li>
<li>Special Announcements</li>
</ul>
</div>
<h3>November 2014</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20141117.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Gutter Cleaning</li>
<li>Landscape Winteriation</li>
<li>Leaking Roof and Vent Issue</li>
</ul>
</div>
<h3>September 2014</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20140922.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Various Projects Completed</li>
<li>Speed Bump</li>
<li>Annual Meeting Scheduled</li>
</ul>
</div>
<h3>July 2014</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20140721.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Vent and Window Cleaning</li>
<li>Curb Paint</li>
<li>Pest Issues and Leaking Faucet</li>
<li>CCR Review and Revision</li>
</ul>
</div>
<h3>May 2014</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20140519.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Landscaping and Tree Trimming</li>
<li>Dryer Vent Cleaning</li>
<li>Mail box door</li>
</ul>
</div>
<h3>March 2014</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20140324.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Lighting by Mail Room</li>
<li>New Landscaping Company</li>
<li>Community Volunteer Day</li>
</ul>
</div>
<h3>January 2014</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20140127.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>New Board Members</li>
<li>Landscaping</li>
<li>Chadwick Farms Website</li>
<li>Community Lighting</li>
</ul>
</div>
<h3>October 2013</h3>
<div>
<a href="/documents/minutes/MeetingMinutes201310.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Potential Projects for 2014 and beyond</li>
<li>Board Membership</li>
<li>2014 Budget Proposal</li>
<li>Sump Pump Noise Fix</li>
<li>Gutters</li>
</ul>
</div>
<h3>August 2013</h3>
<div>
<a href="/documents/minutes/MeetingMinutes201308.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Release of Lien document</li>
<li>Remedial repair close to being finished</li>
<li>Grading and Drainage work</li>
<li>Asphalt seal-coated and re-striped</li>
<li>Roof leak in unit 8327</li>
</ul>
</div>
<h3>June 25, 2013</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20130625.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
This meeting included invited guests: Dan Zimberhoff, Ralph Allen, and Russ Cole<br>
Primary topics covered:
<ul>
<li>Comments from guests</li>
<li>Front door painting</li>
<li>Removal of lien</li>
</ul>
</div>
<h3>June 03, 2013</h3>
<div>
<a href="/documents/minutes/MeetingMinutes20130603.pdf" target="_blank">&nbsp;&nbsp;- View PDF - </a><br>
Primary topics covered:
<ul>
<li>Audit review</li>
<li>Construction updates</li>
<li>Cascade Pest Control</li>
</ul>
</div>
</section>

<br><br><br>
</div>
</div>

<jsp:include page="/common/layout/page_footer.jsp" flush="true" />	
	
</body>
</html>
