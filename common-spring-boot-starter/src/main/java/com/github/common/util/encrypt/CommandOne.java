package com.github.common.util.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2020/12/7
 */
@Slf4j
@Component
public class CommandOne implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Running with {}", String.join("|", args));
    }
}