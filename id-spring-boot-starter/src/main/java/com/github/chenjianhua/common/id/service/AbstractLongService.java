package com.github.chenjianhua.common.id.service;


import com.github.chenjianhua.common.id.model.AbstractLongModel;
import com.github.chenjianhua.common.id.repository.BaseLongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 基础service服务类
 *
 * @param <M> model类型
 * @author haiping.huang
 */
@Slf4j
public abstract class AbstractLongService<M extends AbstractLongModel> {

    protected BaseLongRepository<M> baseLongRepository;

    public AbstractLongService(BaseLongRepository<M> baseLongRepository) {
        this.baseLongRepository = baseLongRepository;
    }

    public M save(M m) {
        return baseLongRepository.saveAndFlush(m);
    }

    /**
     * 批量保存
     */
    public List<M> saveAll(List<M> list) {
        baseLongRepository.saveAll(list);
        baseLongRepository.flush();
        return list;
    }

    /**
     * 批量物理删除
     */
    public void deleteByIds(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                baseLongRepository.deleteById(id);
            });
        }
        baseLongRepository.flush();
    }

    /**
     * 物理删除
     */
    public void deleteById(Long id) {
        baseLongRepository.deleteById(id);
        baseLongRepository.flush();
    }

    /**
     * 逻辑删除, deleted设置为true
     */
    public M logicDelete(Long id) {
        Optional<M> optionalData = findById(id);
        if (!optionalData.isPresent()) {
            throw new RuntimeException("实体ID" + id + " 不存在");
        }
        optionalData.get().setDeleted(true);
        return save(optionalData.get());
    }

    public Optional<M> findById(Long id) {
        return baseLongRepository.findById(id);
    }

    public List<M> findByIds(Collection<Long> ids) {
        return baseLongRepository.findAllById(ids);
    }
}
