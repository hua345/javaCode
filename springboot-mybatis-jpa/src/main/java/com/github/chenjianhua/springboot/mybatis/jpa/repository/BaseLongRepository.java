package com.github.chenjianhua.springboot.mybatis.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@NoRepositoryBean
public interface BaseLongRepository<M> extends JpaRepository<M, Long>, JpaSpecificationExecutor<M> {

}
