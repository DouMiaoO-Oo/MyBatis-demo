package org.example.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    /**
     * 添加用户信息
     */
    int insertUserByMap(Map<String, Object> map);  // int 获取 DML 操作影响的行数
    int insertUserByObject(User user);  // int 获取 DML 操作影响的行数

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
    User getUserById(Integer id);
    User getUserByGenderAge(String gender, Integer age);
    User getUserByGenderAgeV2(@Param("xb") String gender, @Param("nl")Integer age);
    List<User> getAllUser();
    Integer getCount();  // 测试返回单个 scalar
    List<Integer> getAllAge();  // 测试返回 scalar 列表
    Map<String, Object> getUserViaMap();  // 测试返回 map
    List<Map<String, Object>> getAllUserToMap();
    @MapKey("id")
    Map<String, Object> getAllUserToMapV2();

}
