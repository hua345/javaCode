package com.github.spring.boot.idleaf.service.idleaf;

import com.github.spring.boot.idleaf.utils.SnowFlakeUtil;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.Serializable;

/**
 * @author chenjianhua
 * @date 2020/9/24
 */
public class LeafId extends IdentityGenerator implements ApplicationRunner {
    private IdLeafService idLeafService;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        Object id = SnowFlakeUtil.getNextId();
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(session, object);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        idLeafService = new IdLeafRedisService();
    }
}