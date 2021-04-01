package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.SpringbootJunitTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringbootJunitTestApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
class SpringbootServletTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试helloRegistrationBeanServlet方法")
    void test() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                get("/helloRegistrationBeanServlet?name=fang"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        log.info("helloRegistrationBeanServlet结果:{}", mvcResult.getResponse().getContentAsString());
    }
    @Test
    @DisplayName("测试helloContextServlet方法")
    void testHelloContextServlet() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                get("/helloContextServlet?name=fang"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        log.info("helloContextServlet结果:{}", mvcResult.getResponse().getContentAsString());
    }

}
