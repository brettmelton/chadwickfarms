
package chadwickfarms.action;

/**
 * 
 * @author brettmelton
 */
public class HistoryAction extends ChadwickFarmsAction
{
    public static final String s_strPage = "/history.jsp";

    public HistoryAction()
    {
    }

    protected String doActionHandler()
    {
        return s_strPage;
    }

}
