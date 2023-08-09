## Windows

### 端口

**netstat -ano**

查看所以的端口[监听](https://so.csdn.net/so/search?q=监听&spm=1001.2101.3001.7020)情况，数据比较多。

![image-20230111162227240](http://mk-images.tagao.top/img/image-20230111162227240.png?imageslim)

**netstat -aon|findstr “8085”**

从结果中过滤指定端口 

![image-20230111162310806](http://mk-images.tagao.top/img/image-20230111162310806.png?imageslim)

**tasklist|findstr “13436”**

查看某个进程ID对应的进程名称

![image-20230111162450575](http://mk-images.tagao.top/img/image-20230111162450575.png?imageslim)



**taskkill /f /t /pid "13436"**

根据进程号杀死进程(/f:强制 /t:j进程树 )  ，此外：根据进程名称杀死进程 **taskkill /f /t /im "进程名称"**

![image-20230111162830375](http://mk-images.tagao.top/img/image-20230111162830375.png?imageslim)









## Linux



`crontab `   定时执行

`vim /etc/crontab   `编写

`/etc/init.d/cron restart  || service cron restart `重启

`chmod +777 文件名.后缀 `   授权

`/var/log/cron` 查看运行日志

目标： 每天上午9:00签到
分钟 小时 日期  月份 周几 用户名 命令
` 5      9       *     *      *    root /user/bin/python3.6pyth`

****

[linux crontab定时执行shell脚本](https://www.cnblogs.com/chen-lhx/p/5996781.html)

[crontab 添加sh文件定时](https://www.cnblogs.com/fenglan/p/5917593.html)



## df/du

`df`以磁盘分区为单位查看文件系统，可以获取硬盘被占用了多少空间，目前还剩下多少空间等信息：

<img src="http://mk-images.tagao.top/img/image-20230710105734043.png?imageslim" alt="image-20230710105734043" style="zoom:80%;" />

使用`df -h`命令来查看磁盘信息， -h 选项为根据大小适当显示：

![image-20230710105909674](http://mk-images.tagao.top/img/image-20230710105909674.png?imageslim)



`du` (disk usage)，含义为显示磁盘空间的使用情况，用于查看当前目录的大小

```shell
#查看当前目录大小
du -sh
#返回该目录/文件的大小
du -sh [目录/文件]


#查看当前文件夹下的所有文件大小（包含子文件夹）
du -h
#查看指定文件夹下的所有文件大小（包含子文件夹）
du -h [目录/文件]
#返回当前文件夹的总M数
du -sm
#返回指定文件夹/文件总M数
du -sm [文件夹/文件]
```



![image-20230710110115731](http://mk-images.tagao.top/img/image-20230710110115731.png?imageslim)





## Top

E e

f s





## PS

> `ps` （英文全拼：`process status`）命令用于显示当前进程的状态，类似于 windows 的任务管理器。

1）ps a 显示现行终端机下的所有程序，包括其他用户的程序。
2）ps -A 显示所有程序。 
3）ps c 列出程序时，显示每个程序真正的指令名称，而不包含路径，参数或常驻服务的标示。 
4）ps -e 此参数的效果和指定"A"参数相同。 
5）ps e 列出程序时，显示每个程序所使用的环境变量。 
6）ps f 用ASCII字符显示树状结构，表达程序间的相互关系。 
7）ps -H 显示树状结构，表示程序间的相互关系。 
8）ps -N 显示所有的程序，除了执行ps指令终端机下的程序之外。 
9）ps s 采用程序信号的格式显示程序状况。 
10）ps S 列出程序时，包括已中断的子程序资料。 
11）ps -t 　指定终端机编号，并列出属于该终端机的程序的状况。 
12）ps u 　以用户为主的格式来显示程序状况。 
13）ps x 　显示所有程序，不以终端机来区分。





ps -ef

![image-20230710103047981](http://mk-images.tagao.top/img/image-20230710103047981.png?imageslim)

列的意思

<img src="http://mk-images.tagao.top/img/image-20230710103423470.png?imageslim" alt="image-20230710103423470" style="zoom: 80%;" />



ps -aux

![image-20230710103303702](http://mk-images.tagao.top/img/image-20230710103303702.png?imageslim)

<img src="http://mk-images.tagao.top/img/image-20230710103446429.png?imageslim" alt="image-20230710103446429" style="zoom: 80%;" />

![image-20230710103541752](http://mk-images.tagao.top/img/image-20230710103541752.png?imageslim)

**ps**是显示当前状态处于running的进程，**grep**表示正则化搜索搜索，而**ps aux**是显示所有进程和其状态

```shell
$ ps aux | grep python  //查到使用python的进程
```



```shell
$ kill -s 9 pid号 //杀死进程
```





## find



全局搜索

```shell
find / -name aaa
```



```shell
find  -iname "*SaleContractFromDc*"
```



