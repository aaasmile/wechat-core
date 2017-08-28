##dev目录使用说明

从gitlab导出项目到本地后，maven默认激活profile:dev，
执行编译时会将该目录下的配置文件拷贝到target/classes目录下并覆盖原配置文件，以方便本地开发.

该目录仅供本地开发修改配置时使用，目录路径已添加到.gitignore，不需要提交到服务器。

日志现在采用log4j2.xml, 默认配置为输出到文件以便测试环境部署,
本地开发时可将src/test/resources/log4j2.xml拷贝至src/main/profile/dev/目录下,
并根据个人需要进行修改