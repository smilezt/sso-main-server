package com.xiaozhao.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    public static String sendReq(String reqUrl,String content) throws Exception {
        URL url=new URL(reqUrl);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        byte[] b=content.getBytes("UTF-8");
        conn.getOutputStream().write(b);
        conn.connect();
        InputStream in =conn.getInputStream();
        return Stream2String(in);
    }

    public static String Stream2String(InputStream in){
        if(in!=null) {
            try {
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuffer tStringBuffer = new StringBuffer();
                String sTempOneLine = new String("");
                while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
