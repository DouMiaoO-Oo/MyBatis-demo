package org.example.mapper;

import org.example.pojo.Emp;

import java.util.List;

public interface EmpMapper {
    public Emp getEmpV1(Integer eid);
    public Emp getEmpV2(Integer eid);

    public Emp getEmpByStep(Integer eid);  // 分布 (step by step/懒查询) 查询

    public List<Emp> getEmps(Integer did);  // 根据 did 查询
}
