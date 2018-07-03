package com.abin.lee.spike.patch.test;

import com.abin.lee.spike.common.HttpClientUtil;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2018/1/15 11:36.
 * spring-boot-start2
 * com.abin.lee.spring.boot.mybatis.test
 */
public class FlashByPatchTest {

        private static final String httpURL = "http://localhost:8080/spikeRequest/spikePatch";
//    private static final String httpURL = "http://localhost:8099/spikeRequest/spikePatch";

    @Test
    public void testFlashByLua() {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("userName", "second"));
            int id = (int)(Math.random()*10000000L);
            nvps.add(new BasicNameValuePair("keyName", "" + id));
            HttpPatch httpPost = new HttpPatch(httpURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
