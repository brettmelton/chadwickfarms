package chadwickfarms.survey;

public class Option
{
	
	private final int id;
	private final int surveyId;
	private final String value;
	
	private Option(int id, int surveyId, String value)
	{
		this.id = id;
		this.surveyId = surveyId;
		this.value = value;
	}

	public static Option newSurveyOption(int id, int surveyId, String value)
	{
		return new Option( id, surveyId, value );
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
