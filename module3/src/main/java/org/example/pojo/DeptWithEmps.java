package org.example.pojo;

import java.util.List;

public class DeptWithEmps {
    private Integer did;
    private String deptName;
    private List<Emp> emps;

    @Override
    public String toString() {
        return "DeptWithEmps{" +
                "did=" + did +
                ", deptName='" + deptName + '\'' +
                ", emps=" + emps +
                '}';
    }
    public void setDid(Integer did) {
        this.did = did;
    }
    public Integer getDid() {
        return did;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setEmps(List<Emp> emps) {
        this.emps = emps;
    }
    public List<Emp> getEmps() {
        return emps;
    }
}
