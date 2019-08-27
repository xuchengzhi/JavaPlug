package com.App.Check;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.UseFeature;
 
 



public class CheckRun {
	/**
	 * 读取ipa
	 */
	public static Map<String,Object> readApk(String apkUrl){
		Map<String,Object> resMap=new HashMap<String,Object>();
		try (ApkFile apkFile = new ApkFile(new File(apkUrl))) {
	            ApkMeta apkMeta = apkFile.getApkMeta();
	           
	            resMap.put("Name", apkMeta.getName());
	            resMap.put("Ico",  apkMeta.getIcon());
	            resMap.put("PackageName", apkMeta.getPackageName());
	            resMap.put("versionCode", apkMeta.getVersionCode());
	            resMap.put("versionName", apkMeta.getVersionName()); 
//	            String name = apkMeta.getIcon();
//	            String Home = System.getProperty("user.dir");
//	            FileOutputStream fos = new FileOutputStream(new File(Home+"/img/"+apkMeta.getName()+".png"));
//	            int chunk = 0;
//	            byte[] data = new byte[1024];
//	            fos.close();
		                
		                  
		            
		        
	            for (UseFeature feature : apkMeta.getUsesFeatures()) {
	                System.out.println(feature.getName());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return resMap;
	}
	
	public static Map<String,Object> readIPA(String ipaURL){
	    Map<String,Object> map = new HashMap<String,Object>();
	    try {
	        File file = new File(ipaURL);
	        InputStream is = new FileInputStream(file);
	        InputStream is2 = new FileInputStream(file);
	        ZipInputStream zipIns = new ZipInputStream(is);
	        ZipInputStream zipIns2 = new ZipInputStream(is2);
	        ZipEntry ze;
	        ZipEntry ze2;
	        InputStream infoIs = null;
	        NSDictionary rootDict = null;
	        String icon = null;
	        while ((ze = zipIns.getNextEntry()) != null) {
	            if (!ze.isDirectory()) {
	                String name = ze.getName();
	                if (null != name &&
	                 name.toLowerCase().contains(".app/info.plist")) {
	                    ByteArrayOutputStream _copy = new 
	                                ByteArrayOutputStream();
	                    int chunk = 0;
	                    byte[] data = new byte[1024];
	                    while(-1!=(chunk=zipIns.read(data))){
	                        _copy.write(data, 0, chunk);
	                    }
	                    infoIs = new ByteArrayInputStream(_copy.toByteArray());
	                    rootDict = (NSDictionary) PropertyListParser.parse(infoIs);
	 
	                    //我们可以根据info.plist结构获取任意我们需要的东西
	                    //比如下面我获取图标名称，图标的目录结构请下面图片
	                    //获取图标名称
	                    NSDictionary iconDict = (NSDictionary) rootDict.get("CFBundleIcons");
	 
	                    while (null != iconDict) {
	                        if(iconDict.containsKey("CFBundlePrimaryIcon")){
	                        NSDictionary CFBundlePrimaryIcon = (NSDictionary)iconDict.get("CFBundlePrimaryIcon"); 
	                            if(CFBundlePrimaryIcon.containsKey("CFBundleIconFiles")){
	                            NSArray CFBundleIconFiles =(NSArray)CFBundlePrimaryIcon.get("CFBundleIconFiles"); 
	                            icon = CFBundleIconFiles.getArray()[0].toString();
	                                if(icon.contains(".png")){
	                                    icon = icon.replace(".png", "");
	                                }
//	                                System.out.println("获取icon名称:" + icon);
	                                break;
	                            }
	                        }
	                    }
	                    break;
	                }
	            }
	        }
	 
	        ////////////////////////////////////////////////////////////////
	        //如果想要查看有哪些key ，可以把下面注释放开
	      for (String keyName : rootDict.allKeys()) {
	          System.out.println(keyName + ":" + rootDict.get(keyName).toString());
	      }
	 
	 
	        // 应用包名
	        NSString parameters = (NSString) rootDict.get("CFBundleIdentifier");
	        map.put("package", parameters.toString());
	        parameters = (NSString) rootDict.objectForKey("CFBundleDisplayName");
	        
	      //根据图标名称下载图标文件到指定位置
	        while ((ze2 = zipIns2.getNextEntry()) != null) {
	            if (!ze2.isDirectory()) {
	                String name = ze2.getName();
//	                System.out.println(name);
	                if(name.contains(icon.trim())){
//	                    System.out.println(11111);
	                	String Home = System.getProperty("user.dir");
	                    FileOutputStream fos = new FileOutputStream(new File(Home+"/img/"+parameters.toString()+".png"));
	                       int chunk = 0;
	                       byte[] data = new byte[1024];
	                       while(-1!=(chunk=zipIns2.read(data))){
	                           fos.write(data, 0, chunk);
	                       }
	                       fos.close();
	                    break;
	                }
	            }
	        }
	        
	        map.put("packageName", parameters.toString());
	        // 应用版本名
	        parameters = (NSString) rootDict.objectForKey("CFBundleShortVersionString");
	        map.put("versionName", parameters.toString());
	        //应用版本号
	        parameters = (NSString) rootDict.get("CFBundleVersion");
	        map.put("versionCode", parameters.toString());
	        
	        
	 
	        /////////////////////////////////////////////////
	        infoIs.close();
	        is.close();
	        zipIns.close();
	 
	    } catch (Exception e) {
	        map.put("code", "fail");
	        map.put("error","读取ipa文件失败");
	    }
	    return map;
	}
	public static void main(String[] args) {
//		String ipaUrl = "E:/go/data/src/github.com/xuchengzhi/apimonitor/App/IOS/6f2e18e1-242d-4ad6-b691-94dcac6cab7d.ipa";
	
		String ipaUrl = args[0];
		Map<String,Object> mapIpa = readIPA(ipaUrl);
//		
//		String apkurl = "E:\\go\\data\\src\\github.com\\xuchengzhi\\apimonitor\\App\\android\\98d5d343-e5f6-4fb8-abd5-c1a22f4f8f70.apk";
//		String apkurl = args[0];
//		Map<String,Object> mapIpa = readApk(apkurl);
		
		System.out.println(mapIpa);
//		for (String key : mapIpa.keySet()) {
//			System.out.println("{"+key + ":" + mapIpa.get(key)+"}");
//		}
//		String filePath = "D:\\apk\\farmClient.apk";
//		 
//        try (ApkFile apkFile = new ApkFile(new File(filePath))) {
//            ApkMeta apkMeta = apkFile.getApkMeta();
//            System.out.println(apkMeta.getName());
//            System.out.println(apkMeta.getPackageName());
//            System.out.println(apkMeta.getVersionCode());
//            System.out.println(apkMeta.getVersionName());
//            for (UseFeature feature : apkMeta.getUsesFeatures()) {
//                System.out.println(feature.getName());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }                       
	}

}
