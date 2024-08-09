import org.apache.ibatis.session.SqlSession;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.utils.SqlSessionUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserParameterMapperTest {
    @Test
    public void testSelectByPara() throws IOException {
        /*
         * 单个字面量类型的参数
         * 多个字面量类型的参数
         */

        // 获取 session
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserById(2);  // 单个字面量类型的参数
        System.out.println("getUserById: " + user);

        /*
        insert into t_user values(null,'张3','1234',99,'男');
        insert into t_user values(null,'李4','4321',98,'男');
        * */
        user = userMapper.getUserByGenderAge("男",99);  // 多个字面量类型的参数
        System.out.println("getUserByGenderAge: " + user);

        user = userMapper.getUserByGenderAgeV2("男",98);  // 多个字面量类型的参数
        System.out.println("getUserByGenderAgeV2: " + user);
    }

    @Test
    public void testInsertUser() {
        /*
         * 测试 map 集合类型的参数
         */
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        Map<String, Object> map = new HashMap<>();
        map.put("id", null);
        map.put("username", "insertUserName");
        map.put("password", "insertPassword");
        map.put("age", 11);
        map.put("gender", "中性");
        int rows = userMapper.insertUserByMap(map);
        System.out.println("insert rows: " + rows);
        System.out.println("插入之后的 map: " + map);  //  {password=insertPassword, gender=中性, id=1, age=11, username=insertUserName}

        User user = new User(null,"insertUserByObject","123456",12,"男");
        userMapper.insertUserByObject(user);
        System.out.println("插入之后的 Object: " + user);  // User{id=2, username='insertUserByObject', password='123456', age=12, gender='男'}

        userMapper.getAllUser().forEach(System.out::println);
    }

    @Test
    public void testReturnType(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println("getCount: " + userMapper.getCount());
        System.out.println("getAllAge: " + userMapper.getAllAge());
        System.out.println("getUserViaMap: " + userMapper.getUserViaMap());
        System.out.println("getAllUserToMap: " + userMapper.getAllUserToMap());
        System.out.println("getAllUserToMapV2: " + userMapper.getAllUserToMapV2());
        /*
            {
                1={password=3231, gender=中性, id=1, age=11, username=wang},
                2={password=123456, gender=男, id=2, age=12, username=Tom},
                3={password=1234, gender=男, id=3, age=99, username=张3},
                4={password=4321, gender=男, id=4, age=98, username=李4}
            }
        */
    }
}
