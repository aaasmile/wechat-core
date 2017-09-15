package com.d1m.wechat.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@SuppressWarnings("deprecation")
public final class HttpFileUtil {
	public static void copy(String httpSource, String destFile) throws Exception {
        HttpClient client = new DefaultHttpClient();
	    if(httpSource.startsWith("https://")){
            client = HttpsClient.newHttpsClient();
        }
        HttpGet httpget = new HttpGet(httpSource);
        HttpResponse response = client.execute(httpget);

        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();  

        FileOutputStream fileout = new FileOutputStream(new File(destFile));  
        IOUtils.copy(is, fileout);
        IOUtils.closeQuietly(fileout);
        IOUtils.closeQuietly(is);
	}

    public static void main(String[] args){
        try {
            HttpFileUtil.copy("https://mmbiz.qpic.cn/mmbiz_gif/F6Wu7SN3xDzyrdTSk81QuVSXQ1WZOW4ExLLaVHPk7PVSzfico9g7oqgWHlVEubaMVI2dRia88p15hlRDrgsnbzPA/0","c:\\aaa.gif");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
