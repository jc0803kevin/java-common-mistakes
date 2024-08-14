



#### -XX:+PrintFlagsFinal 查看所有可设置的非稳定性参数


#### -XX:UseBiasedLocking


```shell
root@DESKTOP-0JS7U4E:~# java -XX:+PrintFlagsFinal | grep UseBiasedLocking
     bool UseBiasedLocking                          = true                                {product}
```

#### -XX:BiasedLockingStartupDelay 偏向锁启动延迟时间

查看默认值

```shell

root@DESKTOP-0JS7U4E:~# java -XX:+PrintFlagsFinal | grep BiasedLockingStartupDelay
     intx BiasedLockingStartupDelay                 = 4000                                {product}

```


```shell
java.lang.Object object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
  8   4        (object header: class)    0xf80001e5
 12   4        (object alignment gap)    
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x00000000035af6a8 (thin lock: 0x00000000035af6a8)
  8   4        (object header: class)    0xf80001e5
 12   4        (object alignment gap)    
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
```

线程`Thread.sleep(5000)`之后，`0x0000000000000001`变成了`0x0000000000000005`

```shell
java.lang.Object object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   4        (object header: class)    0xf80001e5
 12   4        (object alignment gap)    
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000002f45805 (biased: 0x000000000000bd16; epoch: 0; age: 0)
  8   4        (object header: class)    0xf80001e5
 12   4        (object alignment gap)    
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
```