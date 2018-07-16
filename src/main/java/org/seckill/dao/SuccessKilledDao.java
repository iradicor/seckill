package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {

	/**
	 * 插入购买明细,可过滤重复(seckillId 和 userPhone 是联合组件,不会插入重复记录)
	 * @param seckillId
	 * @param userPhone
	 * @return 表示插入的记录行数,0 表示没有插入数据,1 表示插入1条记录,n 表示插入n条记录
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
	/**
	 * 根据id查询SuccessKilled并携带秒杀产品对象实体
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
}
