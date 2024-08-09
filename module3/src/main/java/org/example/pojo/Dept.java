package org.example.pojo;

public class Dept {
    Integer did;
    String deptName;

    public void setDid(Integer did) {
        this.did = did;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "did=" + did +
                ", deptName='" + deptName + '\'' +
                '}';
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDid() {
        return did;
    }

    public String getDeptName() {
        return deptName;
    }
}
