package Encrypt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Httprequest {
	public static String sha256_HMAC(String msg,String key) {
//      ��ϣ256����
           String strDes = "";
           String res = "";
           
           String type = "1";
           
           Charset charset = Charset.forName("UTF-8");
           try {
               Mac sha256 = Mac.getInstance("HmacSHA256");
               SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(charset), "HmacSHA256");
               sha256.init(secret_key);
               byte[] bytes = sha256.doFinal(msg.getBytes());
               System.out.println(bytes);
//             strDes = bytes2Hex(bytes);
               strDes = bytesToHexString(bytes);
               System.out.println(strDes);
               res = new sun.misc.BASE64Encoder().encode(strDes.getBytes("utf-8"));
           } catch (Exception e) {
               e.printStackTrace();
           }
           
//           String token = res;
           System.out.println(res);
           return res;
       }
    public static String bytesToHexString(byte[] src){   
           StringBuilder stringBuilder = new StringBuilder("");   
           if (src == null || src.length <= 0) {   
               return null;   
           }   
           for (int i = 0; i < src.length; i++) {   
               int v = src[i] & 0xFF;   
               String hv = Integer.toHexString(v);   
               if (hv.length() < 2) {   
                   stringBuilder.append(0);   
               }   
               stringBuilder.append(hv);   
           }   
           return stringBuilder.toString();   
   }   
    static String xorEncode(byte[] data, String key) {
           Charset charset = Charset.forName("UTF-8");
           byte[] keyBytes = key.getBytes(charset);
           byte[] encryptBytes = new byte[data.length];
           for (int i = 0; i < data.length; i++) {
               encryptBytes[i] = (byte) (data[i] ^ keyBytes[i % keyBytes.length]);
           }
           return new String(encryptBytes, charset);
       }
    public static String xorEncode(String src, String key) {
   	 	System.out.println(xorEncode(src.getBytes(Charset.forName("UTF-8")), key));
           return xorEncode(src.getBytes(Charset.forName("UTF-8")), key);
       }
	public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ��ָ�� URL ����POST����������
     * 
     * @param url
     *            ��������� URL
     * @param string
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ������Զ����Դ����Ӧ���
     */
    public static String sendPost(String url, String string) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(string);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
           
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
  
    
    public static String post2(String url1, String string) {
    
    	 try {  
    	         
    	        String param= string;//"sts_info=" + URLEncoder.encode(string,"UTF-8");
    	         
    	        //��������
    	        URL url=new URL(url1);
    	        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
    	         
    	        //���ò���
    	        httpConn.setDoOutput(true);     //��Ҫ���
    	        httpConn.setDoInput(true);      //��Ҫ����
    	        httpConn.setUseCaches(false);   //��������
    	        httpConn.setRequestMethod("POST");      //����POST��ʽ����
    	         
    	        //������������
    	        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    	        httpConn.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����
    	        httpConn.setRequestProperty("Charset", "UTF-8");
    	         
    	        //����,Ҳ���Բ�������connect��ʹ�������httpConn.getOutputStream()���Զ�connect
    	        httpConn.connect();
    	             
    	        //��������������ָ���URL�������
    	        DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
    	        dos.writeBytes(param);
    	        dos.flush();
    	        dos.close();
    	         
    	        //�����Ӧ״̬
    	        int resultCode=httpConn.getResponseCode();
    	        if(HttpURLConnection.HTTP_OK==resultCode){
    	            StringBuffer sb=new StringBuffer();
    	            String readLine=new String();
    	            BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
    	            while((readLine=responseReader.readLine())!=null){
    	                sb.append(readLine).append("\n");
    	            }
    	            responseReader.close();
//    	            System.out.println(sb.toString());
    	            return sb.toString();
    	        }  
    	 } catch (Exception e) {
             System.out.println("���� POST ��������쳣��"+e);
             e.printStackTrace();
         }
    	
 

    	return "";
    }
    
    public static void tojson(String msg){
    	
    } 
    
    public static void run(String types,String chanekey,String key){
    	String sys = "adr5.0";
    	String type = types;
        String date = "348394834390493"; //Calendar.getInstance().getTimeInMillis()+"";
        String temp = date + types + sys;
    	String token = sha256_HMAC(temp,chanekey);
    	JSONObject datas = new JSONObject();
        try {
        	datas.put("type", types);
        	datas.put("t", date);
        	datas.put("sys", sys);
        	datas.put("token", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(datas);
		JSONObject par = new JSONObject();
		try {
			String torms = xorEncode(datas.toString(),key);
			par.put("sts_info", torms );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		String res = Httprequest.sendPost("http://fontsdkdev.fzshouji.com/mobile.php/Sdk/Sdk_getAuthToken",par);
//    	System.out.println(res);
    	
    }
    public static void main(String[] args) {
        //����  POST ����
//    	String sys = "adr5.0";
//    	String date = Calendar.getInstance().getTimeInMillis()+"";
//    	run("1","qqprivatekey","sJzz$%^sdk98Pnviw!*qIwe4");
    	
//    	sha256_HMAC("1","qqprivatekey");
//    	json = new JSONObject();
//    	json.put("id","1");
//    	System.out.println(date);
//        String s=Httprequest.sendPost("http://hwdev.xiezixiansheng.com/mobile.php?c=Users&a=phone_check", "login_name=13888888872");
//        sha256_HMAC("123456","qqprivatekey");
//        System.out.println(s);
        //����GET ����
//        String sr=Httprequest.sendGet("http://hw.xiezixiansheng.com/mobile.php", "c=Client&a=gupdate&platform=adr&cpid=fz&clientSW=1.0.2&ptype=Product%3A+kenzo%2C+CPU_ABI%3A+arm64-v8a%2C+MODEL%3A+Redmi+Note+3%2C+SDK%3A+22%2C+BRAND%3A+Xiaomi%2C+MANUFACTURER%3A+Xiaomi%2C+USER%3A+builder&sys=ADR5.1.1");
//        System.out.println(sr);
    }
}
