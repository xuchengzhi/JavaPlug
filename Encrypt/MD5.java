package Encrypt;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * MD5 绠楁硶
 */
public class MD5 {

    // 鍏ㄥ眬鏁扮粍
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5() {
    }

    // 杩斿洖褰㈠紡涓烘暟瀛楄窡瀛楃涓�
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 杞崲瀛楄妭鏁扮粍涓�16杩涘埗瀛椾覆
    private static String byteToString(byte[] bByte) {
        StringBuilder sBuffer = new StringBuilder();
        for (byte aBByte : bByte) {
            sBuffer.append(byteToArrayString(aBByte));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 璇ュ嚱鏁拌繑鍥炲�间负瀛樻斁鍝堝笇鍊肩粨鏋滅殑byte鏁扮粍
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
}