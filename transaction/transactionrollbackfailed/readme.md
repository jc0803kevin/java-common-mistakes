
## 

### 

```shell
curl http://localhost:45678/transactionrollbackfailed/wrong1?name=kevin
```


### 错误的异常配置类型

```shell
curl http://localhost:45678/transactionrollbackfailed/wrong2?name=kevin
```


```shell
2024-06-12 15:57:17.299 DEBUG 4960 --- [io-45678-exec-2] o.j.s.OpenEntityManagerInViewInterceptor : Opening JPA EntityManager in OpenEntityManagerInViewInterceptor
2024-06-12 15:57:17.300 DEBUG 4960 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Found thread-bound EntityManager [SessionImpl(89426235<open>)] for JPA transaction
2024-06-12 15:57:17.300 DEBUG 4960 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Creating new transaction with name [icu.kevin.icu.kevin.tx.transactionrollbackfailed.UserService.createUserWrong2]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
2024-06-12 15:57:17.301 DEBUG 4960 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@67bf2a48]
2024-06-12 15:57:17.301 DEBUG 4960 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Found thread-bound EntityManager [SessionImpl(89426235<open>)] for JPA transaction
2024-06-12 15:57:17.301 DEBUG 4960 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Participating in existing transaction
2024-06-12 15:57:17.315 DEBUG 4960 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
2024-06-12 15:57:17.315 DEBUG 4960 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Committing JPA transaction on EntityManager [SessionImpl(89426235<open>)]
2024-06-12 15:57:17.320 DEBUG 4960 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Not closing pre-bound JPA EntityManager after transaction
2024-06-12 15:57:17.322 ERROR 4960 --- [io-45678-exec-2] .t.t.TransactionRollbackFailedController : create user failed

java.nio.file.NoSuchFileException: file-that-not-exist
	at sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:79) ~[na:1.8.0_131]
	.....................
	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_131]

2024-06-12 15:57:17.325 DEBUG 4960 --- [io-45678-exec-2] o.j.s.OpenEntityManagerInViewInterceptor : Closing JPA EntityManager in OpenEntityManagerInViewInterceptor
```
