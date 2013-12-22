package chadwickfarms.action;

import java.util.Collections;
import java.util.List;

import chadwickfarms.data.SurveyData;
import chadwickfarms.survey.SurveyAnswers;

public class BoardOnlyAction extends ChadwickFarmsAction
{
    public static final String s_strPage = "/boardonly.jsp";

	@Override
	protected String doActionHandler()
	{
    	SurveyData surveyData = new SurveyData();
    	List<SurveyAnswers> surveyAnswers = surveyData.getOpenSurveysWithAnswers();
    	Collections.sort(surveyAnswers);  // Sort these by survey id descending
		_request.setAttribute("the_survey_answers", surveyAnswers);
		return s_strPage;
	}

}
