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
//      哈希256加密
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
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
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
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param string
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String string) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(string);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
           
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
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
    	         
    	        //建立连接
    	        URL url=new URL(url1);
    	        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
    	         
    	        //设置参数
    	        httpConn.setDoOutput(true);     //需要输出
    	        httpConn.setDoInput(true);      //需要输入
    	        httpConn.setUseCaches(false);   //不允许缓存
    	        httpConn.setRequestMethod("POST");      //设置POST方式连接
    	         
    	        //设置请求属性
    	        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    	        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
    	        httpConn.setRequestProperty("Charset", "UTF-8");
    	         
    	        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
    	        httpConn.connect();
    	             
    	        //建立输入流，向指向的URL传入参数
    	        DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
    	        dos.writeBytes(param);
    	        dos.flush();
    	        dos.close();
    	         
    	        //获得响应状态
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
             System.out.println("发送 POST 请求出现异常！"+e);
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
        //发送  POST 请求
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
        //发送GET 请求
//        String sr=Httprequest.sendGet("http://hw.xiezixiansheng.com/mobile.php", "c=Client&a=gupdate&platform=adr&cpid=fz&clientSW=1.0.2&ptype=Product%3A+kenzo%2C+CPU_ABI%3A+arm64-v8a%2C+MODEL%3A+Redmi+Note+3%2C+SDK%3A+22%2C+BRAND%3A+Xiaomi%2C+MANUFACTURER%3A+Xiaomi%2C+USER%3A+builder&sys=ADR5.1.1");
//        System.out.println(sr);
    }
}
