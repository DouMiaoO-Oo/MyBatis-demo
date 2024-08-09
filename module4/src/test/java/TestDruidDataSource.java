import org.apache.ibatis.session.SqlSession;
import org.example.mapper.EmpMapper;
import org.example.pojo.Emp;
import org.example.utils.SqlSessionUtils;
import org.junit.Test;

public class TestDruidDataSource {
    @Test
    public void testDemo(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp1 = empMapper.getEmpV1(1);
        System.out.println(emp1);
    }
}
