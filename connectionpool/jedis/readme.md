
[](https://time.geekbang.org/column/article/211388)

### 
```shell
curl http://localhost:45678/jedismisreuse/wrong
```

写操作互相干扰，多条命令相互穿插的话，必然不是合法的 Redis 命令，那么 Redis 会关闭客户端连接，导致连接断开；
又比如，线程 1 和 2 先后写入了 get a 和 get b 操作的请求，Redis 也返回了值 1 和 2，但是线程 2 先读取了数据 1 就会出现数据错乱的问题。
```
Exception in thread "Thread-9" redis.clients.jedis.exceptions.JedisDataException: ERR Protocol error: invalid multibulk length
	at redis.clients.jedis.Protocol.processError(Protocol.java:132)
	at redis.clients.jedis.Protocol.process(Protocol.java:166)
	at redis.clients.jedis.Protocol.read(Protocol.java:220)
	at redis.clients.jedis.Connection.readProtocolWithCheckingBroken(Connection.java:318)
	at redis.clients.jedis.Connection.getBinaryBulkReply(Connection.java:255)
	at redis.clients.jedis.Connection.getBulkReply(Connection.java:245)
	at redis.clients.jedis.Jedis.get(Jedis.java:181)
	at icu.kevin.connectionpool.jedis.JedisMisreuseController.lambda$wrong$2(JedisMisreuseController.java:49)
	at java.lang.Thread.run(Thread.java:748)
Exception in thread "Thread-8" redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketException: Connection reset
	at redis.clients.jedis.util.RedisInputStream.ensureFill(RedisInputStream.java:205)
	at redis.clients.jedis.util.RedisInputStream.readByte(RedisInputStream.java:43)
	at redis.clients.jedis.Protocol.process(Protocol.java:155)
	at redis.clients.jedis.Protocol.read(Protocol.java:220)
	at redis.clients.jedis.Connection.readProtocolWithCheckingBroken(Connection.java:318)
	at redis.clients.jedis.Connection.getBinaryBulkReply(Connection.java:255)
	at redis.clients.jedis.Connection.getBulkReply(Connection.java:245)
	at redis.clients.jedis.Jedis.get(Jedis.java:181)
	at icu.kevin.connectionpool.jedis.JedisMisreuseController.lambda$wrong$1(JedisMisreuseController.java:40)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.net.SocketException: Connection reset
	at java.net.SocketInputStream.read(SocketInputStream.java:210)
	at java.net.SocketInputStream.read(SocketInputStream.java:141)
	at java.net.SocketInputStream.read(SocketInputStream.java:127)
	at redis.clients.jedis.util.RedisInputStream.ensureFill(RedisInputStream.java:199)
	... 9 more
```


### 

```shell
curl http://localhost:45678/jedismisreuse/right
```