# blog-parent
博客练手级别项目，重点是掌握经验，掌握项目的编码结构与过程。技术栈：vue、springboot、mybatisplus、redis、七牛云

# 注意配置自己的jar版本问题

# 亮点

1. jwt redis

   token令牌的登录方式,访问认证速度快,session共享,安全性

   rdis做了令牌和用户信息的对应管理,1.进一步增加了安全性2.登录用户做了缓存3.灵活控制用户的过期(续期,踢掉线等)

2. threadLocal

   使用了保存用户信息,请求的线程之内,可以随时获取登录的用户,做了线程隔离，在使用完ThreadLocal之后,做了value的删除,防止了内存泄漏

3. 线程安全

   update table set value=newValue where id=1 and value=oldValue

4. 线程池

   应用非常广,面试7个核心参数(对当前的主业务流程无影响的操作,放入线程池执行)，登录,记录日志

6. 权限系统  重点内容

7. 统一日志记录,统一缓存处

8. AOP方式延迟双删+线程池异步删除缓存

9. 解决缓存一致性、预防缓存击穿问题

10. 采用分布式缓存、NGINX动静分离和反向代理
