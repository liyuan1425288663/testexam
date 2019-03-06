package com.ley.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @create 2019-03-01  20:48
 */
@EnableZuulProxy
@SpringCloudApplication
public class LyGetWay {
    public static void main(String[] args) {

        SpringApplication.run(LyGetWay.class);
    }
}
