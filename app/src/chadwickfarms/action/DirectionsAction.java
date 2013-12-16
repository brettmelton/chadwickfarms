
package chadwickfarms.action;

/**
 * 
 * @author brettmelton
 */
public class DirectionsAction extends ChadwickFarmsAction
{
    public static final String s_strPage = "/directions.jsp";

    public DirectionsAction()
    {
    }

    protected String doActionHandler()
    {
        return s_strPage;
    }

}
