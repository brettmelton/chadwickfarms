// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ItemComparator.java

package chadwickfarms.lists;

import java.util.Comparator;

import chadwickfarms.lists.Item;

// Referenced classes of package chadwickfarms.lists:
//            Item

public class ItemComparator implements Comparator<Item>
{
    private String _strColumnName;
        
    public ItemComparator(String strColumnName)
    {
        _strColumnName = strColumnName;
    }

    @Override
    public int compare(Item item0, Item item1)
    {
        String value0 = (String)item0.get( _strColumnName );
        String value1 = (String)item1.get( _strColumnName );
        
        return value0.compareTo( value1 );
    }
}
