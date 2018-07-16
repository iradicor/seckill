// 存放主要交互逻辑js代码
// javascript 模块化
// seckill.detail.init(params);
var seckill ={
	// 封装秒杀相关ajax的url
	URL : {
		now : function(){
			return '/seckill/time/now';
		},
		exposer : function(seckillId) {
			return '/seckill/'+seckillId+'/exposer';
		},
		execution : function(seckillId,md5) {
			return '/seckill/'+seckillId+'/'+md5+'/execution';
		}
	},
	handleSeckillkill : function(seckillId,node) {
		// 获取秒杀地址，控制显示逻辑，执行秒杀
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
		$.post(seckill.URL.exposer(seckillId),{},function(result){
			// 在回调函数中，执行交互流程
			if(result && result['success']){
				var exposer = result['data'];
				if(exposer['exposed']) {
					console.log("有秒杀地址接口");
					// 开启秒杀,获取秒杀地址
					var md5 = exposer['md5'];
					var killUrl = seckill.URL.execution(seckillId,md5);
					console.log("秒杀的地址为:"+killUrl);
					// 绑定一次点击事件
					$('#killBtn').one('click',function(){
						console.log("开始进行秒杀,按钮被禁用");
						// 执行秒杀请求
						// 1:先禁用按钮
						$('#killBtn').addClass('disabled');
						// 2:发送秒杀请求，执行秒杀
						$.post(killUrl,{},function(result){
							if(result && result['success']) {
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								console.log("秒杀状态" + stateInfo);
								// 3:显示秒杀结果
								node.html('<span class="label label-success">' + stateInfo + '</span>');
							}
						});
					});
					node.show();
					
				}else{
					console.warn("还没有暴露秒杀地址接口,无法进行秒杀");
					// 未开启秒杀
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					console.log("当前时间" + now);
                    console.log("开始时间" + start);
                    console.log("结束时间" + end);
                    console.log("开始倒计时");
                    console.debug("开始进行倒计时");
					// 重新计算计时逻辑
					seckill.countdown(seckillId,now,start,end);
				}
			}else{
				console.error("服务器端查询秒杀商品详情失败");
				consele.log('result:'+result);
			}
		});
	},
	// 验证手机号
	validatePhone : function(phone) {
		// isNaN是判断字符串是非数字,这里取反判断phone是数字
		if(phone && phone.length == 11 && !isNaN(phone)) {
			return true;
		}else {
			return false;
		}
	},
	countdown : function(seckillId,nowTime,startTime,endTime) {
		var seckillBox = $('#seckill-box');
		// 时间判断
		if(nowTime > endTime) {
			// 系统时间比结束时间大秒杀结束
			seckillBox.html('秒杀结束！');
		}else if(nowTime < startTime) {
			// 系统时间比开始时间小,秒杀未开始,计时事件绑定
			var killTime = new Date(startTime + 1000);
			// jQuery.countdown插件提供一个事件绑定的操作
			seckillBox.countdown(killTime,function(event){
				// 事件格式
				var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
				seckillBox.html(format);
			}).on('finish.countdown',function(){
				// 获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSeckillkill(seckillId,seckillBox);
			});
		}else{
			// 秒杀开始
			seckill.handleSeckillkill(seckillId,seckillBox);
		}
	},
    
	// 详情页秒杀逻辑
	detail : {
		// 详情页初始化
		init : function(params) {
			// 手机验证和登录，计时交互
			// 规划我们的交互流程
			// 在cookie中查找手机号
			var killPhone = $.cookie('killPhone');
			
			// 验证手机号
			if(!seckill.validatePhone(killPhone)) {
				// 绑定phone
				// 控制输出,根据弹出层的id来获取弹出层
				var killPhoneModal = $('#killPhoneModal');
				// 显示弹出层
				killPhoneModal.modal({
					show : true,// 显示弹出层(弹出层默认是fade,也就是隐藏的)
					backdrop : 'static',// 禁止位置关闭(防止点击其他位置来关闭弹出层)
					keyboarg : false// 关闭键盘事件(防止按下Esc键来关闭弹出层)
				});
				// 为提交按钮添加点击事件
				$('#killPhoneBtn').click(function(){
					var inputPhone = $('#killPhoneKey').val();
					// 在控制台输出log,注意!:console不能乱用
					console.log('inputPhone='+inputPhone);// TODO
					if(seckill.validatePhone(inputPhone)) {
						// 电话写入cookie, 3个参数 : cookie的name,cookie的值,cookie的属性(持续时间和作用范围)
						$.cookie('killPhone',inputPhone,{expires : 7,path : '/seckill'});
						// 刷新页面
						window.location.reload();
					}else {
						// 先隐藏,再放内容,防止用户看到直接方内容时后看到中间的一个过程
						$("#killPhoneMessage").hide().html("<label class='label label-danger'>手机号码错误</label>").show(300);
					}
				});
			}
			// 已经登录
			// 计时交互
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			$.get(seckill.URL.now(),{},function(result){
				if(result && result['success']){
					var nowTime = result['data'];
					// 时间判断
					seckill.countdown(seckillId,nowTime,startTime,endTime);
					
				}else{
					console.log('result:'+result);
				}
			});
		}
	}
}