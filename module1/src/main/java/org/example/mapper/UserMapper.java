package org.example.mapper;

import org.example.pojo.User;

import java.util.List;

public interface UserMapper {
    /**
     * 添加用户信息
     */
    int insertUser();  // int 获取 DML 操作影响的行数
    /**
     * 更新用户信息
     */
    void updateUser();  // void 类型不获取返回的变更数量

    /**
     * 删除用户信息
     */
    int deleteUser();  // int 获取 DML 操作影响的行数

    /**
     * 获取用户信息
     */
    User getUser();

    /**
     * 获取所有用户信息
     */
    List<User> getAllUser();

}
