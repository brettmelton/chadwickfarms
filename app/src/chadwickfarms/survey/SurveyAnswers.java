package chadwickfarms.survey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SurveyAnswers implements Comparable<SurveyAnswers>
{
	private final SurveyOptions surveyOptions;
	private final List<Answer> surveyAnswers;
	
	public SurveyAnswers()
	{
		surveyOptions = new SurveyOptions();
		surveyAnswers = Collections.emptyList();
	}
	
	public SurveyAnswers(SurveyOptions surveyQuestions)
	{
		this.surveyOptions = surveyQuestions;
		surveyAnswers = new ArrayList<Answer>();
	}

	public String getName() {
		return surveyOptions.getName();
	}
	public boolean isActive() {
		return surveyOptions.isActive();
	}
	public String getQuestion() {
		return surveyOptions.getQuestion();
	}
	public List<Answer> getAnswers() {
		return surveyAnswers;
	}
	public void addAnswer(Answer answer) {
		this.surveyAnswers.add(answer);
	}

	@Override
	public int compareTo(SurveyAnswers otherSurvey) {
        if (otherSurvey.surveyOptions.getId() > surveyOptions.getId()) return 1;
        if (otherSurvey.surveyOptions.getId() == surveyOptions.getId()) return 0;
        return -1;
	}
}
