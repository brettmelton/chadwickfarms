package chadwickfarms.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import chadwickfarms.survey.Survey;
import chadwickfarms.survey.SurveyOption;

public class SurveyData extends DataObject
{

	public List<Survey> getOpenSurveys()
	{
		List<Survey> surveys = getSurveys(true);
		loadOptions( surveys );
		return surveys;
	}
	
	public List<Survey> getClosedSurveys()
	{
		List<Survey> surveys = getSurveys(false);
		loadOptions( surveys );		
		return surveys;
	}
	public void storeSurveyAnswer( int iSurveyId, int iOptionId, String strUserId, String strIpAddress )
	{
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
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
                if( null != rs ) rs.close();
                if( null != pstmt ) pstmt.close();
                if( null != conn ) conn.close();
            } catch (Exception ex) {
                System.out.println( "Exception when closing db objects: " + ex.getMessage() );
            }
        }		
	}


	private void loadOptions( List<Survey> surveys )
	{
		for( Survey survey : surveys )
		{
			loadOption(survey);
		}
	}
	private void loadOption( Survey survey )
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
            SurveyOption surveyOption = null;
            while( rs.next() )
            {
            	int id = rs.getInt("survey_option_id");
            	int surveyId = rs.getInt("survey_id");
            	String optionValue = rs.getString("value");
            	surveyOption = SurveyOption.newSurveyOption( id, surveyId, optionValue );
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
	private List<Survey> getSurveys( final boolean activeOnly )
	{
		List<Survey> listSurveys = new ArrayList<Survey>();
		
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
            Survey survey = null;
            while( rs.next() )
            {
            	int isActive = rs.getInt("active");
            	survey = Survey.newSurvey( rs.getInt("survey_id"), rs.getString("name"), rs.getString("question"), (isActive == 1 ? true : false) );
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
