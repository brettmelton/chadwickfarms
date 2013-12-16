
package chadwickfarms.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

public class LoginServlet extends HttpServlet
{
    private static final long serialVersionUID = 6322441264331417480L;

    /**
     * Entry Point for the servlet
     * @param HttpServletRequest request Request Object
     * @param HttpServletResponse response Response Object
     * @exception ServletException Servlet Exception
     * @exception IOException IO Exception
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        if ( null != request.getParameter("un") )
        {
            String username = request.getParameter("un");
            String password = request.getParameter("pw");
            session.setAttribute("j_username", username);
            session.setAttribute("un", username);
            session.setAttribute("pw", password);
            session.setAttribute("orig", request.getParameter("orig"));
        }
        String strView = request.getParameter("nextpage");
        if ( null == strView )
        {
            System.out.println( "service Exception. nextpage is missing from request." );
            strView = "/index.jsp";
        }
        redirectTo( strView, request, response );
    }

    /**
     * redirectTo send to next page
     * @param String viewURL redirect URL
     * @param HttpServletRequest req Request Object
     * @param HttpServletResponse resp Response Object
     * @exception throws ServletException Error In Servlet Processing
     * @exception IOException Input/Output Error
     */
    protected void redirectTo( String viewURL, HttpServletRequest req, HttpServletResponse rep ) throws ServletException, IOException
    {
        if( viewURL.startsWith( "http" ) )
        {
            rep.sendRedirect(viewURL);
        }
        else
        {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( viewURL );
            dispatcher.forward( req, rep );
        }
    }

    /**
     * destroy the servlet
     * @return destroy                    called by the server when the servlet is garbage collected
     */
    public void destroy()
    {
    }

   /**
    * initialize the servlet
    * @param ServletConfig config        Servlet Config Context
    * @exception ServletException        Servlet Exception
    */
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
}
