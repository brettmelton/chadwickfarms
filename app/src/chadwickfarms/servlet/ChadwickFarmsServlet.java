// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ControllerServlet.java

package chadwickfarms.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import chadwickfarms.action.ChadwickFarmsAction;
import chadwickfarms.action.ChadwickFarmsActionFactory;

public class ChadwickFarmsServlet extends HttpServlet
{
	private static final long serialVersionUID = -6916863404567648423L;
	private ChadwickFarmsAction _action;

    public ChadwickFarmsServlet()
    {
        _action = null;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String strPage = null;
        if(_action != null)
            strPage = _action.doAction(request, response);
        else
            strPage = request.getParameter("v");
        if(strPage == null || strPage.trim().length() < 1)
            strPage = "/index.jsp";
        if(strPage.indexOf(".jsp") > -1)
        {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(strPage);
            dispatcher.forward(request, response);
        } else
        {
            response.sendRedirect(strPage);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        String actionName = config.getInitParameter("action");
        if(actionName != null)
        {
            _action = ChadwickFarmsActionFactory.getAction(actionName);
            String strLogValue = config.getInitParameter("logvalue");
            if("false".equals(strLogValue))
                _action.setLogValue(false);
        }
    }
}
