
package chadwickfarms.action;

/**
 * 
 * @author brettmelton
 */
public class PhotosAction extends ChadwickFarmsAction
{
    public static final String s_strPage = "/photos.jsp";

    public PhotosAction()
    {
    }

    protected String doActionHandler()
    {
        return s_strPage;
    }

}
