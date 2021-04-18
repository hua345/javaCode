package com.github.springbootjunittest.springboot.http;

import com.github.chenjianhua.common.json.util.JsonUtil;
import com.github.springbootjunittest.springboot.controller.HelloParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenjianhua
 * @date 2021/4/16
 */
@Slf4j
public class RestTemplateUtil {
    public static ResponseEntity<String> getRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, String.class);
    }

    public static ResponseEntity<String> postJson(String url, String jsonStr) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonStr, headers);
        return restTemplate.postForEntity(url, request, String.class);
    }

    public static void main(String[] args){
        String testUrl = "http://127.0.0.1:8080/helloController?name=fang";
        log.info(JsonUtil.toJsonString(RestTemplateUtil.getRequest(testUrl)));

        testUrl = "http://127.0.0.1:8080/helloController";
        HelloParam helloParam = new HelloParam();
        helloParam.setName("fangfang");
        log.info(JsonUtil.toJsonString(RestTemplateUtil.postJson(testUrl, JsonUtil.toJsonString(helloParam))));
    }
}
