// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SignInAction.java

package chadwickfarms.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// Referenced classes of package chadwickfarms.action:
//            Action

public class SignInAction extends ChadwickFarmsAction
{

    public SignInAction()
    {
    }

    protected String doActionHandler()
    {
        String strTask = _request.getParameter("task");
        if("signin".equals(strTask))
        {
            String strName = _request.getParameter("name");
            if(strName != null && strName.trim().length() > 0 && !"visitor".equalsIgnoreCase(strName))
            {
                HttpSession theSession = _request.getSession(true);
                theSession.setAttribute("session_fname", strName);
                super.assignCookie("name", strName);
            }
            return "/index.jsp";
        } else
        {
            return "/signin.jsp";
        }
    }

    public static final String s_strPage = "/signin.jsp";
}
