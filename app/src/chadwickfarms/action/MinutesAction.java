
package chadwickfarms.action;

/**
 * 
 * @author brettmelton
 */
public class MinutesAction extends ChadwickFarmsAction
{
    public static final String s_strPage = "/meetings.jsp";

    public MinutesAction()
    {
    }

    protected String doActionHandler()
    {
        return s_strPage;
    }

}
