package com.github.chenjianhua.consumer.service;

import com.github.chenjianhua.common.json.util.JsonUtil;
import com.github.common.resp.ResponseVO;
import com.github.common.util.ResponseUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenjianhua
 * @date 2021/4/16
 */
@Slf4j
@Component
public class RestTemplateService {
    @Autowired
    private RestTemplate restTemplate;

    private RestTemplateService() {

    }

    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * RestTemplate get请求工具类
     */
    public static ResponseEntity<String> request(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, String.class);
    }

    /**
     * RestTemplate post请求类
     */
    public static ResponseEntity<String> postJson(String url, String jsonStr) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> request = new HttpEntity<>(jsonStr, headers);
        return restTemplate.postForEntity(url, request, String.class);
    }

    /**
     * 通过服务名进行post请求
     */
    @HystrixCommand(fallbackMethod = "getErrorInfo")
    public ResponseEntity<String> postJsonByServerName(String serverName, String uri, String jsonStr) {
        if (!StringUtils.hasText(serverName)) {
            throw new RuntimeException("导出服务名没有配置");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(serverName).append("/").append(uri);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> request = new HttpEntity<>(jsonStr, headers);
        return restTemplate.postForEntity(sb.toString(), request, String.class);
    }

    public ResponseEntity<String> getErrorInfo(String serverName, String uri, String jsonStr) {
        log.info("生产者服务:{} {}请求失败", serverName, uri);
        return ResponseEntity.ok("生产者服务请求失败!");
    }
}
