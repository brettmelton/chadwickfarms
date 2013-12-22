package chadwickfarms.survey;


public class Answer
{
	private final String strUserId;
	private final String strUserIp;
	private final String strAnswerDate;
	private final String strValue;
	
    public Answer(String strUserIdentifier, String strUserIpAddress, String strCreateDate, String strSurveyAnswer)
    {
    	this.strUserId = strUserIdentifier;
    	this.strUserIp = strUserIpAddress;
    	this.strAnswerDate = strCreateDate;
    	this.strValue = strSurveyAnswer;
    }

	/**
	 * @return the strUserId
	 */
	public String getUserId() {
		return strUserId;
	}

	/**
	 * @return the strUserIp
	 */
	public String getUserIp() {
		return strUserIp;
	}

	/**
	 * @return the strAnswerDate
	 */
	public String getAnswerDate() {
		return strAnswerDate;
	}

	/**
	 * @return the strValue
	 */
	public String getValue() {
		return strValue;
	}

}
