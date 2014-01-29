
package chadwickfarms.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import chadwickfarms.data.SurveyData;
import chadwickfarms.survey.SurveyOptions;
/**
 * @author brettmelton
 */
public class MinutesAction extends ChadwickFarmsAction
{
    public static final String s_strPage = "/meetings.jsp";

    public MinutesAction()
    {
    }

    protected String doActionHandler()
    {
        if( "true".equalsIgnoreCase( _request.getParameter("submit_survey") ) )
        {
        	processSurvey();
        }
        else
        {
        	String strUserId = super.getCookieValue(_request, "USERID");
        	if( StringUtils.isNotBlank(strUserId))
        		_request.setAttribute("the_user_id", strUserId);
        }

    	SurveyData surveyData = new SurveyData();
    	List<SurveyOptions> surveys = surveyData.getOpenSurveys();
        _request.setAttribute("surveys", (ArrayList<SurveyOptions>)surveys);
        return s_strPage;
    }

    private void processSurvey()
    {
    	String strSurveyId = _request.getParameter("survey_id");
    	String strOptionId = _request.getParameter("question" + strSurveyId);
    	String strUserId = _request.getParameter("user_id");
    	String strComments = _request.getParameter("survey_comments");
    	if( null != strComments )
    	{
    		strComments = strComments.trim();
    		if( strComments.length() > 240 )
    			strComments = strComments.substring(0, 240);
    	}
        HttpSession theSession = _request.getSession(true);
        theSession.setAttribute("USERID", strUserId);
        super.assignCookie("USERID", strUserId);
    	
    	String strIpAddress = _request.getRemoteAddr();
    	try
    	{
    		int iSurveyId = Integer.parseInt(strSurveyId);
    		int iOptionId = Integer.parseInt(strOptionId);
    		
        	SurveyData surveyData = new SurveyData();
        	surveyData.storeSurveyAnswer( iSurveyId, iOptionId, strUserId, strIpAddress, strComments );
    		
    		_request.setAttribute("the_survey_id", strSurveyId);
    		_request.setAttribute("the_option_id", strOptionId);
    		_request.setAttribute("the_user_id", strUserId);
    		_request.setAttribute("the_user_comments", strComments);
    	}
    	catch( NumberFormatException nfe )
    	{
    		// ignore
    	}    	
    }
}
