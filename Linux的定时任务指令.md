pwd                                 查看当前目录的名字
ls                                     查看当前目录的下的文件名
cat 文件名.后缀                 进入文件，查看内容
touch 文件名.后缀             当前目录下新建文件
cd 文件名                           进入路径
crontab                           定时执行
mkdir  文件夹名              新建一个文件夹
cat  文件.后缀                 查看文本内容（可以改）
vim                               进入文本（不可改）



crontab  

编写
vim /etc/crontab

重启
/etc/init.d/cron restart

chmod +777 文件名.后缀    授权



目标： 每天上午9:00签到
分钟 小时 日期  月份 周几 用户名 命令
 5      9       *     *      *    root /user/bin/python3.6pyth




