
## 事务即便生效也不一定能回滚

### 标记了@Transactional的private方法

```shell
curl http://localhost:45678/transactionpropagation/wrong?name=kevin
```

