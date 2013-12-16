// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileHandler.java

package chadwickfarms.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHandler
{
    private BufferedReader _dataInput;
    private PrintWriter _dataOutput;
    private boolean _bHealth;
    
    private String _strInputFileName;
    private String _strOutputFileName;
    
    static public final int IN = 1;
    static public final int OUT = 2;
    static public final int INOUT = 3;

   /**
    * Constructor
    */
    public FileHandler(int iFormat, String strFileName)
    {
        if( (iFormat & IN) == IN )
            _strInputFileName = strFileName;
        if( (iFormat & OUT) == OUT )
            _strOutputFileName = strFileName;
        if( null != _strInputFileName && null != _strOutputFileName )
            _strOutputFileName += ".out";
        initialize(iFormat);
    }
   /**
    * Constructor
    */
    public FileHandler(String strInputFileName, String strOutputFileName)
    {
        _strInputFileName = strInputFileName;
        _strOutputFileName = strOutputFileName;
        initialize(INOUT);
    }
   /**
    * Inidicator if the files are successfully opened
    * @return boolean                    flag
    */
    public boolean isHealthy() { return _bHealth; }
    
   /**
    * Initialize the system file handlers
    * @param int <b>iFormat</b>         Format for input or output
    */
    private void initialize(int iFormat)
    {
        _bHealth = true;
        try
        {
            if( (iFormat & IN) == IN )
            {
                _dataInput = new BufferedReader( new FileReader( _strInputFileName ) );
            }
            else if( (iFormat & OUT) == OUT )
            {
                FileOutputStream fos = new FileOutputStream( _strOutputFileName, true );
                OutputStreamWriter osw = new OutputStreamWriter( fos, "US-ASCII" );
                _dataOutput = new PrintWriter( osw );
            }
            else
            {
                _bHealth = false;
            }
        }
        catch(Exception exc)
        {
            _bHealth = false;
            System.out.println("FileHandler::initialize Exception: " + exc);
        }
    }
    
   /**
    * Get a Line from the Input
    * @return String                     Input Line just read
    */
    public String getLine()
    {
        String strLine = null;
        try
        {
            if( null != _dataInput )
                strLine = _dataInput.readLine();
            if( null != strLine )
                strLine = strLine.trim();
        }
        catch(Exception exc)
        {
            System.out.println("Exception: " + exc);
        }
        return strLine;
    }
   /**
    * Put a line into the output file
    * @return boolean                    Successful write or not
    * @param String strLine              Output Line to write
    */
    public boolean putLine2(String strLine)
    {
        if( null != _dataOutput )
        {
            _dataOutput.println(strLine);
            _dataOutput.flush();
            return true;
        }
        return false;
    }
   /**
    * rename the input file to the same name w/ a timestamp appended to the end.<br>
    * this function is used if your input file is a processing file and your want<br>
    * to save it with a timestamp of the processing time.
    * @return String                        New Name
    */
    public String renameInputFileAsTimestamp()
    {
        return renameFileAsTimestamp( _strInputFileName );
    }
   /**
    * rename the output file to the same name w/ a timestamp appended to the end.<br>
    * this function is used if your output file is a processing file and your want<br>
    * to save it with a timestamp of the processing time.
    * @return String                        New Name
    */
    public String renameOutputFileAsTimestamp()
    {
        return renameFileAsTimestamp( _strOutputFileName );
    }        
   /**
    * rename the file to the same name w/ a timestamp appended to the end.<br>
    * this function is used if your file is a processing file and your want<br>
    * to save it with a timestamp of the processing time.
    * @return String                        New Name
    */
    private String renameFileAsTimestamp(String strFileName)
    {
        if( null == strFileName ) return null;
        File file = new File(strFileName);
        if( ! file.exists() ) return null;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
        final String strNewName = strFileName + "." + sdf.format( now );
        File newFile = new File( strNewName );
        file.renameTo(newFile);
        return strNewName;
    }
   /**
    * Close the system file handlers
    */
    public boolean close()
    {
        boolean bClosed = false;
        try
        {
            if( null != _dataInput ) { _dataInput.close(); bClosed = true; }
        }
        catch(Exception exc)
        {
            System.out.println("Exception: " + exc);
        }
        _dataInput = null;

        if( null != _dataOutput ) { _dataOutput.close(); bClosed = true; }
        _dataOutput = null;
        
        return bClosed;
    }

   /**
    * Make sure everything is closed
    */
    @Override
    public void finalize()
    {
        if( close() )
            System.out.println("FileHandler: Closing File Handles.  Please cleanup yourself.");
    }
}
