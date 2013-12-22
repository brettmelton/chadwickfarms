package chadwickfarms.survey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SurveyOptions extends Survey
{
	private List<Option> options;

	public SurveyOptions()
	{
		super(-1, StringUtils.EMPTY, StringUtils.EMPTY, false);
		options = Collections.emptyList();
	}
	
	protected SurveyOptions(int id, String name, String question, boolean active)
	{
		super(id, name, question, active);
		options = new ArrayList<Option>();
	}
	
	public static SurveyOptions newSurvey(int id, String name, String question, boolean active)
	{
		return new SurveyOptions( id, name, question, active );
	}

	public List<Option> getOptions() {
		return options;
	}

	public void addOption(Option surveyOption)
	{
		this.options.add(surveyOption);
	}


}
