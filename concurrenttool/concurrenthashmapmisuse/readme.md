
## 未加锁情况下


```shell
curl http://localhost:45678/concurrenthashmapmisuse/wrong
```

可能访问一次不会出现，多访问几次就可以复现这个场景了
```shell
2024-05-31 19:48:03.681  INFO 21952 --- [io-45678-exec-2] .k.c.c.ConcurrentHashMapMisuseController : init size:900
2024-05-31 19:48:03.683  INFO 21952 --- [Pool-8-worker-9] .k.c.c.ConcurrentHashMapMisuseController : gap size:100
2024-05-31 19:48:03.684  INFO 21952 --- [ool-8-worker-11] .k.c.c.ConcurrentHashMapMisuseController : gap size:100
2024-05-31 19:48:03.684  INFO 21952 --- [Pool-8-worker-2] .k.c.c.ConcurrentHashMapMisuseController : gap size:100
2024-05-31 19:48:03.685  INFO 21952 --- [Pool-8-worker-2] .k.c.c.ConcurrentHashMapMisuseController : gap size:-102
2024-05-31 19:48:03.684  INFO 21952 --- [ool-8-worker-11] .k.c.c.ConcurrentHashMapMisuseController : gap size:-86
2024-05-31 19:48:03.685  INFO 21952 --- [Pool-8-worker-2] .k.c.c.ConcurrentHashMapMisuseController : gap size:-102
2024-05-31 19:48:03.685  INFO 21952 --- [ool-8-worker-11] .k.c.c.ConcurrentHashMapMisuseController : gap size:-102
2024-05-31 19:48:03.685  INFO 21952 --- [Pool-8-worker-2] .k.c.c.ConcurrentHashMapMisuseController : gap size:-102
2024-05-31 19:48:03.685  INFO 21952 --- [Pool-8-worker-2] .k.c.c.ConcurrentHashMapMisuseController : gap size:-102
2024-05-31 19:48:03.685  INFO 21952 --- [ool-8-worker-11] .k.c.c.ConcurrentHashMapMisuseController : gap size:-102
2024-05-31 19:48:03.690  INFO 21952 --- [io-45678-exec-2] .k.c.c.ConcurrentHashMapMisuseController : finish size:1200
```

* 使用了 ConcurrentHashMap，不代表对它的多个操作之间的状态是一致的，是没有其他线程在操作它的，如果需要确保需要手动加锁。
* 诸如 size、isEmpty 和 containsValue 等聚合方法，在并发情况下可能会反映 ConcurrentHashMap 的中间状态。因此在并发情况下，这些方法的返回值只能用作参考，而不能用于流程控制。显然，利用 size 方法计算差异值，是一个流程控制。
* 诸如 putAll 这样的聚合方法也不能确保原子性，在 putAll 的过程中去获取数据可能会获取到部分数据。


## 加锁情况下


```shell
curl http://localhost:45678/concurrenthashmapmisuse/right
```

不管重复访问多少次，最终都是这个运行结果，加锁之后生效了
```shell
2024-05-31 19:55:08.469  INFO 21952 --- [io-45678-exec-1] .k.c.c.ConcurrentHashMapMisuseController : init size:900
2024-05-31 19:55:08.471  INFO 21952 --- [ool-18-worker-9] .k.c.c.ConcurrentHashMapMisuseController : gap size:100
2024-05-31 19:55:08.472  INFO 21952 --- [ool-18-worker-9] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.473  INFO 21952 --- [ool-18-worker-6] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.474  INFO 21952 --- [ool-18-worker-6] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.474  INFO 21952 --- [ool-18-worker-6] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.474  INFO 21952 --- [ool-18-worker-6] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.475  INFO 21952 --- [ool-18-worker-9] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.476  INFO 21952 --- [ool-18-worker-2] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.476  INFO 21952 --- [ol-18-worker-11] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.476  INFO 21952 --- [ol-18-worker-13] .k.c.c.ConcurrentHashMapMisuseController : gap size:0
2024-05-31 19:55:08.478  INFO 21952 --- [io-45678-exec-1] .k.c.c.ConcurrentHashMapMisuseController : finish size:1000
```

