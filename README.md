# seckill

#### 项目介绍
基于慕课网的"Java高并发秒杀API"系列的课程所做的一个完成项目

#### 软件架构
- 编译工具:eclipse-oxygen
- 数据库:mysql 5.5
- 服务器:Tomcat 7.0
- 项目构建工具:Maven 3.5.4
- jdk版本:jdk1.8.0_131
- 
- 注意,该项目只是进行前3个课程的编写,不涉及第4个课程高并发相关的代码,可以移步到另一个项目seckills,在seckill的基础上,添加了高并发相关的代码编写.


#### 安装教程

1. 创建一个新的Maven项目,没有Maven选项的,说明eclipse没有按照Maven插件.
![](https://github.com/iradicor/seckill/blob/master/images/TIM%E5%9B%BE%E7%89%8720180716164318.png "TIM图片20180716164318.png")
![](https://images.gitee.com/uploads/images/2018/0716/164743_d52845e6_1857479.png "TIM图片20180716164332.png")
![](https://images.gitee.com/uploads/images/2018/0716/164815_2ff08ade_1857479.png "TIM图片20180716164335.png")
![](https://images.gitee.com/uploads/images/2018/0716/164822_8e91a303_1857479.png "TIM图片20180716164339.png")
2. 导入项目
![](https://images.gitee.com/uploads/images/2018/0716/164843_9594de39_1857479.png "TIM图片20180716164342.png")
3. 将下载的src文件夹和pom.xml文件一起拖动到seckill项目的根目录下,选择Copy files,点击OK,选择Overwrite All来进行全部替换,导入后发现有很多地方报错,先把错误解决.
![](https://images.gitee.com/uploads/images/2018/0716/164937_9aea712e_1857479.png "TIM图片20180716164345.png")
4. 打开eclipse配置页
![](https://images.gitee.com/uploads/images/2018/0716/165013_7e5a9813_1857479.png "TIM图片20180716164347.png")
5. 找到server一栏-->Runtime-->查看是否添加了Tomcat服务器,如果没有,点击右侧的add来添加一个
![](https://images.gitee.com/uploads/images/2018/0716/165024_743c475e_1857479.png "TIM图片20180716164350.png")
6. 鼠标右键项目名,打开项目属性页.
![](https://images.gitee.com/uploads/images/2018/0716/165216_48984dd8_1857479.png "TIM图片20180716164354.png")
7. 为项目添加Tomcat环境
- ![](https://images.gitee.com/uploads/images/2018/0716/165246_0193c1ef_1857479.png "TIM图片20180716164357.png")
- ![](https://images.gitee.com/uploads/images/2018/0716/165255_2be9045e_1857479.png "TIM图片20180716164400.png")
- ![](https://images.gitee.com/uploads/images/2018/0716/165323_b1fef8ba_1857479.png "TIM图片20180716164403.png")
- ![](https://images.gitee.com/uploads/images/2018/0716/165335_e488cf69_1857479.png "TIM图片20180716164406.png")
8. 到此添加完成,然后打开web.xml文件,在WEB-INF下.
- ![](https://images.gitee.com/uploads/images/2018/0716/165507_4270d277_1857479.png "TIM图片20180716164409.png")
9. 发现web.xml用的是3.0,而项目默认的版本是2.3
- ![](https://images.gitee.com/uploads/images/2018/0716/165555_af00939e_1857479.png "TIM图片20180716164414.png")
10. 打开项目属性页(右键项目名,点最后一个,Properties),点击Project Facets,我们发现web版本是2.3,尝试更改为3.0,发现无法更改
- ![](https://images.gitee.com/uploads/images/2018/0716/165815_d3ebf431_1857479.png "TIM图片20180716164416.png")
11. 换另一种方式更改,打开Navigator.
- ![](https://images.gitee.com/uploads/images/2018/0716/165847_3717b907_1857479.png "TIM图片20180716164419.png")
12. 打开core.xml的那个文件
- ![](https://images.gitee.com/uploads/images/2018/0716/165932_8330ed9e_1857479.png "TIM图片20180716164422.png")
13. 修改jst.web的version,将2.3改为3.0
- ![](https://images.gitee.com/uploads/images/2018/0716/165946_f78e5ba1_1857479.png "TIM图片20180716164425.png")
- ![](https://images.gitee.com/uploads/images/2018/0716/170019_a767c8d7_1857479.png "TIM图片20180716164427.png")
14. 然后更新一下Maven项目
- ![](https://images.gitee.com/uploads/images/2018/0716/170048_63fd3518_1857479.png "TIM图片20180716164430.png")
15. 发现报错消失.
- ![](https://images.gitee.com/uploads/images/2018/0716/170122_7258e461_1857479.png "TIM图片20180716164435.png")
16. 修改配置信息
17. 修改数据库连接对象和logback配置信息.
- ![](https://images.gitee.com/uploads/images/2018/0716/170701_1be401fe_1857479.png "TIM图片20180716164438.png")
18. 数据库的用户名和密码修改为自己的
- ![](https://images.gitee.com/uploads/images/2018/0716/170735_2791e58e_1857479.png "TIM图片20180716164441.png")
19. 日志保存路径修改为自己的目录下,这里是放到D盘的logs目录下了
- ![](https://images.gitee.com/uploads/images/2018/0716/170745_5de709f9_1857479.png "TIM图片20180716164444.png")
20. 接下来将项目添加到Tomcat服务器上,运行.
- ![](https://images.gitee.com/uploads/images/2018/0716/170903_b88ffe0f_1857479.png "TIM图片20180716164447.png")
- ![](https://images.gitee.com/uploads/images/2018/0716/170912_1225c80a_1857479.png "TIM图片20180716164450.png")
21. 打开浏览器访问测试:locallhost:8080/seckill
- ![](https://images.gitee.com/uploads/images/2018/0716/170953_1d1453f5_1857479.png "TIM图片20180716164453.png")
22. 项目截图
- ![](https://images.gitee.com/uploads/images/2018/0716/171019_1d05ca13_1857479.png "TIM图片20180716164455.png")

