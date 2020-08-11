package com.dabin.utils;

import java.math.BigInteger;

/**
 * 数据处理的工具类
 *
 * @author Dabin
 */
public class DataUtil {

    /**
     * byte数组转为十六进制字符串
     *
     * @param src b
     * @return r
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 十六进制字符串转byte数组
     *
     * @param hexString 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 字符转字节
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 将指定byte数组以16进制的形式打印到控制台
     *
     * @param b b
     */
    public static void printHexString(byte[] b) {
        for (byte aB : b) {
            String hex = Integer.toHexString(aB & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase());
        }
    }

    /**
     * 把字符串改为utf-8的字节数组
     *
     * @param str s
     * @return array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 把字符串改为gb2312的字节数组
     *
     * @param str s
     * @return array of NULL if error was found
     */
    public static byte[] getGB2312Bytes(String str) {
        try {
            return str.getBytes("gb2312");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 解析String类型的数据，使得它仅显示出小数点后2位数值
     *
     * @param accu s
     */
    public static String parseFloat(String accu) {
        return Math.round(Float.parseFloat(accu) * 100) / 100f + "";
    }

    /**
     * 解析float类型的数据，使得它仅显示出小数点后n位数值
     *
     * @param f f
     * @param n n
     * @return 小数点n位浮点
     */
    public static float parseFloat2NumFloat(float f, int n) {
        return (float) (Math.round(f * Math.pow(10, n)) / Math.pow(10, n));
    }

    /**
     * 计算CRC16校验码
     *
     * @param bytes 字节数组
     * @return {@link String} 校验码
     */
    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
//        int hex1 = ( CRC & 0xFF000000)>>24;
//        int hex2 = ( CRC & 0x000000FF)<<24;
        // 四位16进制的结果调换
        int hex1 = (CRC & 0xFF00) >> 8;
        int hex2 = (CRC & 0x00FF) << 8;
//        return Integer.toHexString(hex1 + hex2);
        return String.format("%04x", hex1 + hex2);
    }

    /**
     * 将16进制单精度浮点型转换为10进制浮点型
     *
     * @return float
     */
    public static float parseHex2Float(String hexStr) {
        BigInteger bigInteger = new BigInteger(hexStr, 16);
        return Float.intBitsToFloat(bigInteger.intValue());
    }

    /**
     * 将十进制浮点型转换为十六进制浮点型
     *
     * @return String
     */
    public static String parseFloat2Hex(float data) {
        return Integer.toHexString(Float.floatToIntBits(data));
    }

    /**
     * //     * Hex字符串转byte
     * //     *
     * //     * @param inHex h
     * //     * @return byte
     * //
     */
    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * 1字节转2个Hex字符
     *
     * @param inByte ib
     * @return s
     */
    public static String Byte2Hex(Byte inByte) {
        return String.format("%02x", inByte).toUpperCase();
    }

    /**
     * 十进制int转换成二进制
     *
     * @param decimalSource d
     * @return String
     */
    public static String decimalToBinary(int decimalSource) {
        BigInteger bi = new BigInteger(String.valueOf(decimalSource));    //转换成BigInteger类型
        return bi.toString(2);    //参数2指定的是转化成X进制，默认10进制
    }

    /**
     * 十进制long转换成二进制
     *
     * @param decimalSource d
     * @return String
     */
    public static String ldecimalToBinary(long decimalSource) {
        BigInteger bi = new BigInteger(String.valueOf(decimalSource));    //转换成BigInteger类型
        return bi.toString(2);    //参数2指定的是转化成X进制，默认10进制
    }

    /**
     * 二进制转换成十进制
     *
     * @param binarySource b
     * @return int
     */
    public static int binaryToDecimal(String binarySource) {
        BigInteger bi = new BigInteger(binarySource, 2);    //转换为BigInteger类型
        return Integer.parseInt(bi.toString());        //转换成十进制
    }

}


