package com.github.spring.boot.idleaf.model;

import com.github.spring.boot.idleaf.service.idleaf.IdLeafRedisService;
import com.github.spring.boot.idleaf.service.idleaf.IdLeafService;
import com.github.spring.boot.idleaf.utils.holder.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author chenjianhua
 * @date 2020/9/24
 */
@Slf4j
public class LeafId implements Configurable, IdentifierGenerator {
    private IdLeafService idLeafService;

    private String bizTag;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.bizTag = params.getProperty("bizTag");
        log.info("bizTag:{}", bizTag);

    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        if (null == idLeafService) {
            synchronized (LeafId.class) {
                if (null == idLeafService) {
                    idLeafService = SpringContextHolder.getBean(IdLeafRedisService.class);
                    log.info("初始化LeafId");
                }
            }
        }
        return idLeafService.getIdByBizTag(bizTag);
    }
}