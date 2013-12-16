// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ItemList.java

package chadwickfarms.lists;

import java.util.*;

// Referenced classes of package chadwickfarms.lists:
//            ItemComparator, Item

public class ItemList
{

    public static ItemList newInstance()
    {
        return new ItemList();
    }

    private ItemList()
    {
        _items = new ArrayList();
    }

    public void sortList(String strColumnName)
    {
        ItemComparator itemComparator = new ItemComparator(strColumnName);
        Collections.sort(_items, itemComparator);
    }

    public void setHeader(String strHeader)
    {
        _astrHeader = tokenString(strHeader);
    }

    public String[] getHeader()
    {
        return _astrHeader;
    }

    public void addItem(String strLine)
    {
        _items.add(Item.newInstance(_astrHeader, tokenString(strLine)));
    }

    private String[] tokenString(String strLine)
    {
        StringTokenizer tokenizer = new StringTokenizer(strLine, ",");
        String astr[] = new String[tokenizer.countTokens()];
        int inx = 0;
        while(tokenizer.hasMoreTokens()) 
            astr[inx++] = tokenizer.nextToken();
        return astr;
    }

    private List _items;
    private String _astrHeader[];
}
