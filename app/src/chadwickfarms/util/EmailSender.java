// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailSender.java

package chadwickfarms.util;

import java.io.PrintStream;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender
{
    protected String _strFromName;
    protected String _strFromEmail;
    protected String _strToName;
    protected String _strToEmail;
    protected String _strSubject;
    protected List<NVMimeBody> _arrMimeBody;

    protected List<String> _arrCCEmails;
    protected List<String> _arrBCCEmails;

    private String _strHeaderMessageId;
    final private String _smtp_host;

    protected static String DEFAULT_SMTP_HOST = "smtp.comcast.net";

    /**
     * Constructor
     */
     public EmailSender()
     {
         _arrMimeBody = new ArrayList<NVMimeBody>();
         _smtp_host = DEFAULT_SMTP_HOST;
     }
     /**
      * Constructor
      */
      public EmailSender(String smtphost)
      {
          _arrMimeBody = new ArrayList<NVMimeBody>();
          _smtp_host = smtphost;
      }
    /**
     * Clear all data member items
     */
    public void clearAll()
    {
        _strFromName = null;
        _strFromEmail = null;
        _strToName = null;
        _strToEmail = null;
        _strSubject = null;
        _arrCCEmails = null;
        _arrBCCEmails = null;
        _strHeaderMessageId = null;
        _arrMimeBody.clear();
    }
   /**
    * Return a flag indicating if the object is valid<br>
    * All required datamembers must have been set to make this object valid.<br>
    * You <b>cannot</b> send out this email until the object is valid !<br>
    * @return boolean                         Flag indicating if valid or not
    */
    protected boolean isValid()
    {
        if( null == _strFromEmail || null == _strToEmail || null == _strSubject ||
            null == _arrMimeBody || _arrMimeBody.size() < 1 || null == _strHeaderMessageId )
        {
            return false;
        }
        return true;
    }
    /**
     * return string representation of this object
     * @return String
     */
    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append( "\nFrom Email: " + _strFromEmail );
        sb.append( "\nTo Email: " + _strToEmail );
        sb.append( "\nSubject: " + _strSubject );
        sb.append( "\nMimeBody Size: " + ( null == _arrMimeBody ? 0 : _arrMimeBody.size() ) );
        sb.append( "\nHeaderId: " + _strHeaderMessageId );
        return sb.toString();
    }
   /**
    * Compile the email using the data members and send it out
    * @return boolean                          Flag indicating successful send or not
    * @exception AddressException              Thrown if the email address for the from or to does not meet RFC822
    */
    public boolean send() throws AddressException
    {
        try
        {
            Session session = getSession(_smtp_host);
            Message msg = new MimeMessage( session ) {  // override updateHeaders to override the MessageId
                @Override protected void updateHeaders() throws MessagingException {
                    super.updateHeaders();  // let the base set the headers
                    super.setHeader("Message-ID", _strHeaderMessageId);  // change the header to be our id
                }
            };
            msg.setFrom( new InternetAddress( getFromNameEmail() ) );
            msg.setSubject( (null == _strSubject ? "no subject specified" : _strSubject) );
            msg.setSentDate( new java.util.Date(System.currentTimeMillis()) );

            if( _arrMimeBody.size() > 1 )
            { // send using multi part mime bodies
                MimeMultipart mp = new MimeMultipart();
                mp.setSubType( "alternative" );
                for( int inx=0; inx<_arrMimeBody.size(); ++inx )
                {
                    NVMimeBody mimeBody = (NVMimeBody)_arrMimeBody.get(inx);
                    MimeBodyPart mimePart = new MimeBodyPart();
                    mimePart.setContent( mimeBody.getBody(), mimeBody.getMime() );
                    mp.addBodyPart( mimePart );
                }
                msg.setContent( mp );
            }
            else if( _arrMimeBody.size() == 1 )
            { // send using only the individual mime type offered
                NVMimeBody mimeBody = (NVMimeBody)_arrMimeBody.get(0);
                msg.setContent( mimeBody.getBody(), mimeBody.getMime() );
            }
            else
            {
                System.out.println( "Mime Body Array is 0 in size.  Header: " + _strHeaderMessageId + " Subject: " + _strSubject );
            }
            
            msg.addRecipient( Message.RecipientType.TO, getRecipientAddress()[0] );
            for( int inx=0; null != _arrCCEmails && inx < _arrCCEmails.size(); ++inx )
            {
                msg.addRecipient( Message.RecipientType.CC, new InternetAddress( (String)_arrCCEmails.get(inx) ) );
            }
            for( int inx=0; null != _arrBCCEmails && inx < _arrBCCEmails.size(); ++inx )
            {
                msg.addRecipient( Message.RecipientType.BCC, new InternetAddress( (String)_arrBCCEmails.get(inx) ) );
            }
            try
            {
                Transport.send( msg );
            }
            catch(Exception exc)
            {
                throw new Exception( "Unable to queue email to smtp servers: " + _strHeaderMessageId );
            }
        }
        catch(AddressException addressException)
        {
            throw addressException;
        }
        catch(Exception exc)
        {
            System.out.println( "Exception thrown in send." + exc );
            return false;
        }
        return true;
    }
   /**
    * Get the To Recipients to the email message <br>
    * The TO Recipient will always be in the FIRST slot <br>
    * then the CC recipient(s) and BCC recipients(s) will follow.
    * @return InternetAddress                 Address to send to
    * @param Message <b>msg</b>               Email Message Object
    * @exception AddressException
    */
    protected InternetAddress[] getRecipientAddress() throws AddressException
    {
        final int iNumCC = null == _arrCCEmails ? 0 : _arrCCEmails.size();
        final int iNumBCC = null == _arrBCCEmails ? 0 : _arrBCCEmails.size();
        
        InternetAddress[] aInetAddrs = new InternetAddress[ 1 + iNumCC + iNumBCC ];
        int inx = 0;
        aInetAddrs[inx++] = new InternetAddress( _strToEmail );
        for( int jnx=0 ; jnx < iNumCC ; ++jnx )
        {
            aInetAddrs[inx++] = new InternetAddress( (String)_arrCCEmails.get(jnx) );
        }
        for( int jnx=0 ; jnx < iNumBCC ; ++jnx )
        {
            aInetAddrs[inx++] = new InternetAddress( (String)_arrBCCEmails.get(jnx) );
        }
        return aInetAddrs;
    }
   /**
    * Assign a mime type'd message body for the email
    * @param String <b>strMimeType</b>        Mime Type
    * @param String <b>strBodyText</b>        Body of email
    */
    public void assignMimeBodyInformation( String strMimeType, String strBodyText )
    {
        _arrMimeBody.add( new NVMimeBody( strMimeType, strBodyText ) );
    }
   /**
    * Set the From Name for this Email
    * @param String <b>strValue</b>           From Name
    */
    public void setFromName( String strValue )
    {
        _strFromName = strValue;
    }
   /**
    * Set the From Email Address for this Email
    * @param String <b>strValue</b>           From Email Address
    */
    public void setFromEmail( String strValue )
    {
        _strFromEmail = strValue;
    }
   /**
    * Set the To Name for this Email
    * @param String <b>strValue</b>           To Name
    */
    public void setToName( String strValue )
    {
        _strToName = strValue;
    }
   /**
    * Set the To Email Address for this Email
    * @param String <b>strValue</b>           To Email Address
    */
    public void setToEmail( String strValue )
    {
        _strToEmail = strValue;
    }
   /**
    * Set the Subject Line for this Email
    * @param String <b>strValue</b>           Subject Line
    */
    public void setSubject( String strValue )
    {
        _strSubject = strValue;
    }
   /**
    * Set the CC Email Address for this Email
    * @param String <b>strValue</b>           CC Email Address
    */
    public void addCCEmail( String strValue )
    {
        if( null == strValue || strValue.trim().length() < 1 )
            return;
        if( null == _arrCCEmails )
            _arrCCEmails = new ArrayList<String>();
        _arrCCEmails.add( strValue );
    }
   /**
    * Set the BCC Email Address for this Email
    * @param String <b>strValue</b>           BCC Email Address
    */
    public void addBCCEmail( String strValue )
    {
        if( null == strValue || strValue.trim().length() < 1 )
            return;
        if( null == _arrBCCEmails )
            _arrBCCEmails = new ArrayList<String>();
        _arrBCCEmails.add( strValue );
    }
   /**
    * Get the Message ID value for the header of this Email
    * @return String                          Message ID
    */
    final public String getHeaderMessageId()
    {
        return _strHeaderMessageId;
    }
   /**
    * Set the Message ID value for the header of this Email
    * @param String <b>strValue</b>           Message ID
    */
    public void setHeaderMessageId( String strValue )
    {
        _strHeaderMessageId = strValue;
    }    
   /**
    * Return the From Name for this Email
    * @return String                          From Name
    */
    protected String getFromNameEmail()
    {
        return getNameEmail( _strFromName, _strFromEmail );
    }
   /**
    * Return an email Name/Email combination
    * @return String                          Name/Email String combined
    * @param String <b>strName</b>            Name For Email
    * @param String <b>strEmail</b>           Email Address For Email
    */
    private String getNameEmail(String strName, String strEmail)
    {
        StringBuffer sbNameEmail = new StringBuffer();
        if( null != strName && null != strEmail )
        {
            sbNameEmail.append( strName );
            sbNameEmail.append( " <" );
            sbNameEmail.append( strEmail );
            sbNameEmail.append( ">" );
        }
        else if( null == strName && null == strEmail )
        {
            sbNameEmail.append( "Aptimus@Aptimus.com" );
        }
        else if( null == strName )
        {
            sbNameEmail.append( strEmail );
        }
        else if( null == strEmail )
        {
            sbNameEmail.append( strName );
            sbNameEmail.append( " <" );
            sbNameEmail.append( strName );
            sbNameEmail.append( "@Aptimus.com>" );
        }
        return sbNameEmail.toString();
    }
    /**
     * return the count of mime body types for this email
     * @return int                          Number of email bodies
     */
    public int getBodyCount()
    {
        return ( null == _arrMimeBody ? 0 : _arrMimeBody.size() );
    }
    /**
     * Return the Mime Type at a specified index
     * @return String                       Mime Type
     * @param int <b>index</b>              Index of Mime/Body array
     */
    public String getMimeType( int index )
    {
        if( index + 1 > _arrMimeBody.size() )
            return null;
        return( ((NVMimeBody)_arrMimeBody.get(index)).getMime() );
    }
    /**
     * Return the Body String at a specified index
     * @return String                       Body Text
     * @param int <b>index</b>              Index of Mime/Body array
     */
    public String getBody( int index )
    {
        if( index + 1 > _arrMimeBody.size() )
            return null;
        return( ((NVMimeBody)_arrMimeBody.get(index)).getBody() );
    }

   /**
    * Assign the primary smtp servers or the primary smtp load balancer
    */
    protected Session getSession(String strHostName)
    {
        Properties props = new Properties();
        props.put( "mail.smtp.host", strHostName );
        // Socket connection timeout value in milliseconds. Default is infinite timeout.
        props.put( "mail.smtp.connectiontimeout", "600000" ); // 10 mins * 60 secs * 1000 millis
        // Socket I/O timeout value in milliseconds. Default is infinite timeout.
        props.put( "mail.smtp.timeout", "600000" ); // 10 mins * 60 secs * 1000 millis
        
        Authenticator auth = null;
        if( strHostName.indexOf("comcast") > -1 )
            auth = new ComcastAuthenticator();
        else
            auth = new LocalAuthenticator();
        Session session;
        session = Session.getInstance( props, auth );                
        session.setDebug( false );
        return session;
    }
    
    private class ComcastAuthenticator extends Authenticator
    {
        @Override
        public PasswordAuthentication getPasswordAuthentication()
        {
            final String username = "brettmelton";
            final String password = "kalhar1LISA";
            return new PasswordAuthentication( username, password );
        }
    }
    private class LocalAuthenticator extends Authenticator
    {
        @Override
        public PasswordAuthentication getPasswordAuthentication()
        {
            final String username = "brett";
            final String password = "kalhar1LISA";
            return new PasswordAuthentication( username, password );
        }
    }
    
   /****************************************************
    * <b>NVMimeBody</b> <br> 
    * Inner / Utility Class <br> 
    *
    *
    * First Created:  11/22/2003 01:59 PM <br> 
    *
    * @author Brett Melton
    * @version 1.0
    *
    * <br><font color="red"> Copyright Kalhar Heritage Consulting © 2003  All rights reserved. </font> 
    ****************************************************/ 
    class NVMimeBody
    {
        private String _strMime;
        private String _strBody;
       /**
        * Constructor
        * @param String <b>strMime</b>       Mime Type
        * @param String <b>strBody</b>       Body for this Mime Type
        */
        public NVMimeBody( final String strMime, final String strBody )
        {
            _strMime = strMime;
            _strBody = strBody;
        }
       /**
        * Return out the Mime Type
        * @return String                      Mime Type
        */
        public final String getMime() { return _strMime; }
       /**
        * Return out the Body for this Mime Type
        * @return String                      Body for Email
        */
        public final String getBody() { return _strBody; }
    }
}
