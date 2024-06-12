
## Spring 的事务可能没有生效

### 标记了@Transactional的private方法

```shell
curl http://localhost:45678/transactionproxyfailed/wrong1?name=kevin
```

```shell
2024-06-12 14:43:39.943 DEBUG 17836 --- [io-45678-exec-2] o.j.s.OpenEntityManagerInViewInterceptor : Opening JPA EntityManager in OpenEntityManagerInViewInterceptor
2024-06-12 14:43:47.938 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Found thread-bound EntityManager [SessionImpl(742185506<open>)] for JPA transaction
2024-06-12 14:43:47.939 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
2024-06-12 14:43:47.942 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@5a4619fd]
2024-06-12 14:43:47.959 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
2024-06-12 14:43:47.960 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Committing JPA transaction on EntityManager [SessionImpl(742185506<open>)]
2024-06-12 14:43:47.975 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Not closing pre-bound JPA EntityManager after transaction
2024-06-12 14:43:57.637 ERROR 17836 --- [io-45678-exec-2] i.k.t.t.UserService                      : create user failed because invalid username!  #虽然抛出了异常，但是事务还是提交了
2024-06-12 14:43:57.641 DEBUG 17836 --- [io-45678-exec-2] o.j.s.OpenEntityManagerInViewInterceptor : Closing JPA EntityManager in OpenEntityManagerInViewInterceptor

```

原因是，Spring 默认通过动态代理的方式实现 AOP，对目标方法进行增强，private 方法无法代理到，Spring 自然也无法动态增强事务处理逻辑。


### 自调用

```shell
curl http://localhost:45678/transactionproxyfailed/wrong2?name=kevintest
```

```shell
2024-06-12 14:51:44.060 DEBUG 17836 --- [io-45678-exec-2] o.j.s.OpenEntityManagerInViewInterceptor : Opening JPA EntityManager in OpenEntityManagerInViewInterceptor
2024-06-12 14:51:44.061 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Found thread-bound EntityManager [SessionImpl(1186588810<open>)] for JPA transaction
2024-06-12 14:51:44.061 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
2024-06-12 14:51:44.063 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@4515776a]
2024-06-12 14:51:44.076 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
2024-06-12 14:51:44.076 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Committing JPA transaction on EntityManager [SessionImpl(1186588810<open>)]
2024-06-12 14:51:44.082 DEBUG 17836 --- [io-45678-exec-2] o.s.orm.jpa.JpaTransactionManager        : Not closing pre-bound JPA EntityManager after transaction
2024-06-12 14:51:44.082 ERROR 17836 --- [io-45678-exec-2] i.k.t.t.UserService                      : create user failed because invalid username!  ##抛出了异常
2024-06-12 14:51:44.085 DEBUG 17836 --- [io-45678-exec-2] o.j.s.OpenEntityManagerInViewInterceptor : Closing JPA EntityManager in OpenEntityManagerInViewInterceptor

```

必须通过代理过的类从外部调用目标方法才能生效。
