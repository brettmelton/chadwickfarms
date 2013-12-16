// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Base32.java

package chadwickfarms.util;

import java.io.PrintStream;

public class Base32
{

    public Base32()
    {
    }

    public static String encode(byte bytes[])
    {
        StringBuffer base32 = new StringBuffer((bytes.length * 8 + 4) / 5);
        for(int i = 0; i < bytes.length;)
        {
            int currByte = bytes[i++] & 0xff;
            base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(currByte >> 3));
            int digit = (currByte & 7) << 2;
            if(i >= bytes.length)
            {
                base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit));
                break;
            }
            currByte = bytes[i++] & 0xff;
            base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit | currByte >> 6));
            base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(currByte >> 1 & 0x1f));
            digit = (currByte & 1) << 4;
            if(i >= bytes.length)
            {
                base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit));
                break;
            }
            currByte = bytes[i++] & 0xff;
            base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit | currByte >> 4));
            digit = (currByte & 0xf) << 1;
            if(i >= bytes.length)
            {
                base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit));
                break;
            }
            currByte = bytes[i++] & 0xff;
            base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit | currByte >> 7));
            base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(currByte >> 2 & 0x1f));
            digit = (currByte & 3) << 3;
            if(i >= bytes.length)
            {
                base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit));
                break;
            }
            currByte = bytes[i++] & 0xff;
            base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit | currByte >> 5));
            base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(currByte & 0x1f));
        }

        return base32.toString();
    }

    public static byte[] decodeToBytes(String base32)
        throws IllegalArgumentException
    {
        byte bytes[];
        switch(base32.length() % 8)
        {
        case 1: // '\001'
        case 3: // '\003'
        case 6: // '\006'
            throw new IllegalArgumentException("non canonical Base32 string length");

        case 2: // '\002'
        case 4: // '\004'
        case 5: // '\005'
        default:
            bytes = new byte[(base32.length() * 5) / 8];
            break;
        }
        int offset = 0;
        for(int i = 0; i < base32.length();)
        {
            int lookup = base32.charAt(i++) - 50;
            if(lookup < 0 || lookup >= base32Lookup.length)
                throw new IllegalArgumentException("invalid character in Base32 string");
            byte digit = base32Lookup[lookup];
            if(digit == -1)
                throw new IllegalArgumentException("invalid character in Base32 string");
            byte nextByte = (byte)(digit << 3);
            lookup = base32.charAt(i++) - 50;
            if(lookup < 0 || lookup >= base32Lookup.length)
                throw new IllegalArgumentException("invalid character in Base32 string");
            digit = base32Lookup[lookup];
            if(digit == -1)
                throw new IllegalArgumentException("invalid character in Base32 string");
            bytes[offset++] = (byte)(nextByte | digit >> 2);
            nextByte = (byte)((digit & 3) << 6);
            if(i >= base32.length())
            {
                if(nextByte != 0)
                    throw new IllegalArgumentException("non canonical bits at end of Base32 string");
                break;
            }
            lookup = base32.charAt(i++) - 50;
            if(lookup < 0 || lookup >= base32Lookup.length)
                throw new IllegalArgumentException("invalid character in Base32 string");
            digit = base32Lookup[lookup];
            if(digit == -1)
                throw new IllegalArgumentException("invalid character in Base32 string");
            nextByte |= (byte)(digit << 1);
            lookup = base32.charAt(i++) - 50;
            if(lookup < 0 || lookup >= base32Lookup.length)
                throw new IllegalArgumentException("invalid character in Base32 string");
            digit = base32Lookup[lookup];
            if(digit == -1)
                throw new IllegalArgumentException("invalid character in Base32 string");
            bytes[offset++] = (byte)(nextByte | digit >> 4);
            nextByte = (byte)((digit & 0xf) << 4);
            if(i >= base32.length())
            {
                if(nextByte != 0)
                    throw new IllegalArgumentException("non canonical bits at end of Base32 string");
                break;
            }
            lookup = base32.charAt(i++) - 50;
            if(lookup < 0 || lookup >= base32Lookup.length)
                throw new IllegalArgumentException("invalid character in Base32 string");
            digit = base32Lookup[lookup];
            if(digit == -1)
                throw new IllegalArgumentException("invalid character in Base32 string");
            bytes[offset++] = (byte)(nextByte | digit >> 1);
            nextByte = (byte)((digit & 1) << 7);
            if(i >= base32.length())
            {
                if(nextByte != 0)
                    throw new IllegalArgumentException("non canonical bits at end of Base32 string");
                break;
            }
            lookup = base32.charAt(i++) - 50;
            if(lookup < 0 || lookup >= base32Lookup.length)
                throw new IllegalArgumentException("invalid character in Base32 string");
            digit = base32Lookup[lookup];
            if(digit == -1)
                throw new IllegalArgumentException("invalid character in Base32 string");
            nextByte |= (byte)(digit << 2);
            lookup = base32.charAt(i++) - 50;
            if(lookup < 0 || lookup >= base32Lookup.length)
                throw new IllegalArgumentException("invalid character in Base32 string");
            digit = base32Lookup[lookup];
            if(digit == -1)
                throw new IllegalArgumentException("invalid character in Base32 string");
            bytes[offset++] = (byte)(nextByte | digit >> 3);
            nextByte = (byte)((digit & 7) << 5);
            if(i >= base32.length())
            {
                if(nextByte != 0)
                    throw new IllegalArgumentException("non canonical bits at end of Base32 string");
                break;
            }
            lookup = base32.charAt(i++) - 50;
            if(lookup < 0 || lookup >= base32Lookup.length)
                throw new IllegalArgumentException("invalid character in Base32 string");
            digit = base32Lookup[lookup];
            if(digit == -1)
                throw new IllegalArgumentException("invalid character in Base32 string");
            bytes[offset++] = (byte)(nextByte | digit);
        }

        return bytes;
    }

    public static String encode(String strIn)
    {
        return encode(strIn.getBytes());
    }

    public static String decode(String strIn)
        throws Exception
    {
        byte btOut[] = decodeToBytes(strIn);
        StringBuffer strOut = new StringBuffer();
        for(int idx = 0; idx < btOut.length; idx++)
            strOut.append((char)btOut[idx]);

        return strOut.toString();
    }

    public static void main(String args[])
    {
        try
        {
            System.out.println("original1: dogs20050109");
            System.out.println("original2: 125");
            System.out.println("original3: 9827");
            String str1 = encode("dogs20050109");
            String str2 = encode("125");
            String str3 = encode("9827");
            System.out.println((new StringBuilder("encode String1: ")).append(str1).toString());
            System.out.println((new StringBuilder("encode String2: ")).append(str2).toString());
            System.out.println((new StringBuilder("encode String3: ")).append(str3).toString());
            String str1b = decode(str1);
            String str2b = decode(str2);
            String str3b = decode(str3);
            System.out.println((new StringBuilder("decode String1: ")).append(str1b).toString());
            System.out.println((new StringBuilder("decode String2: ")).append(str2b).toString());
            System.out.println((new StringBuilder("decode String3: ")).append(str3b).toString());
        }
        catch(Exception exc)
        {
            System.out.println("invalid");
        }
    }

    private static final String base32Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final byte base32Lookup[] = {
        26, 27, 28, 29, 30, 31, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 
        5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 
        25, -1, -1, -1, -1, -1, -1, 0, 1, 2, 
        3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 
        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 
        23, 24, 25
    };
    private static final String errorCanonicalLength = "non canonical Base32 string length";
    private static final String errorCanonicalEnd = "non canonical bits at end of Base32 string";
    private static final String errorInvalidChar = "invalid character in Base32 string";

}
