package chadwickfarms.survey;

public class SurveyOption
{
	
	private final int id;
	private final int surveyId;
	private final String value;
	
	private SurveyOption(int id, int surveyId, String value)
	{
		this.id = id;
		this.surveyId = surveyId;
		this.value = value;
	}

	public static SurveyOption newSurveyOption(int id, int surveyId, String value)
	{
		return new SurveyOption( id, surveyId, value );
	}

	public int getId() {
		return id;
	}
	public int getSurveyId() {
		return surveyId;
	}

	public String getValue() {
		return value;
	}


}
