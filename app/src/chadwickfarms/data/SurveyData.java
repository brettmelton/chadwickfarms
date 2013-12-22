package chadwickfarms.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import chadwickfarms.survey.Answer;
import chadwickfarms.survey.SurveyAnswers;
import chadwickfarms.survey.Option;
import chadwickfarms.survey.SurveyOptions;

public class SurveyData extends DataObject
{
	public static DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

	public List<SurveyAnswers> getOpenSurveysWithAnswers()
	{
		List<SurveyAnswers> listSurveyAnswers = new ArrayList<SurveyAnswers>();
		
		List<SurveyOptions> listSurveyQuestions = getOpenSurveys();
		listSurveyQuestions.addAll(getClosedSurveys());
		
		for( SurveyOptions surveyQuestions : listSurveyQuestions )
		{
			SurveyAnswers surveyAnswers = loadAnswersForSurvey(surveyQuestions);
			listSurveyAnswers.add( surveyAnswers );
		}
		return listSurveyAnswers;
	}
	public List<SurveyOptions> getOpenSurveys()
	{
		List<SurveyOptions> surveys = getSurveys(true);
		loadOptions( surveys );
		return surveys;
	}
	
	public List<SurveyOptions> getClosedSurveys()
	{
		List<SurveyOptions> surveys = getSurveys(false);
		loadOptions( surveys );		
		return surveys;
	}
	
	public SurveyAnswers loadAnswersForSurvey(SurveyOptions surveyQuestions)
	{
		SurveyAnswers surveyAnswers = new SurveyAnswers(surveyQuestions);
		int iSurveyId = surveyQuestions.getId();
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuilder sbSql = new StringBuilder();
        sbSql.append( "SELECT a.user_identifier, b.value, a.user_ip_address, a.create_date " );
        sbSql.append( "FROM survey_answers a, survey_option b " );
        sbSql.append( "WHERE a.survey_id = ? AND a.survey_option_id = b.survey_option_id " );
        sbSql.append( "ORDER BY a.user_identifier, b.value, a.create_date desc " );
        String strSql = sbSql.toString();
        try
        {
            conn = getConnectionFromPool();
            pstmt = conn.prepareStatement(strSql);
            pstmt.setInt( 1, iSurveyId );
            rs = pstmt.executeQuery();
            Answer answer = null;
            while( rs.next() )
            {
            	String strUserIdentifier = rs.getString("user_identifier");
            	String strSurveyAnswer = rs.getString("value");
            	String strUserIpAddress = rs.getString("user_ip_address");
            	java.sql.Timestamp dateCreated = rs.getTimestamp("create_date");
            	String strCreateDate = df.format(dateCreated);
            	
            	answer = new Answer(strUserIdentifier, strUserIpAddress, strCreateDate, strSurveyAnswer);
            	surveyAnswers.addAnswer(answer);
            }
        }
        catch(SQLException sqlExc)
        {
            System.out.println("Exception: " + sqlExc.getMessage() );
        }
        finally
        {
            try {
                if( null != rs ) rs.close();
                if( null != pstmt ) pstmt.close();
                if( null != conn ) conn.close();
            } catch (Exception ex) {
                System.out.println( "Exception when closing db objects: " + ex.getMessage() );
            }
        }		

		return surveyAnswers;
	}
	public void storeSurveyAnswer( int iSurveyId, int iOptionId, String strUserId, String strIpAddress )
	{
		Connection conn = null;
        PreparedStatement pstmt = null;
        String strSql = "INSERT INTO survey_answers (survey_id, survey_option_id, user_identifier, user_ip_address, create_date) VALUES (?,?,?,?,?)";

        try
        {
            conn = getConnectionFromPool();
            pstmt = conn.prepareStatement(strSql);
            pstmt.setInt( 1, iSurveyId );
            pstmt.setInt( 2,  iOptionId );
            pstmt.setString( 3, strUserId );
            pstmt.setString( 4, strIpAddress );
            pstmt.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
            pstmt.execute();            
        }
        catch(SQLException sqlExc)
        {
            System.out.println("Exception: " + sqlExc.getMessage() );
        }
        finally
        {
            try {
                if( null != pstmt ) pstmt.close();
                if( null != conn ) conn.close();
            } catch (Exception ex) {
                System.out.println( "Exception when closing db objects: " + ex.getMessage() );
            }
        }		
	}


	private void loadOptions( List<SurveyOptions> surveys )
	{
		for( SurveyOptions survey : surveys )
		{
			loadOption(survey);
		}
	}
	private void loadOption( SurveyOptions survey )
	{
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String strSql = "SELECT * FROM survey_option WHERE survey_id = ?";
        try
        {
            conn = getConnectionFromPool();
            pstmt = conn.prepareStatement(strSql);
            pstmt.setInt( 1, survey.getId() );
            rs = pstmt.executeQuery();
            Option surveyOption = null;
            while( rs.next() )
            {
            	int id = rs.getInt("survey_option_id");
            	int surveyId = rs.getInt("survey_id");
            	String optionValue = rs.getString("value");
            	surveyOption = Option.newSurveyOption( id, surveyId, optionValue );
            	survey.addOption( surveyOption );
            }
        }
        catch(SQLException sqlExc)
        {
            System.out.println("Exception: " + sqlExc.getMessage() );
        }
        finally
        {
            try {
                if( null != rs ) rs.close();
                if( null != pstmt ) pstmt.close();
                if( null != conn ) conn.close();
            } catch (Exception ex) {
                System.out.println( "Exception when closing db objects: " + ex.getMessage() );
            }
        }		
	}
	private List<SurveyOptions> getSurveys( final boolean activeOnly )
	{
		List<SurveyOptions> listSurveys = new ArrayList<SurveyOptions>();
		
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String strSql = "SELECT * FROM survey WHERE active = ? ORDER BY create_date desc";
        try
        {
            conn = getConnectionFromPool();
            pstmt = conn.prepareStatement(strSql);
            pstmt.setInt( 1, activeOnly ? 1 : 0 );
            rs = pstmt.executeQuery();
            SurveyOptions survey = null;
            while( rs.next() )
            {
            	int isActive = rs.getInt("active");
            	survey = SurveyOptions.newSurvey( rs.getInt("survey_id"), rs.getString("name"), rs.getString("question"), (isActive == 1 ? true : false) );
            	listSurveys.add( survey );
            }
        }
        catch(SQLException sqlExc)
        {
            System.out.println("Exception: " + sqlExc.getMessage() );
        }
        finally
        {
            try {
                if( null != rs ) rs.close();
                if( null != pstmt ) pstmt.close();
                if( null != conn ) conn.close();
            } catch (Exception ex) {
                System.out.println( "Exception when closing db objects: " + ex.getMessage() );
            }
        }		
		return listSurveys;
	}
}
