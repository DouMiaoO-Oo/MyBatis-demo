package org.example.mapper;

import org.example.pojo.DeptWithEmps;

public interface DeptWithEmpsMapper {
    public DeptWithEmps getDeptWithEmps(Integer did);
    public DeptWithEmps getDeptWithEmpsByStep(Integer did);
}
