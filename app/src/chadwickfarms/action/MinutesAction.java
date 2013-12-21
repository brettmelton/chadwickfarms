
package chadwickfarms.action;

import java.util.ArrayList;
import java.util.List;


import chadwickfarms.data.SurveyData;
import chadwickfarms.survey.Survey;

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
        	processSurvey();

    	SurveyData surveyData = new SurveyData();
    	List<Survey> surveys = surveyData.getOpenSurveys();
        _request.setAttribute("surveys", (ArrayList)surveys);
        return s_strPage;
    }

    private void processSurvey()
    {
    	String strSurveyId = _request.getParameter("survey_id");
    	String strOptionId = _request.getParameter("question" + strSurveyId);
    	String strUserId = _request.getParameter("user_id");
    	
// TODO - I have their user id now.   cookie this value and in the doActionHandler,
//		see if they've answered surveys before and put the list of ID's into the request
    	
    	String strIpAddress = _request.getRemoteAddr();
    	    	
    	try
    	{
    		int iSurveyId = Integer.parseInt(strSurveyId);
    		int iOptionId = Integer.parseInt(strOptionId);
    		
        	SurveyData surveyData = new SurveyData();
        	surveyData.storeSurveyAnswer( iSurveyId, iOptionId, strUserId, strIpAddress );
    		
    		_request.setAttribute("the_survey_id", strSurveyId);
    		_request.setAttribute("the_option_id", strOptionId);
    		_request.setAttribute("the_user_id", strUserId);
    	}
    	catch( NumberFormatException nfe )
    	{
    		// ignore
    	}    	
    }
}
