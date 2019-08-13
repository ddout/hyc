HYC-Hello 
    Yare
    Cloud
zhishen
---
#### 模块说明
| 模块名称 | 端口范围 | 说明 |
| ---: | ---:    | :---: | 
| hyc-core|-|核心框架API |
| hyc-gateway|8600-8699|网关.使用Nginx做前端代理 |
| hyc-eureka|8700-8799|注册中心Eureka服务 |
| hyc-configure|8800-8899|配置中心(集群使用Nginx负载,不加入euraka) |
| hyc-log|8900-8999|统一日志sleuth |
| hyc-monitor|9000-9099|监控(集群使用Nginx负载) |
| hyc-services|00000-00000|具体业务实现包 |



#### 启动顺序
0. hyc-configure 必须第一个启动；其他应用的配置全都是从这里获取的；
1. hyc-eureka
2. hyc-monitor
3. hyc-gateway
4. 其他


#### 未加入
1. 鉴权
2. 日志(调用链追踪, 业务数据日志)  
   调用链需要使用sleuth的功能。包含 sessionid userid等的追踪
   配置中心统一配置，
     sleuth:
       propagation-keys:
         - x-hyc-session-id
         - x-hyc-user-id
   通过sleuth-rabbitmq 向mq服务发送日志；
   hyc-logger功能：收集mq服务的消息，然后入库存储；提供查询、展示调用链信息；
   问题：调用链目前可以通过sleuth收集信息，但没有接口传入和返回的数据收集功能？？？
        通过ELK 来搞实在一些。
3. 网关路由规则？？？？
    1. 网关对客户端业务调用只暴露一个通用接口，用于业务请求，使用service-key做具体接口区分(貌似违背rest设计了!!!)
       好处是，客户端只需要记录一个地址,用service-key来区分具体业务；不用做复杂的路由映射规则配置
    2. 通过service-key和access-token去鉴权中心获取授权和换取service-key对应的内部应用rest地址
       鉴权中心可以不用关心具体的rest地址是否变更；rest地址用eureka注册id匹配；
4. 熔断
5. 缓存
6. 分布事务


rabbitmq: guest/guest
mysql5.7: root/ZhipengWang2012@

test
