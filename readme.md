# 环境
JDK8
MySQL5.7

在 src/main/resources 下新建核心配置文件 mybatis-config.xml
在 src/main/resources 下新建数据库连接的配置文件 jdbc.properties
在 src/main/resources 下新建 mapper目录

# 介绍
MyBatis 中的 mapper 接口相当于以前的dao。但是区别在于，mapper 仅仅是接口，我们不需要提供实现类。
## 前置知识

**相关概念：** ORM（Object Relationship Mapping）对象关系映射。
**对象：** Java的实体类对象
**关系：** 关系型数据库
**映射：** 二者之间的对应关系

| Java概念 | 数据库概念 |
|--------|-------|
| 类      | 表     |
| 属性     | 字段/列  |
| 对象     | 记录/行  |


## MyBatis的映射文件
MyBatis映射文件存放的位置位于 src/main/resources/mapper 目录下
映射文件的命名 = 表所对应的实体类 + Mapper.xml

例如：数据库表 learnjdbc.t_user 映射的实体类为 User，所对应的映射文件为 UserMapper.xml
因此一个映射文件对应一个实体类，对应一张表的操作
MyBatis映射文件用于编写SQL，访问以及操作表中的数据

MyBatis中可以面向接口操作数据，要保证两个一致：
- mapper接口的全类名和映射文件的 namespace 保持一致
- mapper接口中的方法名和映射文件中编写SQL标签的id属性保持一致

```sql
-- 表 learnjdbc.t_user 创建语句
CREATE TABLE learnjdbc.t_user (
  id BIGINT AUTO_INCREMENT NOT NULL,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(50) NOT NULL,
  PRIMARY KEY(id)
) Engine=INNODB DEFAULT CHARSET=UTF8;
```

```xml
<!-- UserMapper.xml -->
<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper  
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">  
<mapper namespace="org.example.mapper.UserMapper">  
	<!--int insertUser();-->  
	<insert id="insertUser">  
		insert into t_user values(null,'张三','123',23,'女')  
	</insert>  
</mapper>
```

**涉及到的对象包括**： 数据库的表 - Java实体类 - Java接口 - xml配置文件

## 核心配置文件
习惯上命名为mybatis-config.xml.
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--引入properties文件，此时就可以${属性名}的方式访问属性值-->
    <properties resource="jdbc.properties"/>

    <!--设置类型别名-->
    <typeAliases>
        <!--
            typeAlias：设置某个类型的别名
            属性：
                type：需要设置别名的类型
                alias：类型的别名（不区分大小写）；若不设置该属性，那么该类型拥有默认的别名，即不区分大小写的类名
        -->
        <!--<typeAlias type="org.example.pojo.User"></typeAlias>-->

        <!--以包为单位，包下所有的类型均设置默认的类型别名，即不区分大小写的类名-->
        <package name="org.example.pojo"/>
    </typeAliases>

    <!--
        environments：配置多个连接数据库的环境
        属性 default：设置默认使用的环境的id
    -->
    <environments default="development">
        <!--
            environment：配置某个具体的环境
            属性 id：表示连接数据库的环境的唯一标识，不能重复
        -->
        <environment id="development">

            <!--
                transactionManager：设置事务管理方式
                属性：
                    type="JDBC|MANAGED"
                    JDBC：表示当前环境中，执行SQL时使用的是 JDBC 中原生的事务管理方式，事务的提交或回滚需要手动处理
                    MANAGED：被管理. 例如交给 Spring 管理
            -->
            <transactionManager type="JDBC"/>

            <!--
                dataSource：配置数据源
                属性：
                    type：设置数据源的类型
                    type="POOLED|UNPOOLED|JNDI"
                    POOLED：表示使用数据库连接池缓存数据库连接，即会将创建的连接进行缓存，下次使用可以从缓存中直接获取，不需要重新创建
                    UNPOOLED：表示不使用数据库连接池，即每次使用连接都需要重新创建
                    JNDI：表示使用上下文中的数据源
            -->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--引入映射文件-->
    <mappers>

<!--
    不在 package 包路径 main/java 下面，而是在 main/resources 路径下面，需要用普通目录的形式来引入
-->

        <!--单独引入映射文件 -->
<!--        <mapper resource="org/example/mapper/UserMapper.xml"/>-->

        <!--以包为单位引入映射文件
        注意：
			1. mapper接口和映射文件必须在相同的包下
			2. mapper接口和映射文件的名字一致
        -->
        <package name="org.example.mapper"/>
    </mappers>
</configuration>
```

# 带参数的 MyBatis 映射文件
MyBatis 映射文件 *Mapper.xml如何支持带有参数的 SQL 语句？

MyBatis获取参数值的两种方式：
- ${}. ${}的本质是字符串拼接，为字符串类型或日期类型的字段进行赋值时，需要手动添加单引号
- #{}. #{}的本质是占位符赋值，为字符串类型或日期类型的字段进行赋值时，可以自动添加单引号

## 单个字面量类型的参数
若 mapper 接口中的方法参数为单个的字面量类型，此时可以使用 ${} 或 #{} 以任意的名称（最好见名识意）获取参数的值
注意 ${} 需要手动加单引号

```xml
<!--User getUserByUsername(String username);-->
<select id="getUserByUsername" resultType="User">  
	select * from t_user where username = '${username}'  
</select>
```

```xml
<!--User getUserByUsername(String username);-->
<select id="getUserByUsername" resultType="User">
	select * from t_user where username = #{username}
</select>
```

## 多个字面量类型的参数
若 mapper 接口中的方法参数为多个时，此时 MyBatis 会自动将这些参数放在一个 map 集合中.
两种获取参数值的方式:
- 以 arg0, arg1...为键，以参数为值
- 以 param1, param2...为键，以参数为值

因此只需要通过 ${} 或 #{} 访问 map 集合的键就可以获取相对应的值
注意 ${} 需要手动加单引号

使用 arg 或者 param 都行，要注意的是 arg 是从 arg0 开始的，param 是从 param1 开始的
```xml
<!--User checkLogin(String username,String password);-->
<select id="checkLogin" resultType="User">  
	select * from t_user where username = #{arg0} and password = #{arg1}  
</select>
```

```xml
<!--User checkLogin(String username,String password);-->
<select id="checkLogin" resultType="User">
	select * from t_user where username = '${param1}' and password = '${param2}'
</select>
```

## map 集合类型的参数
若 mapper 接口中的方法需要的参数为多个时，此时可以手动创建 map 集合，将这些数据放在 map 中只需要通过 ${} 或 #{} 访问 map 集合的键就可以获取相对应的值
注意 ${} 需要手动加单引号
```xml
<!--int insertUserByMap(Map<String, Object> map);-->
 <insert id="insertUserByMap">
    insert into t_user values(#{id},#{username}, #{password} ,${age}, '${gender}')
</insert>
```

## 实体类对象型的参数（常用）
若 mapper 接口中的方法参数为实体类对象时此时可以使用 ${} 和 #{}，通过访问实体类对象中的属性名获取属性值
注意 {} 需要手动加单引号
```xml
<insert id="insertUserByObject">
    insert into t_user values(#{id},#{username}, #{password} ,${age}, '${gender}')
</insert>
```

## 使用 @Param 标识参数（常用）
可以通过 @Param 注解标识 mapper 接口中的方法参数，此时会将这些参数放在 map 集合中
两种获取参数值的方式:
- 以 @Param 注解的 value 属性值为键，以参数为值；
- 以param1,param2...为键，以参数为值；
```xml
<select id="getUserByGenderAge" resultType="User">
    select * from t_user where gender = #{xb} and age = #{param2}
</select>
```

# 不同返回值类型的 MyBatis 映射文件

select 标签必须设置属性 resultType 或 resultMap
用于设置实体类和数据库表的映射关系：
- resultType：自动映射，用于属性名和表中字段名一致的情况
- resultMap：自定义映射，用于一对多或多对一或字段名和属性名不一致的情况


如果查询出的数据只有一条，可以通过
- 实体类对象接收
- List 集合接收
- Map集合接收，结果{password=123456, sex=男, id=1, age=23, username=admin}

如果查询出的数据有多条，一定不能用实体类对象接收，会抛异常TooManyResultsException，可以通过
- 实体类类型的 List 集合接收
- Map类型的 List 集合接收
- 在 mapper 接口的方法上添加 @MapKey 注解

## 查询一条记录返回一个实体类对象
```java
/**
* 根据用户id查询用户信息
* @param id
* @return
*/
User getUserById(@Param("id") int id);
```

MyBatis 映射文件:

```xml
<!--User getUserById(@Param("id") int id);-->
<select id="getUserById" resultType="User">
	select * from t_user where id = #{id}
</select>
```


## 查询多条记录返回实体类对象的列表
通过实体类类型的 list 集合接收
```java
/**
 * 查询所有用户信息
 * @return
 */
List<User> getAllUser();
```

MyBatis 映射文件:
```xml
<!--List<User> getAllUser();-->
<select id="getAllUser" resultType="User">
	select * from t_user
</select>
```

## 查询单条记录中的单个字段（scalar）

```java
/**  
 * 查询用户的总记录数  
 * @return  
 * 在MyBatis中，对于Java中常用的类型都设置了类型别名  
 * 例如：java.lang.Integer-->int|integer  
 * 例如：int-->_int|_integer  
 * 例如：Map-->map,List-->list  
 */  
int getCount();
```
MyBatis 映射文件:
```xml
<!--int getCount();-->
<select id="getCount" resultType="_integer">
	select count(id) from t_user
</select>
```

## 查询多条记录中的单个字段（scalar 列表）

```java
List<Integer> getAllAge();  // 测试返回 scalar 列表
```
MyBatis 映射文件:
```xml
<!--List<Integer> getAllAge();-->
<select id="getAllAge" resultType="_integer">
    select age from t_user
</select>
```

## 查询一条记录返回 map 集合
```java
Map<String, Object> getUserViaMap();  // 测试返回 map
```

MyBatis 映射文件:
```xml
<!--List<Integer> getAllAge();-->
<select id="getAllAge" resultType="_integer">
    select age from t_user
</select>
```

输出：
`getUserViaMap: {password=insertPassword, gender=中性, id=1, age=11, username=insertUserName}`

## 查询多条记录返回 map 列表

```java
List<Map<String, Object>> getAllUserToMap();
```

```xml
<select id="getAllUserToMap" resultType="map">
        select * from t_user
</select>
```


## 查询多条记录返回 map

```java
import org.apache.ibatis.annotations.MapKey;
@MapKey("id")
Map<String, Object> getAllUserToMapV2();
```

在 mapper 接口的方法上添加 @MapKey 注解，此时就可以将每条数据转换的 map 作为值，以注解配置的字段的值作为键，放在同一个map集合中

```xml
<select id="getAllUserToMapV2" resultType="map">
        select * from t_user
</select>
```
返回的结果：
```
{
    1={password=3231, gender=中性, id=1, age=11, username=wang},
    2={password=123456, gender=男, id=2, age=12, username=Tom},
    3={password=1234, gender=男, id=3, age=99, username=张3},
    4={password=4321, gender=男, id=4, age=98, username=李4}
}
```
# 特殊SQL
## 模糊查询
```java
/**
 * 根据用户名进行模糊查询
 */
List<User> getUserByLike(@Param("pat") String pat);
```

```xml
<!--List<User> getUserByLike(@Param("pat") String pat);-->
<select id="getUserByLike" resultType="User">
	<!--select * from t_user where username like '%${pat}%'-->  
	<!--select * from t_user where username like concat('%',#{pat},'%')-->  
	select * from t_user where username like "%"#{pat}"%"
</select>
```
其中 `select * from t_user where username like "%"#{pat}"%"` 是最常用的

## 添加功能获取自增的主键
通过 map 或者 Object 的方法均支持
```xml
<!--void insertUserByObject(User user);-->
<insert id="insertUserByObject" useGeneratedKeys="true" keyProperty="id">
	insert into t_user values (null,#{username},#{password},#{age},#{sex},#{email})
</insert>

<!--void insertUserByMap(Map<String, Object> map);-->
<insert id="insertUserByMap" useGeneratedKeys="true" keyProperty="id">
insert into t_user values (null,#{username},#{password},#{age},#{sex},#{email})
</insert>
```

```java
@Test
public void insertUserUseGeneratedKeys() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    SQLMapper mapper = sqlSession.getMapper(UserMapper.class);
    User user = new User(null, "ton", "123pw", 23, "男");
    mapper.insertUserByObject(user);
    System.out.println(user);
    //  输出：user{id=10, username='ton', password='123pw', age=23, sex='男'}，自增主键存放到了user的id属性中

    Map<String, Object> map = new HashMap<>();
    map.put("id", null);
    map.put("username", "insertUserName");
    map.put("password", "insertPassword");
    map.put("age", 11);
    map.put("gender", "中性");
    int rows = mapper.insertUserByMap(map);
    System.out.println("插入之后的 map: " + map);
    // 输出： {password=insertPassword, gender=中性, id=1, age=11, username=insertUserName}
}
```

# 多对一/一对多映射字段/懒加载
多对一/一对多的样例请见 module3


分步查询时可以实现延迟加载，但是必须在核心配置文件中设置全局配置信息（配置如下两个信息）：没有全局设置的话，默认的是立即加载；
- lazyLoadingEnabled：延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。**默认关闭**；
- aggressiveLazyLoading：当开启时，任何方法的调用都会加载该对象的所有属性。 否则，每个属性会按需加载。**默认关闭**。

在 mybatis-config.xml 中配置全局懒加载 ：
```xml
<settings>
    <!--开启延迟加载-->
    <setting name="lazyLoadingEnabled" value="true"/>
</settings>
```

fetchType：当开启了全局的延迟加载之后，可以通过该属性手动取消特定查询延迟加载的效果，
fetchType="lazy(延迟加载)|eager(立即加载)"

```xml
<resultMap id="empAndDeptByStepResultMap" type="Emp">
	<id property="eid" column="eid"></id>
	<result property="empName" column="emp_name"></result>
	<result property="age" column="age"></result>
	<result property="sex" column="sex"></result>
	<result property="email" column="email"></result>
	<association property="dept"
				 select="com.atguigu.mybatis.mapper.DeptMapper.getEmpAndDeptByStepTwo"
				 column="did"
				 fetchType="eager"></association>
</resultMap>
```

# 动态SQL
个人感觉下列总结的SQL模板标签比较有用
## if
```xml
<!--List<Emp> getEmpByCondition(Emp emp);-->
<select id="getEmpByCondition" resultType="Emp">
	select * from t_emp where 1=1
	<if test="empName != null and empName !=''">
		and emp_name = #{empName}
	</if>
	<if test="age != null and age !=''">
		and age = #{age}
	</if>
	<if test="sex != null and sex !=''">
		and sex = #{sex}
	</if>
	<if test="email != null and email !=''">
		and email = #{email}
	</if>
</select>

```
## choose、when、otherwise
```xml
<select id="getEmpByChoose" resultType="Emp">
	select * from t_emp
	<where>
		<choose>
			<when test="empName != null and empName != ''">
				emp_name = #{empName}
			</when>
			<when test="age != null and age != ''">
				age = #{age}
			</when>
			<when test="sex != null and sex != ''">
				sex = #{sex}
			</when>
			<when test="email != null and email != ''">
				email = #{email}
			</when>
			<otherwise>
				did = 1
			</otherwise>
		</choose>
	</where>
</select>
```
## foreach
**适合处理**：
- in
- 批量 insert

**属性** ：
- collection：设置要循环的数组或集合
- item：表示集合或数组中的每一个数据
- separator：设置循环体之间的分隔符，分隔符前后默认有一个空格，如,
- open：设置foreach标签中的内容的开始符
- close：设置foreach标签中的内容的结束符

```xml
<!--int deleteMoreByArray(Integer[] eids);-->
<delete id="deleteMoreByArray">
	delete from t_emp where eid in
	<foreach collection="eids" item="eid" separator="," open="(" close=")">
		#{eid}
	</foreach>
</delete>
```

测试代码：
```java
@Test
public void deleteMoreByArray() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
	int result = mapper.deleteMoreByArray(new Integer[]{6, 7, 8, 9});
	System.out.println(result);
}
```

# FAQ
## MyBatis 方法支持重载
[参考](https://blog.csdn.net/qq_28898917/article/details/103567919)


# 参考链接
- [（尚硅谷）2022 版 MyBatis 教程笔记上](https://blog.csdn.net/qq_42148002/article/details/125131983)
- [（尚硅谷）2022 版 MyBatis 教程笔记下](https://blog.csdn.net/qq_42148002/article/details/125205386)
- [【尚硅谷】【MyBatis】2022版Mybatis配套MD文档](https://www.cnblogs.com/mllt/articles/UPGuiGu_Mybatis.html)

