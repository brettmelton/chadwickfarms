// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IPAddressUtils.java

package chadwickfarms.data;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.StringTokenizer;

public abstract class IPAddressUtils
{

    protected IPAddressUtils()
    {
    }

    public static boolean isStaticIP(String strAddress)
    {
        char achAddress[] = strAddress.toCharArray();
        int iAddressLength = achAddress.length;
        if(Arrays.binarySearch(NUMBERS, achAddress[0]) < 0 || Arrays.binarySearch(NUMBERS, achAddress[iAddressLength - 1]) < 0)
            return false;
        return countPeriods(achAddress) == 3;
    }

    public static String[] createRange(String strAddress)
    {
        if(!Character.isDigit(strAddress.charAt(0)))
            return new String[0];
        if(!strAddress.endsWith(".") && countPeriods(strAddress.toCharArray()) < 3)
            strAddress = (new StringBuilder(String.valueOf(strAddress))).append(".").toString();
        String strAddressStart = strAddress;
        String strAddressEnd = strAddress;
        int iStartPeriods = countPeriods(strAddressStart.toCharArray());
        if(iStartPeriods < 3)
            strAddressStart = (new StringBuilder(String.valueOf(strAddressStart))).append(s_astr0s[iStartPeriods]).toString();
        int iEndPeriods = countPeriods(strAddressEnd.toCharArray());
        if(iEndPeriods < 3)
            strAddressEnd = (new StringBuilder(String.valueOf(strAddressEnd))).append(s_astr255s[iEndPeriods]).toString();
        if(strAddressStart.endsWith("."))
            strAddressStart = (new StringBuilder(String.valueOf(strAddressStart))).append("0").toString();
        if(strAddressEnd.endsWith("."))
            strAddressEnd = (new StringBuilder(String.valueOf(strAddressEnd))).append("255").toString();
        return (new String[] {
            strAddressStart, strAddressEnd
        });
    }

    public static int countPeriods(char ach[])
    {
        int iTokenCount = 0;
        int length = ach.length;
        for(int inx = 1; inx < length; inx++)
            if(ach[inx] == '.')
                iTokenCount++;

        return iTokenCount;
    }

    public static String toStringAddress(long ipAddress)
    {
        String strIpAddress = null;
        try
        {
            long upperBytes = 0L;
            long longByte = 0L;
            byte bytes[] = new byte[4];
            for(int inx = 3; inx >= 0; inx--)
            {
                double dValue = Math.floor(ipAddress - Math.abs(upperBytes) >> 8 * inx);
                longByte = Double.valueOf(dValue).longValue();
                bytes[4 - inx - 1] = (byte)(int)longByte;
                upperBytes += longByte << 8 * inx;
            }

            strIpAddress = InetAddress.getByAddress(bytes).getHostAddress();
        }
        catch(UnknownHostException uhe)
        {
            System.out.println((new StringBuilder("Unexpected exception: ")).append(uhe.getMessage()).toString());
        }
        return strIpAddress;
    }

    public static long ipToLongValue(String ipAddress)
    {
        long lReturn = -1L;
        if(ipAddress == null || ipAddress.trim().length() < 1)
            return lReturn;
        try
        {
            lReturn = convert(ipAddress);
        }
        catch(RuntimeException rExc)
        {
            System.out.println((new StringBuilder("IPAddress: ")).append(ipAddress).append(" could not convert to long. ").append(rExc.getMessage()).toString());
        }
        catch(Exception exc)
        {
            System.out.println((new StringBuilder("IPAddress: ")).append(ipAddress).append(" could not convert to long. ").append(exc.getMessage()).toString());
        }
        return lReturn;
    }

    private static long convert(String ipAddress)
        throws Exception
    {
        int aiAddressParts[] = splitAddress(ipAddress);
        StringBuilder sbHexVal = new StringBuilder();
        for(int iParts = 0; iParts < aiAddressParts.length; iParts++)
        {
            int intVal = aiAddressParts[iParts];
            if(intVal < 0 || intVal > 255)
                throw new Exception("invalid ip part");
            String hexTemp = Integer.toHexString(intVal);
            if(hexTemp.length() < 2)
                hexTemp = (new StringBuilder("0")).append(hexTemp).toString();
            sbHexVal.append(hexTemp);
        }

        return Long.parseLong(sbHexVal.toString(), 16);
    }

    private static int[] splitAddress(String ipAddress)
    {
        int aiIPs[] = new int[4];
        int iIndex = 0;
        char ach[] = ipAddress.toCharArray();
        int length = ach.length;
        for(int inx = 0; inx < length; inx++)
        {
            char ch = ach[inx];
            if(ch == '.')
                iIndex++;
            else
                aiIPs[iIndex] = aiIPs[iIndex] * 10 + Character.digit(ch, 10);
        }

        return aiIPs;
    }

    public static final boolean isValid(String strIPAddress)
    {
        boolean bValid = true;
        int iIP4 = 0;
        StringTokenizer tokenizer = new StringTokenizer(strIPAddress, ".");
        if(tokenizer.countTokens() != 4)
        {
            bValid = false;
        } else
        {
            int iIP1 = Integer.parseInt(tokenizer.nextToken());
            int iIP2 = Integer.parseInt(tokenizer.nextToken());
            int iIP3 = Integer.parseInt(tokenizer.nextToken());
            iIP4 = Integer.parseInt(tokenizer.nextToken());
            if(iIP1 > 255 || iIP2 > 255 || iIP3 > 255 || iIP4 > 255)
                bValid = false;
            else
            if(iIP1 == 0 && iIP2 == 0 && iIP3 == 0 && iIP4 == 0)
                bValid = false;
        }
        return bValid;
    }

    public static void main(String args[])
    {
        String astrIps[] = {
            "24.16.48.226", "206.73.209.94", "87.168.91.159"
        };
        String args1[];
        int j = (args1 = astrIps).length;
        for(int i = 0; i < j; i++)
        {
            String ip = args1[i];
            try
            {
                System.out.println(convert(ip));
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

    }

    public static final long s_lOneFirstOctetValue = 0x1000000L;
    private static final String s_astr0s[] = {
        "0.0.0.0", "0.0.0", "0.0", "0"
    };
    private static final String s_astr255s[] = {
        "255.255.255.255", "255.255.255", "255.255", "255"
    };
    public static final String PERIOD = ".";
    public static final String ZERO = "0";
    public static final String TWOFIFTYFIVE = "255";
    private static final char NUMBERS[] = "0123456789".toCharArray();

}
