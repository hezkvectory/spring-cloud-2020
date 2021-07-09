<a href="https://blog.csdn.net/huaishu/article/details/89880971">分布式事务 Seata TCC 模式深度解析</a>


tcc事物要注意三点：<br/>
1、空回滚<br/> 
2、幂等<br/> 
3、悬挂<br/> 
解决办法，加本地事物消息表<br/> 
1、消息最终一致性 <br/>
2、最大努力通知 <br/>
3、消息事物表 <br/>
4、tcc<br/>


TCC(侵入、耦合)<br/>
本地消息、事物消息、最大努力通知(容忍时延)<br/>
时间敏感<br/>



