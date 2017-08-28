package com.d1m.wechat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public final class HttpFileUtil {
	public static void copy(String httpSource, String destFile) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(httpSource);
        HttpResponse response = client.execute(httpget);  

        HttpEntity entity = response.getEntity();  
        InputStream is = entity.getContent();  

        FileOutputStream fileout = new FileOutputStream(new File(destFile));  
        IOUtils.copy(is, fileout);
        IOUtils.closeQuietly(fileout);
        IOUtils.closeQuietly(is);
	}
}
