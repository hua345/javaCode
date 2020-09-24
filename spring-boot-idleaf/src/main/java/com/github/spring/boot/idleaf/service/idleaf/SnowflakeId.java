package com.github.spring.boot.idleaf.service.idleaf;

import com.github.spring.boot.idleaf.utils.SnowFlakeUtil;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * @author chenjianhua
 * @date 2020/9/24
 */
public class SnowflakeId extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        Object id = SnowFlakeUtil.getNextId();
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(session, object);
    }
}