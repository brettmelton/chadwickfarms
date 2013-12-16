// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ActionFactory.java

package chadwickfarms.action;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package chadwickfarms.action:
//            Action

public class ChadwickFarmsActionFactory
{

    protected ChadwickFarmsActionFactory()
    {
        _mActions = new HashMap();
    }

    public static ChadwickFarmsAction getAction(String strActionName)
    {
        ChadwickFarmsAction act = null;
        ChadwickFarmsActionFactory actFact = instance();
        if(actFact != null)
        {
            act = (ChadwickFarmsAction)actFact._mActions.get(strActionName);
            if(act == null)
            {
            	String strActRealName = "";
                try
                {
                    strActRealName = (new StringBuilder("chadwickfarms.action.")).append(strActionName).toString();
                    Class clsAct = Class.forName(strActRealName);
                    act = (ChadwickFarmsAction)clsAct.newInstance();
                    if(act != null)
                        actFact._mActions.put(strActionName, act);
                }
                catch(Exception exc)
                {
                	System.out.println( "strActReadName = " + strActRealName );
                    System.out.println((new StringBuilder("ChadwickFarmsActionFactory Exception: ")).append(exc).toString());
                }
            }
        }
        return act;
    }

    public static ChadwickFarmsActionFactory instance()
    {
        if(g_instance == null)
            g_instance = new ChadwickFarmsActionFactory();
        return g_instance;
    }

    private Map _mActions;
    private static ChadwickFarmsActionFactory g_instance;
}
