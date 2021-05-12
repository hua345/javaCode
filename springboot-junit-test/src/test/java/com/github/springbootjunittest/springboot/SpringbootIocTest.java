package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.ioc.TestServiceA;
import com.github.springbootjunittest.springboot.ioc.TestServiceB;
import com.github.springbootjunittest.springboot.ioc.TestServiceC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/5/10
 */
@SpringBootTest
public class SpringbootIocTest {
    @Autowired
    private TestServiceA testServiceA;

    @Autowired
    private TestServiceB testServiceB;

    @Autowired
    private TestServiceC testServiceC;

    @Test
    public void IocTest() {
        Assertions.assertNotNull(testServiceA);
        Assertions.assertNotNull(testServiceA.getTestServiceB());
        Assertions.assertNotNull(testServiceB);
        Assertions.assertNotNull(testServiceB.getTestServiceA());
        Assertions.assertNotNull(testServiceC);
        Assertions.assertNotNull(testServiceC.getTestServiceA());
        Assertions.assertNotNull(testServiceC.getTestServiceB());
    }

}
