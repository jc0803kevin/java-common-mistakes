
[](https://time.geekbang.org/column/article/209494)

### 线程重用
```shell
curl http://localhost:45678/threadlocal/wrong?userId=3
```

```shell
E:\workspace\jc0803kevin\java-common-mistakes\concurrenttool-threadlocal>curl http://localhost:45678/threadlocal/wrong?userId=3
{"before":"http-nio-45678-exec-1:null","after":"http-nio-45678-exec-1:3"}
E:\workspace\jc0803kevin\java-common-mistakes\concurrenttool-threadlocal>curl http://localhost:45678/threadlocal/wrong?userId=2
{"before":"http-nio-45678-exec-1:3","after":"http-nio-45678-exec-1:2"}
E:\workspace\jc0803kevin\java-common-mistakes\concurrenttool-threadlocal>curl http://localhost:45678/threadlocal/wrong?userId=1
{"before":"http-nio-45678-exec-1:2","after":"http-nio-45678-exec-1:1"}
E:\workspace\jc0803kevin\java-common-mistakes\concurrenttool-threadlocal>curl http://localhost:45678/threadlocal/wrong?userId=4
{"before":"http-nio-45678-exec-1:1","after":"http-nio-45678-exec-1:4"}
E:\workspace\jc0803kevin\java-common-mistakes\concurrenttool-threadlocal>

```

* 第一步，before 之前是null，
* 第二步，因为线程的重用，使用了上一步的的值`3`,所以before的值是`3`

### 

```shell
curl http://localhost:45678/threadlocalmemoryleak/wrong
```
