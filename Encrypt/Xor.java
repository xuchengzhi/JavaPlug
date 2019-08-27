package Encrypt;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.net.URLEncoder;

public class Xor {
    
     public static String sha256_HMAC(String content,String key) {
//       哈希256加密
            String strDes = "";
            String res = "";
            
            Charset charset = Charset.forName("UTF-8");
//            System.out.println(content);
//            System.out.println(key);
            try {
                Mac sha256 = Mac.getInstance("HmacSHA256");
                SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(charset), "HmacSHA256");
                sha256.init(secret_key);
                byte[] bytes = sha256.doFinal(content.getBytes());
//                System.out.println(bytes);
//              strDes = bytes2Hex(bytes);
                strDes = bytesToHexString(bytes);
//                System.out.println("256="+strDes);
                res = new sun.misc.BASE64Encoder().encode(strDes.getBytes("utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
//            System.out.println(res.replace("\n","").replace("\r",""));
            return res.replace("\n","").replace("\r","");
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
//    	 	System.out.println(xorEncode(src.getBytes(Charset.forName("UTF-8")), key));
            return xorEncode(src.getBytes(Charset.forName("UTF-8")), key);
        }
     public static String run(String types,String chanekey,String key){
     	String sys = "adr5.0";
     	String type = types;
        String date = Calendar.getInstance().getTimeInMillis()+"";
        String temp = date + types + sys;

     	String token = sha256_HMAC(temp,chanekey);
     	
//     	JSONObject datas = new JSONObject();
     	Map<String, String>datas=new HashMap<String, String>();
         try {
        	 datas.put("type", types);
        	 datas.put("t", date);
        	 datas.put("sys", sys);
        	 datas.put("token", token);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
// 		System.out.println(datas);
 		Map<String, String> par = new HashMap<String, String>();
 		String torms = "";
 		String m = GsonUtil.GsonString(datas);
 		
 	 
 		
//		System.out.println(m);
		torms = xorEncode(m,key);
//		System.out.println(torms);
//		System.out.println(torms.length());
//		par.put("sts_info", torms );
//		par.put("key", key );
 		
//		String pars = GsonUtil.GsonString(par);
		String pams = "";
//		System.out.println(torms);
		try {
//			String pams = "sts_info=" + URLEncoder.encode(torms,"UTF-8");
//			pams = "sts_info=" + URLEncoder.encode(torms,"UTF-8");
			pams = URLEncoder.encode(torms,"UTF-8");
//			System.out.println("string pars ="+pams);
//	 		String res1 = Httprequest.sendPost("http://192.168.248.126/dexor",pams+"&key="+key);
//	 		String res2 = Httprequest.post2("http://fontsdkdemo.fzshouji.com/mobile.php/Sdk/Sdk_getAuthToken",pams);
//	 		String res=Httprequest.sendPost("http://hwdev.xiezixiansheng.com/mobile.php?c=Users&a=phone_check", "login_name=13888888872");
//	     	System.out.println("response1=" + res1);
//	     	System.out.println("response2=" + res2);
	     	return pams;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
     	
		return pams;
     	
     }
     
     
     
     
     public static String RandomStr(){
    	 String res = "" ;
    	 final long l = System.currentTimeMillis();
    	 final int i = (int)( l % 1000 );
    	 res = i+"3"+"";
//    	 System.out.println("res"+res);
    	
    	 
    	 return res;
     }
     
     public static String Md5Str(String msg) {
    	 String res = ""; 
    	 try {
             // 生成一个MD5加密计算摘要
             MessageDigest md = MessageDigest.getInstance("MD5");
             // 计算md5函数
             md.update(msg.getBytes());
             // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
             // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
             String md5=new BigInteger(1, md.digest()).toString(16);
             //BigInteger会把0省略掉，需补全至32位
//             System.out.println(md5);
             res = md5;
             return md5;
         } catch (Exception e) {
             throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
         }
     }
     
     public static String NodeStr(String msg,String key){
    	 String res = "";
    	 String flag = "h5wps";
    	 String version = "Node1.0";
         String times = Calendar.getInstance().getTimeInMillis()+""+RandomStr();
         String temp = times + version + msg +flag;
//         System.out.println(temp);
         String tps = MD5.GetMD5Code(temp);
         
         String token = MD5.GetMD5Code(tps);
//         System.out.println(tps);
//         System.out.println(token);
         Map<String, String>datas=new HashMap<String, String>();
         try {
        	 datas.put("v", version);
        	 datas.put("t", times);
        	 datas.put("f", flag);
        	 datas.put("sign", token);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		String JsonStr =  GsonUtil.GsonString(datas);
// 		System.out.println(JsonStr);
 		String res1 = "";
 		res = xorEncode(JsonStr,key);
// 		try {
//			res = new sun.misc.BASE64Encoder().encode(res1.getBytes("utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
// 		String par;
//		try {
//			par = "info=" +URLEncoder.encode(res,"UTF-8");
////			System.out.println(par);
////			String res1 = Httprequest.sendPost("http://39.96.197.60:3000/stsmx",par);
////	 		System.out.println(res1);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
 		
 		return res;
 		
     }
     
     public static void main(String[] args){
        String ceshimsg = "123456";
        String key  = "sJzz$%^sdk98Pnviw!*qIwe4";
        String res;
//        RandomStr();
        Charset charset = Charset.forName("UTF-8");
//      sha256_HMAC(ceshimsg,"qqprivatekey".getBytes(charset));
      System.out.println(xorEncode(ceshimsg,key));
//        System.out.println(args.length);
//        String canshu3 = "";
//        String func = "";
//        run("1","qqprivatekey","sJzz$%^sdk98Pnviw!*qIwe4");
//        NodeStr("fbn","1111");
//        String func = "";
//        
//        try{
//            func = args[0];
//        }catch (Exception e){
//            System.out.println("第一个参数为方法，第二个参数为加密内容，第三个参数为哈希key，第四个参数为异或key\n方法包含：\nsha256_HMAC 哈希256\nxorEncode 异或加密\nall 先哈希再异或");
//        }
//        if (func.equals("sha256_HMAC")){
//            System.out.println("哈希256加密");
//            try{
//                String canshu1 = args[1];
//                String canshu2 = args[2];
//                sha256_HMAC(canshu1,canshu2);
//            }catch(Exception e){
//                System.out.println("哈希256加密需要传加密内容和key");
//            }
//            
//            
//        }else if (func.equals("xorEncode")){
//            try{
//                String canshu1 = args[1];
//                String canshu2 = args[2];
//                xorEncode(canshu1,canshu2);
//            }catch(Exception e){
//                System.out.println("异或加密需要传加密内容和key");
//            }
//            
//        }else if (func.equals( "run")){
//        	String temp; 
//        	String canshu1 = args[1];
//            String canshu2 = args[2];
//            String canshu3 = args[3];
//            run(canshu1,canshu2,canshu3);
//        }
    
    
}
    
}

