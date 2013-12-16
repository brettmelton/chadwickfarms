// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Item.java

package chadwickfarms.lists;

import java.util.HashMap;
import java.util.Map;

public final class Item
{

    public static Item newInstance(String astrNames[], String astrValues[])
    {
        return new Item(astrNames, astrValues);
    }

    private Item()
    {
        _values = new HashMap();
    }

    private Item(String astrNames[], String astrValues[])
    {
        this();
        if(astrNames == null || astrNames.length < 1 || astrValues == null || astrValues.length < 1)
            return;
        for(int inx = 0; inx < astrNames.length; inx++)
        {
            if(inx > astrValues.length - 1)
                break;
            _values.put(astrNames[inx], astrValues[inx]);
        }

    }

    public String get(String name)
    {
        return (String)_values.get(name);
    }

    private Map _values;
}
