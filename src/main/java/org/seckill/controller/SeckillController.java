package org.seckill.controller;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/seckill") //url:/模块/资源/{id}/细分  /seckill/list
public class SeckillController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	/**
	 * 列表页数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		// 获取列表页
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		
		return "/list"; // list --> WEB-INF/jsp/list.jsp
	}
	
	/**
	 * 详情页数据
	 * @param seckillId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if(seckillId == null) {
			// 采用重定向,重新访问list页面
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if(seckill == null) {
			// 应该也用重定向(redirect),这里为了展示才使用转发(forward)
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		
		return "/detail";
	}
	
	/**
	 * ajax json, 获取秒杀接口数据, 如果没开启就返回系统时间和秒杀时间
	 * @param seckillId
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/exposer", 
			method = RequestMethod.POST, 
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 秒杀操作
	 * @param seckillId
	 * @param md5
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/{md5}/execution", 
			method = RequestMethod.POST, 
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId, 
											@PathVariable("md5") String md5, 
											@CookieValue(value = "killPhone", required = false) Long phone) {
		// 这个验证可以用spring的验证,springmvc valid
		if(phone == null) {
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
//		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatKillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, execution);
		}
	}
	
	/**
	 * 获取当前系统时间
	 * @return
	 */
	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}
	
}
