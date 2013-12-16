// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateWrapper.java

package chadwickfarms.util;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class DateWrapper
{

    public DateWrapper()
    {
        _currentTime = null;
        _calendar = Calendar.getInstance();
        stampTime();
    }

    public DateWrapper(int year, int month, int date)
    {
        _currentTime = null;
        _calendar = Calendar.getInstance();
        setDate(year, month, date);
        stampTime();
    }

    public DateWrapper(String strMMDDYYYY)
    {
        _currentTime = null;
        _calendar = Calendar.getInstance();
        parseStringMMDDYYYY(strMMDDYYYY);
        stampTime();
    }

    public DateWrapper(Date date)
    {
        _currentTime = null;
        _calendar = Calendar.getInstance();
        if(date != null)
            _calendar.setTime(date);
        stampTime();
    }

    public long getTimeMillis()
    {
        return _currentTime.getTime();
    }

    public java.sql.Date getDateObject()
    {
        return new java.sql.Date(getTimeMillis());
    }

    public final int getHour()
    {
        return _calendar.get(10);
    }

    public final int getMinute()
    {
        return _calendar.get(12);
    }

    public final int getSecond()
    {
        return _calendar.get(13);
    }

    public final int getDate()
    {
        return _calendar.get(5);
    }

    public final int getMonth()
    {
        return _calendar.get(2);
    }

    public final int getYear()
    {
        return _calendar.get(1);
    }

    public final int getWeekOfYear()
    {
        return _calendar.get(3);
    }

    public final String getDayOfWeek()
    {
        String strDayOfWeek = null;
        int dayofweek = _calendar.get(7);
        switch(dayofweek)
        {
        case 1: // '\001'
            strDayOfWeek = "Sunday";
            break;

        case 2: // '\002'
            strDayOfWeek = "Monday";
            break;

        case 3: // '\003'
            strDayOfWeek = "Tuesday";
            break;

        case 4: // '\004'
            strDayOfWeek = "Wednesday";
            break;

        case 5: // '\005'
            strDayOfWeek = "Thursday";
            break;

        case 6: // '\006'
            strDayOfWeek = "Friday";
            break;

        case 7: // '\007'
            strDayOfWeek = "Saturday";
            break;

        default:
            strDayOfWeek = "Sunday";
            break;
        }
        return strDayOfWeek;
    }

    public Timestamp getTimestamp()
    {
        return _currentTime;
    }

    public boolean parseStringMMDDYYYY(String strDate)
    {
        boolean bSuccess = false;
        String strSlash = "/";
        String strBkSlash = "\\";
        String strDash = "-";
        String strDot = ".";
        int iMonth = 0;
        int iDay = 0;
        int iYear = 0;
        StringTokenizer toke = null;
        try
        {
            toke = new StringTokenizer(strDate, strSlash);
            bSuccess = true;
        }
        catch(NoSuchElementException nosuchelementexception) { }
        if(!bSuccess)
            try
            {
                toke = new StringTokenizer(strDate, strDash);
                bSuccess = true;
            }
            catch(NoSuchElementException nosuchelementexception1) { }
        if(!bSuccess)
            try
            {
                toke = new StringTokenizer(strDate, strBkSlash);
                bSuccess = true;
            }
            catch(NoSuchElementException nosuchelementexception2) { }
        if(!bSuccess)
            try
            {
                toke = new StringTokenizer(strDate, strDot);
                bSuccess = true;
            }
            catch(NoSuchElementException nosuchelementexception3) { }
        try
        {
            iMonth = Integer.parseInt((String)toke.nextElement());
            iDay = Integer.parseInt((String)toke.nextElement());
            iYear = Integer.parseInt((String)toke.nextElement());
            setDate(iYear, iMonth, iDay);
        }
        catch(NumberFormatException e)
        {
            bSuccess = false;
        }
        return bSuccess;
    }

    public void setDate(int iYear, int iMonth, int iDay)
    {
        _calendar.set(iYear, iMonth, iDay);
        stampTime(_calendar.getTimeInMillis());
    }

    public void rollDate(int amount)
    {
        _calendar.add(5, amount);
        stampTime();
    }

    public void rollMonth(int amount)
    {
        _calendar.add(2, amount);
        stampTime();
    }

    public void rollYear(int amount)
    {
        _calendar.add(1, amount);
        stampTime();
    }

    private DateWrapper stampTime()
    {
        _currentTime = new Timestamp(_calendar.getTimeInMillis());
        _calendar.setTime(new java.sql.Date(_calendar.getTimeInMillis()));
        return this;
    }

    private DateWrapper stampTime(long lMilliseconds)
    {
        _currentTime = new Timestamp(lMilliseconds);
        _calendar.setTime(new java.sql.Date(lMilliseconds));
        return this;
    }

    public String getFormattedTime(String strFormat)
    {
        SimpleDateFormat fmt = new SimpleDateFormat(strFormat);
        return fmt.format(_calendar.getTime());
    }

    public String getFormattedTime()
    {
        return getFormattedTime("MM/dd/yyyy");
    }

    public String getISO8601Date()
    {
        String strDate = getFormattedTime("yyyy-MM-dd");
        String strTime = getFormattedTime("hh:mm:ss");
        String strTimeZone = Integer.toString(_calendar.get(15) / 0x36ee80);
        return (new StringBuilder(String.valueOf(strDate))).append("T").append(strTime).append(strTimeZone).append(":00").toString();
    }

    public static void main(String args[])
    {
        DateWrapper now = new DateWrapper();
        System.out.println(now.getFormattedTime());
        now.rollMonth(-5);
        System.out.println(now.getFormattedTime());
        now.rollMonth(-5);
        System.out.println(now.getFormattedTime());
        now.rollMonth(-5);
        System.out.println(now.getFormattedTime());
    }

    private static final long serialVersionUID = 0x393b5d8cf5f302f3L;
    private Timestamp _currentTime;
    private Calendar _calendar;
}
