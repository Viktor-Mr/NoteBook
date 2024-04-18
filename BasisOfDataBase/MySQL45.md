DDL语句Create

DML语句Insert、update、delete

## 基础架构

![image-20230821100054238](http://mk-images.tagao.top/img/image-20230821100054238.png?imageslim)



```markdown
连接器功能: 
	1.建立连接，获取权限，管理和维系连接 
	2.长连接:连接建立后，持续有请求发出，使用同一连接 
	3.短连接:连接建立后，几次查询后，连接自动断开，断开后需要重新建立连接后才能访问 
	4.空闲连接:连接建立后，没有后续动作，可以通过wait_timeout控制空闲连接存在时间默认8小时，查看show processlist (sleep表示空闲) 
	5.短连接存在时间比较短，经常需要重新建立连接，建立连接过程复杂消耗性能 
	6.长连接，mysql使用临时内存管理连接对象，一次大查询后内存不会释放，需要等到连接释放后内存才能，长连接累积过多，可能出现内存使用过大，被系统强杀掉(oom) 
	7.解决方案: 
		（1）定期断开 连接，释放长连接对象内存 
		（2）mysql5.7后，一次大查询后，可以通mysql_reset_connection将连接恢复掉连接刚建立状态，无需重新建立连接，验证权限
```





## 日志系统





Redo log 