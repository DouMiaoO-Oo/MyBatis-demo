import org.apache.ibatis.session.SqlSession;
import org.example.mapper.DeptWithEmpsMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.DeptWithEmps;
import org.example.pojo.Emp;
import org.example.utils.SqlSessionUtils;
import org.junit.Test;

public class ResultMapTest {

    @Test
    public void testResultMap(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        /*
            多对一映射查询
        */
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp1 = mapper.getEmpV1(1);
        System.out.println(emp1);
//        Emp{eid=1, empName='wang', age=22, sex='man', email='null', dept=Dept{did=1, deptName='业务部'}}

        Emp emp2 = mapper.getEmpV1(3);
        System.out.println(emp2);
//        Emp{eid=3, empName='king', age=23, sex='woman', email='null', dept=Dept{did=2, deptName='职能部'}}


        // 分布查询, 可以通过配置实现懒查询
        Emp emp3 = mapper.getEmpByStep(2);
        System.out.println(emp3);
//        Emp{eid=2, empName='ming', age=23, sex='man', email='null', dept=Dept{did=1, deptName='业务部'}}

        /*
            一对多映射查询
         */
        DeptWithEmpsMapper deptwithEmpsMapper = sqlSession.getMapper(DeptWithEmpsMapper.class);
        DeptWithEmps deptWithEmps = deptwithEmpsMapper.getDeptWithEmps(1);
        System.out.println("deptWithEmps: " + deptWithEmps);
        /*
            DeptWithEmps{
                did=1,
                deptName='业务部',
                emps=
                    [
                        Emp{eid=1, empName='wang', age=22, sex='man', dept=Dept{did=1, deptName='业务部'}},
                        Emp{eid=2, empName='ming', age=23, sex='man', dept=Dept{did=1, deptName='业务部'}}
                    ]
            }
         */

        // 一对多映射查询 step by step
        DeptWithEmps deptWithEmpsByStep = deptwithEmpsMapper.getDeptWithEmpsByStep(1);
        System.out.println("deptWithEmpsByStep: " + deptWithEmpsByStep);
        /*
            DeptWithEmps{
                did=1,
                deptName='业务部',
                emps=
                    [
                        Emp{eid=1, empName='null', age=22, sex='man', dept=null},
                        Emp{eid=2, empName='null', age=23, sex='man', dept=null}
                    ]
                }
         */
    }
}
