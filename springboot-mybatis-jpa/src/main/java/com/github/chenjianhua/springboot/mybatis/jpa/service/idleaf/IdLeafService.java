/**
 * 
 */
package com.github.chenjianhua.springboot.mybatis.jpa.service.idleaf;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
public interface IdLeafService {

	/**
	 * 根据业务标签获取相应的id，这样可以动态的创建相应的服务，而不需要停服，
	 * 这个接口相当于网关的作用
	 * @param bizTag
	 * @return
	 */
	public Long getIdByBizTag(String bizTag);
}
