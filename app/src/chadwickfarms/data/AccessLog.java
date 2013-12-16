// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccessLog.java

package chadwickfarms.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Locale;

import chadwickfarms.action.ChadwickFarmsAction;
import chadwickfarms.data.AccessLogList;
import chadwickfarms.lists.ItemList;

// Referenced classes of package chadwickfarms.data:
//            DataObject, AccessLogList

public class AccessLog extends DataObject
{

    public AccessLog()
    {
    }

    public String getLastLoggedInName( String strIPAddr )
    {
        String strUserName = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            String strSql = "SELECT name, create_date FROM access_log WHERE ";
            strSql += "remoteaddr = ? AND name NOT LIKE '%isitor' ORDER BY ";
            strSql += "create_date DESC";


            conn = getConnectionFromPool();
            try
            {
                pstmt = conn.prepareStatement(strSql);
                pstmt.setString( 1, strIPAddr);
                rs = pstmt.executeQuery();
            }
            catch(SQLException sqlExc)
            {
                System.out.println("Exception getting connection from pool: " + sqlExc );
                if( sqlExc.toString() != null && sqlExc.toString().indexOf( "link failure" ) > -1  )
                {
                    conn = getDirectConnection();
                    if( null == conn ) throw new SQLException("Unable to get direct connection.");
                    pstmt = conn.prepareStatement(strSql);
                    pstmt.setString( 1, strIPAddr);
                    rs = pstmt.executeQuery();
                }
            }
            if( null != rs && rs.next() )
            {
                strUserName = rs.getString( 1 );
            }
        
        }
        catch(SQLException sqlExc)
        {
            System.out.println( "SQLException: " + sqlExc );
        }
        finally
        {
            try {
                if( null != rs ) rs.close();
                if( null != pstmt ) pstmt.close();
                if( null != conn ) conn.close();
            } catch (Exception ex) {}
        }
        return strUserName;
    }

    public void addLog( String[] astrValues )
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try
        {
            String strSql = "INSERT INTO access_log VALUES ( ?,?,?,?,?,?,?, now(), \"chadwickfarms\" )";
            conn = getConnectionFromPool();
            try
            {
                pstmt = conn.prepareStatement(strSql);
                for( int inx=0; inx<astrValues.length; ++inx )
                {
                    pstmt.setString( (inx+1), astrValues[inx]);
                }
                pstmt.execute();
            }
            catch(SQLException sqlExc)
            {
                System.out.println("Exception getting connection from pool: " + sqlExc );
                if( sqlExc.toString() != null && sqlExc.toString().indexOf( "link failure" ) > -1  )
                {
                    conn = getDirectConnection();
                    if( null == conn ) throw new SQLException("Unable to get direct connection.");
                    pstmt = conn.prepareStatement(strSql);
                    for( int inx=0; inx<astrValues.length; ++inx )
                    {
                        pstmt.setString( (inx+1), astrValues[inx]);
                    }
                    pstmt.execute();
                }
            }
        }
        catch(SQLException sqlExc)
        {
            System.out.println( "SQLException: " + sqlExc );
        }
        catch(RuntimeException rExc)
        {
            System.out.println( "RuntimeException: " + rExc.getMessage() );
        }
        finally
        {
            try {
                if( null != pstmt ) pstmt.close();
                if( null != conn ) conn.close();
            } catch (Exception ex) {}
        }
    }

    public ItemList getCountByNameAndIP()
    {   
        ItemList itemList = ItemList.newInstance();

        String strLine = "Count,Name,IP Address";
        itemList.setHeader( strLine );

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            
            String strSql = "SELECT count(*), name, remoteaddr FROM access_log " +
                            "GROUP BY remoteaddr, name";
            conn = getConnectionFromPool();
            try
            {
                pstmt = conn.prepareStatement(strSql);
                rs = pstmt.executeQuery();
            }
            catch(SQLException sqlExc)
            {
                System.out.println("Exception getting connection from pool: " + sqlExc );
                if( sqlExc.toString() != null && sqlExc.toString().indexOf( "link failure" ) > -1  )
                {
                    conn = getDirectConnection();
                    if( null == conn ) throw new SQLException("Unable to get direct connection.");
                    pstmt = conn.prepareStatement(strSql);
                    rs = pstmt.executeQuery();
                }
            }
            while( null != rs && rs.next() )
            {
                strLine = rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3);
                itemList.addItem( strLine );
            }
        }
        catch(SQLException sqlExc)
        {
            System.out.println( "SQLException: " + sqlExc );
        }
        finally
        {
            try {
                if( null != rs ) rs.close();
                if( null != pstmt ) pstmt.close();
                if( null != conn ) conn.close();
            } catch (Exception ex) {}
        }
        return itemList;
    }


}
