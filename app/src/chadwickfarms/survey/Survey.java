package chadwickfarms.survey;

import java.util.ArrayList;
import java.util.List;

public class Survey
{
	private final int id;
	private final String name;
	private final String question;
	private final boolean active;
	
	private List<SurveyOption> options;
	
	private Survey (int id, String name, String question, boolean active)
	{
		this.id = id;
		this.name = name;
		this.question = question;
		this.active = active;
		
		this.options = new ArrayList<SurveyOption>();
	}

	public static Survey newSurvey(int id, String name, String question, boolean active)
	{
		return new Survey( id, name, question, active );
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return active;
	}

	public String getQuestion() {
		return question;
	}

	public List<SurveyOption> getOptions() {
		return options;
	}

	public void addOption(SurveyOption surveyOption)
	{
		this.options.add(surveyOption);
	}

}
