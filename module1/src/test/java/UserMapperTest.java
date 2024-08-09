import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserMapperTest {

    @Test
    public void testInsert() throws IOException {
        // 加载核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        // 获取 sqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 获取 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 获取 session
        SqlSession sqlSession = sqlSessionFactory.openSession();  // SqlSession openSession(boolean autoCommit);  // 可以自动提交事务
        // 获取 Mapper 接口对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 测试功能
        int rows = userMapper.insertUser();
        /* 提交事务
                mybatis-config.xml 中设置了 <transactionManager type="JDBC"/>
                openSession 没有设置  autoCommit = true, 需要手动提交
        * */
        sqlSession.commit();

        System.out.println("rows:" + rows);
    }

    @Test
    public void testDML() throws IOException {
        // DML: insert delete update
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);  //  autoCommit = true 可以自动提交事务
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        userMapper.insertUser();
        userMapper.insertUser();
        userMapper.updateUser();
        int rows = userMapper.deleteUser();
        System.out.println("delete rows:" + rows);
    }

    @Test
    public void testSelect() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);  //  autoCommit = true 可以自动提交事务
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.getUser();
        System.out.println(user);

        List<User> users = userMapper.getAllUser();
        users.forEach(System.out::println);
    }
}
