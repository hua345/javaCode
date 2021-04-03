package com.github.springbootjunittest.springboot;

import com.github.chenjianhua.common.json.util.JsonUtil;
import com.github.springbootjunittest.SpringbootJunitTestApplication;
import com.github.springbootjunittest.springboot.controller.HelloParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringbootJunitTestApplication.class)
@AutoConfigureMockMvc
class SpringbootControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试controller get方法")
    void testGet() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                get("/helloController?name=fang"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("测试controller Post方法")
    void testPost() throws Exception {
        HelloParam helloParam = new HelloParam();
        helloParam.setName("fang");
        MvcResult mvcResult = mockMvc.perform(
                post("/helloController")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJSONString(helloParam))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }
}
