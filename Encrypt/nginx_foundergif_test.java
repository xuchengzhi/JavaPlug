package Encrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class nginx_foundergif_test {
	static String[] idparams = {"三生有幸","等你记起我","不能说的秘密"};
    static List<String> textparams = new ArrayList<String>();
	String urlparam;
    public nginx_foundergif_test(String filename)
    {
        if(textparams.isEmpty())
            ReadFileByLine(filename);
        int max=10000;
        int min=1;
        int ran2 = (int) (Math.random()*(max-min)+min);

        int idindex =ran2%(idparams.length - 1);
        int textindex = ran2%(textparams.size() - 1);
        String toencode = "#"+idparams[idindex]+"#"+textparams.get(textindex)+"#FZXDZ";
        //System.out.println(toencode);
        String md5params = md5(toencode);
		//String md5params = string2MD5(toencode);
//        Map<String,String> urlmap = new HashMap<String,String>();
//        urlmap["id"] = idparams[idindex];
//        urlmap["text"] = textparams[textindex];
//        urlmap["code"] = md5paramsstr.toUpperCase();

        try {
            String urlid = URLEncoder.encode(idparams[idindex], "UTF8");  //输出%C4%E3%BA%C3
            String urltext = URLEncoder.encode(textparams.get(textindex), "UTF8");
            String urlcode = URLEncoder.encode(md5params.toUpperCase(), "UTF8");
			String urlString = urlid + "&text="+urltext +"&code=" + urlcode;
			//System.out.println(urlString);
			urlparam = urlString;
        }catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
			e.printStackTrace();
        }

    }
	
	public String GetUrlParam()
	{
		System.out.println(urlparam);
		return urlparam;
	}
	
    /**
     * 按行读取文件
     */
    public static void ReadFileByLine(String filename) {
        File file = new File(filename);
        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = new FileInputStream(file);
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                textparams.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedReader)
                    bufferedReader.close();
                if (null != reader)
                    reader.close();
                if (null != is)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes("UTF-8"));
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
			e.printStackTrace();
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
	
	public static void main(String args[])
    {
        String tst = new nginx_foundergif_test("C:/Users/FD_XU/Desktop/testgif_text.txt").GetUrlParam();
    }
}
