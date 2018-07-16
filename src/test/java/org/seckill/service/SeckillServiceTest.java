package org.seckill.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合,junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 注入Service实现类依赖
	@Resource
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() throws Exception {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}
	
	@Test
	public void testGetById() throws Exception {
		long id = 1000;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}", seckill);
	}
	
	@Test
	public void testSeckillLogic() throws Exception {
		long id = 1001;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		// 判断秒杀是否开启
		if(exposer.isExposed()) {
			logger.info("exposer={}", exposer);
			long phone = 13012345670L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
				logger.info("result={}", execution);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage(), e);
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage(), e);
			}
		}else {
			// 警告:秒杀未开启
			logger.warn("exposer={}", exposer);
		}
		
	}
	
}
