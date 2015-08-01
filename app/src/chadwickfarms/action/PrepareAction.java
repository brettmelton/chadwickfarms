
package chadwickfarms.action;

/**
 * 
 * @author brettmelton
 */
public class PrepareAction extends ChadwickFarmsAction
{
    public static final String s_strPage = "/incident.jsp";

    public PrepareAction()
    {
    }

    protected String doActionHandler()
    {
        return s_strPage;
    }

}
