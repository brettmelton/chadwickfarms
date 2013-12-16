// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Action.java

package chadwickfarms.action;

import java.io.PrintStream;
import java.util.*;

import javax.servlet.http.*;

import chadwickfarms.data.AccessLog;
import chadwickfarms.data.DataObject;
import chadwickfarms.user.UserData;

public abstract class ChadwickFarmsAction
{

    protected abstract String doActionHandler();

    public ChadwickFarmsAction()
    {
        _request = null;
        _response = null;
        _theSession = null;
        _bLogValue = true;
    }

    public void setLogValue(boolean b)
    {
        _bLogValue = b;
    }

    public String doAction(HttpServletRequest request, HttpServletResponse response)
    {
        _request = request;
        _response = response;
        _theSession = _request.getSession(true);
        _theSession.setMaxInactiveInterval(3600);
        boolean bLogActivity = true;
        if("false".equals((String)_theSession.getAttribute("logvalue")))
            bLogActivity = false;
        else
        if("false".equals(_request.getParameter("logvalue")))
        {
            _theSession.setAttribute("logvalue", "false");
            bLogActivity = false;
        }
        UserData ud = new UserData(_request);
        String strUserName = setUserSettingsLastUsed();
        if(bLogActivity && _bLogValue)
            logActivity(ud, strUserName);
        
        String strPage = doActionHandler();
        return strPage;
    }

    private String setUserSettingsLastUsed()
    {
        String strUserName = getCurrentUserName();
        String strTheme = getCurrentTheme();
        if(strUserName != null)
            strUserName = strUserName.trim();
        if(strUserName == null || strUserName.length() < 2)
            strUserName = "visitor";
        if(!"visitor".equalsIgnoreCase(strUserName))
        {
            _request.setAttribute("name", strUserName);
            _theSession.setAttribute("session_fname", strUserName);
        }
        if("visitor".equalsIgnoreCase(strUserName))
            _theSession.setAttribute("session_visitor", "true");
        else
            _theSession.setAttribute("session_visitor", "false");
        _request.setAttribute("theme", strTheme);
        _theSession.setAttribute("session_theme", strTheme);
        return strUserName;
    }

    private String getCurrentUserName()
    {
        String strUserName = getCookieValue("name");
        if(strUserName != null && strUserName.trim().length() > 1 && !"visitor".equalsIgnoreCase(strUserName))
            return strUserName;
        if((strUserName = _request.getParameter("name")) != null)
            return strUserName;
        if((strUserName = (String)_theSession.getAttribute("session_fname")) != null)
            return strUserName;
        else
            return "visitor";
    }

    private String getCurrentTheme()
    {
        String strTheme = getCookieValue("theme");
        if(strTheme != null && strTheme.trim().length() > 1)
            return strTheme;
        if((strTheme = _request.getParameter("theme")) != null)
            return strTheme;
        if((strTheme = (String)_theSession.getAttribute("session_theme")) != null)
            return strTheme;
        else
            return "sb";
    }

    protected void logActivity(UserData ud, String strUserName)
    {
        if("192.168.1.1".equals(ud.get("RemoteAddr")))
            return;
        String strUserAgent = ud.get("User-agent");
        String strReferer = ud.get("Referer");
        if(isBot(strUserAgent, strReferer))
        {
            System.out.println((new StringBuilder("UserAgent not logged: ")).append(strUserAgent).toString());
            return;
        }
        List arrValues = new ArrayList();
        arrValues.add(strUserName);
        String strRequestURI = _request.getRequestURI();
        arrValues.add(strRequestURI);
        StringBuffer sbValue = new StringBuffer();
        for(Enumeration enumNames = _request.getParameterNames(); enumNames.hasMoreElements();)
        {
            String strName = (String)enumNames.nextElement();
            String astrValues[] = _request.getParameterValues(strName);
            if(astrValues != null && astrValues.length > 0)
            {
                for(int inx = 0; inx < astrValues.length; inx++)
                {
                    sbValue.append(strName);
                    sbValue.append('=');
                    sbValue.append(astrValues[inx]);
                    sbValue.append('&');
                }

            }
        }

        String strRequestValue = sbValue.toString();
        if(strRequestValue.length() > 1)
            arrValues.add(strRequestValue.substring(0, strRequestValue.length() - 1));
        else
            arrValues.add("");
        sbValue = new StringBuffer();
        for(Enumeration enumSessionNames = _theSession.getAttributeNames(); enumSessionNames.hasMoreElements();)
        {
            String strName = (String)enumSessionNames.nextElement();
            Object obj = _theSession.getAttribute(strName);
            if(obj instanceof String)
            {
                sbValue.append(strName);
                sbValue.append('=');
                sbValue.append((String)obj);
                sbValue.append('&');
            }
        }

        arrValues.add(sbValue.toString());
        arrValues.add(ud.get("RemoteAddr"));
        arrValues.add(ud.get("User-agent"));
        arrValues.add(ud.get("Referer"));
        String astrDataValues[] = new String[arrValues.size()];
        System.arraycopy(((Object) (arrValues.toArray())), 0, astrDataValues, 0, arrValues.size());
        AccessLog accessLog = new AccessLog();
        accessLog.addLog(astrDataValues);
    }

    public void assignCookie(String strName, String strValue)
    {
        Cookie cookie1 = new Cookie(strName, strValue);
        cookie1.setPath("/");
        cookie1.setMaxAge(0x9e3400);
        _response.addCookie(cookie1);
    }

    public static String getCookieValue(HttpServletRequest request, String strName)
    {
        String strValue = null;
        if(strName == null || strName.trim().length() < 1)
            return strValue;
        Cookie cookies[] = request.getCookies();
        int iNumCookies = cookies != null ? cookies.length : 0;
        for(int inx = 0; inx < iNumCookies; inx++)
        {
            Cookie cookie = cookies[inx];
            if(!strName.equals(cookie.getName()))
                continue;
            strValue = cookie.getValue();
            break;
        }

        return strValue;
    }

    private String getCookieValue(String strName)
    {
        return getCookieValue(_request, strName);
    }

    private boolean isBot(String strUserAgent, String strReferer)
    {
        if(strUserAgent == null)
            return false;
        strUserAgent = strUserAgent.toLowerCase(Locale.getDefault());
        String as[];
        int j = (as = KNOWN_BOTS).length;
        for(int i = 0; i < j; i++)
        {
            String bot = as[i];
            if(strUserAgent.indexOf(bot) > -1)
                return true;
        }

        return strReferer != null && strReferer.toLowerCase(Locale.getDefault()).startsWith("http://www.google.com/search");
    }

    public static String KNOWN_BOTS[] = {
        "msnbot", "googlebot", "yahoo! slurp", "panscient.com"
    };
    protected HttpServletRequest _request;
    protected HttpServletResponse _response;
    protected HttpSession _theSession;
    protected boolean _bLogValue;

}
