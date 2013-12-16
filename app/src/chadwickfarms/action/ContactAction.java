
package chadwickfarms.action;

/**
 * 
 * @author brettmelton
 */
public class ContactAction extends ChadwickFarmsAction
{
    public static final String s_strPage = "/contact.jsp";

    public ContactAction()
    {
    }

    protected String doActionHandler()
    {
        return s_strPage;
    }

}
