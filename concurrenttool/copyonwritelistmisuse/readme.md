

```shell
curl http://localhost:8080/copyonwritelistmisuse/write
```

```shell
2024-05-31 20:22:35.137  INFO 23736 --- [nio-8080-exec-1] i.k.c.c.CopyOnWriteListMisuseController  : StopWatch '': running time = 4632533800 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
4607328500  099%  Write:copyOnWriteArrayList
025205300  001%  Write:synchronizedList
```

错误的使用copyOnWrite，性能反而会急剧下降

copyOnWrite 使用于读多写少或者说希望无锁读的场景


```shell
curl http://localhost:8080/copyonwritelistmisuse/read
```

```shell
2024-05-31 20:31:33.211  INFO 23736 --- [nio-8080-exec-2] i.k.c.c.CopyOnWriteListMisuseController  : StopWatch '': running time = 213286900 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
043876200  021%  Read:copyOnWriteArrayList
169410700  079%  Read:synchronizedList

```