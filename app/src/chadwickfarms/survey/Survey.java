package chadwickfarms.survey;

import org.apache.commons.lang3.StringUtils;

public class Survey
{
	private final int id;
	private final String name;
	private final String question;
	private final boolean active;

	protected Survey()
	{
		this.id = -1;
		this.name = StringUtils.EMPTY;
		this.question = StringUtils.EMPTY;
		this.active = false;
	}
	
	protected Survey (int id, String name, String question, boolean active)
	{
		this.id = id;
		this.name = name;
		this.question = question;
		this.active = active;
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
}
