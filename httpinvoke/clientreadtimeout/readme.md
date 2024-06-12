

```shell
curl http://localhost:45678/clientreadtimeout/client
```

调用 client 接口后，从日志中可以看到，客户端 2 秒后出现了 SocketTimeoutException，原因是读取超时，服务端却丝毫没受影响在 3 秒后执行完成。

```
2024-06-02 16:55:57.624  INFO 28688 --- [io-45678-exec-5] i.k.h.c.ClientReadTimeoutController      : client1 called
2024-06-02 16:55:57.630  INFO 28688 --- [io-45678-exec-6] i.k.h.c.ClientReadTimeoutController      : server called
2024-06-02 16:55:59.641 ERROR 28688 --- [io-45678-exec-5] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception

java.net.SocketTimeoutException: Read timed out
	at java.net.SocketInputStream.socketRead0(Native Method) ~[na:1.8.0_131]
	.............

2024-06-02 16:56:02.631  INFO 28688 --- [io-45678-exec-6] i.k.h.c.ClientReadTimeoutController      : Done
```