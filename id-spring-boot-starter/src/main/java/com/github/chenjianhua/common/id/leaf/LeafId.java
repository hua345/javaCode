package com.github.chenjianhua.common.id.leaf;

import com.github.chenjianhua.common.id.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author chenjianhua
 * @date 2020/9/24
 */
@Slf4j
public class LeafId implements IdentifierGenerator {
    private volatile static IdLeafService idLeafService;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        if (null == idLeafService) {
            synchronized (LeafId.class) {
                if (null == idLeafService) {
                    idLeafService = ApplicationContextUtil.getBean(IdLeafRedisService.class);
                    log.info("初始化LeafId");
                }
            }
        }

        return idLeafService.getIdByBizTag(object.getClass().getSimpleName());
    }
}