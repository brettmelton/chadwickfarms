// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataObject.java

package chadwickfarms.data;

import java.io.PrintStream;
import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

import chadwickfarms.data.DataObject;

public class DataObject
{
    private static DataSource s_ds;

    /**
     * Static function to get all the system parameters
     * @return              Mapping of system parameters
     */    
    public static Map<String, String> getSystemParameters()
    {
        DataObject dObj = new DataObject();
        
        Map<String, String> mapReturn = new HashMap<String, String>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            String strSql = "SELECT parameter_name, parameter_value FROM system_parameter";
            conn = dObj.getConnectionFromPool();
            pstmt = conn.prepareStatement(strSql);
            rs = pstmt.executeQuery();
            while( rs.next() )
            {
                mapReturn.put( rs.getString("parameter_name"), rs.getString("parameter_value") );
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
        return mapReturn;
    }

    /**
     * 
     * @return
     */
    protected Connection getConnectionFromPool()
    {
        Connection conn = null;
        try
        {
            conn = getDataSource().getConnection();
        }
        catch(Exception exc)
        {
            System.out.println("Exception trying to get connection from ds: " + exc );
        }
        return conn;
    }
    /**
     * 
     * @return
     */
    protected Connection getDirectConnection()
    {
        Connection conn = null;
        try
        {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            java.util.Properties props = new java.util.Properties();
            props.setProperty("user", "webuser");
            props.setProperty("password", "webuser!");
            conn = java.sql.DriverManager.getConnection( "jdbc:mysql://localhost/brettmelton_com", props);
        }
        catch(Exception exc)
        {
            System.out.println("Exception trying to get connection from drivermanager: " + exc );
        }
        return conn;
    }
    /**
     * 
     * @return
     */
    private DataSource getDataSource()
    {
        if( null == s_ds )
        {
            try
            {
                Context init = new InitialContext();
                Context ctx = (Context) init.lookup("java:comp/env");
                s_ds = (DataSource) ctx.lookup("jdbc/brettmelton_com");
            }
            catch ( NamingException namingExc )
            {
                System.out.println( "NamingException: " + namingExc );
            }
        }
        return s_ds;
    }

}
