// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserData.java

package chadwickfarms.user;

import java.lang.reflect.Method;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class UserData extends HashMap
{

    public UserData(HttpServletRequest request)
    {
        _req = request;
        initialize();
    }

    public String get(String strItemName)
    {
        return (String)super.get(strItemName);
    }

    private void initialize()
    {
        for(int inx = 0; inx < _astrUserItems.length; inx++)
        {
            String strItemValue = null;
            String strItemName = _astrUserItems[inx];
            String strMethodName = (new StringBuilder("get")).append(strItemName).toString();
            try
            {
                Method method = _req.getClass().getMethod(strMethodName, null);
                if(method != null)
                    strItemValue = (String)method.invoke(_req, null);
            }
            catch(Exception exception) { }
            if(strItemValue == null)
                strItemValue = _req.getHeader(strItemName.toUpperCase());
            if(strItemValue != null)
            {
                if(strItemValue.length() > 256)
                    strItemValue = strItemValue.substring(0, 255);
                put(strItemName, strItemValue);
            }
        }

    }

    public String getHeader()
    {
        return "REMOTEADDR,USERAGENT,REFERRER";
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(get("RemoteAddr"));
        sb.append((new StringBuilder(",")).append(get("User-agent")).toString());
        sb.append((new StringBuilder(",")).append(get("Referer")).toString());
        return sb.toString();
    }

    private static final long serialVersionUID = 0xa1d0cceba8b42060L;
    private final HttpServletRequest _req;
    public static final String REMOTE_ADDR = "RemoteAddr";
    public static final String SERVER_NAME = "ServerName";
    public static final String USER_AGENT = "User-agent";
    public static final String REFERER = "Referer";
    public static final String _astrUserItems[] = {
        "RemoteAddr", "ServerName", "User-agent", "Referer"
    };

}
