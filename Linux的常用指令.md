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