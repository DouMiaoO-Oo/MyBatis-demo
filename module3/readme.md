# 自定义映射 resultMap

多对一映射处理
- 级联方式处理映射关系
- 使用 association 处理映射关系

# 分布查询



# 样例使用的 MySQL 表

```sql
DROP DATABASE IF EXISTS learnjdbc;
CREATE DATABASE learnjdbc;
USE learnjdbc;

CREATE TABLE dept (
  did BIGINT AUTO_INCREMENT NOT NULL,
  dept_name VARCHAR(50) NOT NULL,
  PRIMARY KEY(did)
) Engine=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE emp (
  eid BIGINT AUTO_INCREMENT NOT NULL,
  emp_name VARCHAR(50) NOT NULL,
  age INT NOT NULL,
  sex VARCHAR(50) NOT NULL,
  PRIMARY KEY(eid),
  did BIGINT,
  FOREIGN KEY (did) REFERENCES dept(did)
) Engine=INNODB DEFAULT CHARSET=UTF8;


insert into learnjdbc.dept values (NULL, '业务部'),  (NULL, '职能部');
insert into learnjdbc.emp values 
    (NULL, 'wang', 22, 'man', 1),
    (NULL, 'ming', 23, 'man', 1),
    (NULL, 'king', 23, 'woman', 2);
```
