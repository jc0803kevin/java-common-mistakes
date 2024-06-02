
## 未加锁情况下


```shell
curl http://localhost:8080/lockscope/wrong2
```

```shell
2024-06-01 19:27:27.670  INFO 22572 --- [      Thread-20] icu.kevin.lock.lockscope.Interesting     : a:87525,b:87540,false
2024-06-01 19:27:27.670  INFO 22572 --- [      Thread-20] icu.kevin.lock.lockscope.Interesting     : a:103108,b:103110,false
2024-06-01 19:27:27.670  INFO 22572 --- [      Thread-20] icu.kevin.lock.lockscope.Interesting     : a:108157,b:108156,true
2024-06-01 19:27:27.670  INFO 22572 --- [      Thread-20] icu.kevin.lock.lockscope.Interesting     : a:111484,b:111483,true
```

compare 方法在判断 a<b 的时候才打印日志， 但是a>b 居然也成立

线程安全问题，体现出来


正确的做法应该是，为 add 和 compare 都加上方法锁



```shell
curl http://localhost:8080/lockscope/wrong
```