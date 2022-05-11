创建一个日期格式化的工具类,格式化风格使用枚举类中的常量指定

创建一个响应信息的实体类,将要响应给客户端的信息封装到这个类的对象中,
响应状态码使用枚举类中的常量表示


19-Apr-2022 21:41:00.888 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log Server.服务器版本: Apache Tomcat/8.5.73
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 服务器构建:        Nov 11 2021 13:14:36 UTC
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 服务器版本号:      8.5.73.0
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 操作系统名称:      Windows 10
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log OS.版本:           10.0
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 架构:              amd64
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log Java 环境变量:     C:\Program Files\Java\jdk1.8.0_201\jre
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log Java虚拟机版本:    1.8.0_201-b09
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log JVM.供应商:        Oracle Corporation
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log CATALINA_BASE:     C:\Users\33233\AppData\Local\JetBrains\IntelliJIdea2021.3\tomcat\46492390-b090-463a-b240-ae177285baaf
19-Apr-2022 21:41:00.889 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log CATALINA_HOME:     P:\Libs\apache-tomcat-8.5.73
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Djava.util.logging.config.file=C:\Users\33233\AppData\Local\JetBrains\IntelliJIdea2021.3\tomcat\46492390-b090-463a-b240-ae177285baaf\conf\logging.properties
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Dcom.sun.management.jmxremote=
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Dcom.sun.management.jmxremote.port=1099
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Dcom.sun.management.jmxremote.ssl=false
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Dcom.sun.management.jmxremote.password.file=C:\Users\33233\AppData\Local\JetBrains\IntelliJIdea2021.3\tomcat\46492390-b090-463a-b240-ae177285baaf\jmxremote.password
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Dcom.sun.management.jmxremote.access.file=C:\Users\33233\AppData\Local\JetBrains\IntelliJIdea2021.3\tomcat\46492390-b090-463a-b240-ae177285baaf\jmxremote.access
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Djava.rmi.server.hostname=127.0.0.1
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Djdk.tls.ephemeralDHKeySize=2048
19-Apr-2022 21:41:00.890 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Djava.protocol.handler.pkgs=org.apache.catalina.webresources
19-Apr-2022 21:41:00.891 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Dignore.endorsed.dirs=
19-Apr-2022 21:41:00.891 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Dcatalina.base=C:\Users\33233\AppData\Local\JetBrains\IntelliJIdea2021.3\tomcat\46492390-b090-463a-b240-ae177285baaf
19-Apr-2022 21:41:00.891 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Dcatalina.home=P:\Libs\apache-tomcat-8.5.73
19-Apr-2022 21:41:00.891 信息 [main] org.apache.catalina.startup.VersionLoggerListener.log 命令行参数：       -Djava.io.tmpdir=P:\Libs\apache-tomcat-8.5.73\temp
19-Apr-2022 21:41:00.891 信息 [main] org.apache.catalina.core.AprLifecycleListener.lifecycleEvent 使用APR版本[1.7.0]加载了基于APR的Apache Tomcat本机库[1.2.31]。
19-Apr-2022 21:41:00.891 信息 [main] org.apache.catalina.core.AprLifecycleListener.lifecycleEvent APR功能：IPv6[true]、sendfile[true]、accept filters[false]、random[true]。
19-Apr-2022 21:41:00.891 信息 [main] org.apache.catalina.core.AprLifecycleListener.lifecycleEvent APR/OpenSSL配置：useAprConnector[false]，useOpenSSL[true]
19-Apr-2022 21:41:00.894 信息 [main] org.apache.catalina.core.AprLifecycleListener.initializeSSL OpenSSL成功初始化 [OpenSSL 1.1.1l  24 Aug 2021]
19-Apr-2022 21:41:00.972 信息 [main] org.apache.coyote.AbstractProtocol.init 初始化协议处理器 ["http-nio-8080"]
19-Apr-2022 21:41:00.984 信息 [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
19-Apr-2022 21:41:00.995 信息 [main] org.apache.catalina.startup.Catalina.load Initialization processed in 382 ms
19-Apr-2022 21:41:01.020 信息 [main] org.apache.catalina.core.StandardService.startInternal 正在启动服务[Catalina]
19-Apr-2022 21:41:01.021 信息 [main] org.apache.catalina.core.StandardEngine.startInternal 正在启动 Servlet 引擎：[Apache Tomcat/8.5.73]
19-Apr-2022 21:41:01.027 信息 [main] org.apache.coyote.AbstractProtocol.start 开始协议处理句柄["http-nio-8080"]
19-Apr-2022 21:41:01.035 信息 [main] org.apache.catalina.startup.Catalina.start Server startup in 39 ms
Connected to server
