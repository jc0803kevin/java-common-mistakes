
## 加锁要考虑锁的粒度和场景问题


```shell
curl http://localhost:8080/lockgranularity/right
```

```shell
curl http://localhost:8080/lockgranularity/wrong
```

执行这段代码，同样是 1000 次业务操作 ,粒度小的性能有明显的优越性
```
E:\workspace\jc0803kevin\java-common-mistakes\lock\lockgranularity>curl http://localhost:8080/lockgranularity/right
1000
E:\workspace\jc0803kevin\java-common-mistakes\lock\lockgranularity>curl http://localhost:8080/lockgranularity/wrong
2000
E:\workspace\jc0803kevin\java-common-mistakes\lock\lockgranularity>

```