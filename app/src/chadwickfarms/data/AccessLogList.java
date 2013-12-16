// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccessLogList.java

package chadwickfarms.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AccessLogList
{
    public AccessLogList()
    {
        _arrWrappers = new ArrayList();
    }

    public void add(String name, String uri, String request, String session, String remoteaddr, String useragent, String referrer, 
            Timestamp ts, String project)
    {
        AccessLogWrapper wrapper = new AccessLogWrapper(name, uri, request, session, remoteaddr, useragent, referrer, ts, project);
        _arrWrappers.add(wrapper);
    }

    public void setLocation(int index, String location)
    {
        if(index < 0 || index > _arrWrappers.size() - 1)
        {
            return;
        } else
        {
            AccessLogWrapper wrapper = getWrapper(index);
            wrapper.setLocation(location);
            return;
        }
    }

    public int getSize()
    {
        return _arrWrappers.size();
    }

    public String getName(int iIndex)
    {
        return getWrapper(iIndex).getName();
    }

    public String getUri(int iIndex)
    {
        return getWrapper(iIndex).getUri();
    }

    public String getRequest(int iIndex)
    {
        return getWrapper(iIndex).getRequest();
    }

    public String getSession(int iIndex)
    {
        return getWrapper(iIndex).getSession();
    }

    public String getRemoteaddr(int iIndex)
    {
        return getWrapper(iIndex).getRemoteaddr();
    }

    public String getLocation(int iIndex)
    {
        return getWrapper(iIndex).getLocation();
    }

    public String getUseragent(int iIndex)
    {
        return getWrapper(iIndex).getUseragent();
    }

    public String getReferrer(int iIndex)
    {
        return getWrapper(iIndex).getReferrer();
    }

    public Timestamp getCreatedate(int iIndex)
    {
        return getWrapper(iIndex).getCreatedate();
    }

    public String getProject(int iIndex)
    {
        return getWrapper(iIndex).getProject();
    }

    private AccessLogWrapper getWrapper(int iIndex)
    {
        return (AccessLogWrapper)_arrWrappers.get(iIndex);
    }

    private List _arrWrappers;
    
    /****************************************************
     * <b>AccessLogWrapper</b> <br> 
     * TODO: Insert Class Description Here <br> 
     *
     * First Created:  Dec 8, 2007 1:07:59 PM <br> 
     *
     * @author Brett Melton
     * @version 1
     *
     * <br><font color="red"> Copyright Brett Melton © 2007  All rights reserved. </font> 
     ****************************************************/
    protected class AccessLogWrapper
    {
        private final String name;
        private final String uri;
        private final String request;
        private final String session;
        private final String remoteaddr;
        private final String useragent;
        private final String referrer;
        private final Timestamp createdate;
        private final String project;
        private String location;

        /**
         * @param name
         * @param uri
         * @param request
         * @param session
         * @param remoteaddr
         * @param useragent
         * @param referrer
         * @param ts
         */
        public AccessLogWrapper(String name, String uri, String request, String session,
                                 String remoteaddr, String useragent, String referrer, Timestamp ts,
                                 String project)
        {
            this.name = name;
            this.uri = uri;
            this.request = request;
            this.session = session;
            this.remoteaddr = remoteaddr;
            this.useragent = useragent;
            this.referrer = referrer;
            this.createdate = ts;
            this.project = project;
        }
        /**
         * TODO add method description here
         * @param location
         */
        protected void setLocation( String location )
        {
            this.location = location;
        }
        /** 
         * TODO add method description here
         * @return
         */
        public String getName()
        {
            return name;
        }
        /**
         * TODO add method description here
         * @return
         */
        public String getUri()
        {
            return uri;
        }
        /**
         * TODO add method description here
         * @return
         */
        public String getRequest()
        {
            return request;
        }
        /**
         * TODO add method description here
         * @return
         */
        public String getSession()
        {
            return session;
        }
        /**
         * TODO add method description here
         * @return
         */
        public String getRemoteaddr()
        {
            return remoteaddr;
        }
        /**
         * TODO add method description here
         * @return
         */
        public String getLocation()
        {
            return location;
        }
        /**
         * TODO add method description here
         * @return
         */
        public String getUseragent()
        {
            return useragent;
        }
        /**
         * TODO add method description here
         * @return
         */
        public String getReferrer()
        {
            return referrer; 
        }
        /**
         * TODO add method description here
         * @return
         */
        public Timestamp getCreatedate()
        {
            return createdate;
        }
        /**
         * TODO add method description here
         * @return
         */
        public String getProject()
        {
            return project;
        }
    }

}
