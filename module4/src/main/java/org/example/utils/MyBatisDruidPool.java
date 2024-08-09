package org.example.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class MyBatisDruidPool implements DataSourceFactory {
    private Properties properties;

    @Override
    public void setProperties(Properties props) {
        //  xml文档会将properties注入进来
        this.properties=props;
    }

    @Override
    public DataSource getDataSource() {
        //  创建druid数据源
        DruidDataSource dataSource = new DruidDataSource();
        //  从配置好的 properties 中加载配置
        dataSource.setUsername(this.properties.getProperty("username"));
        dataSource.setPassword(this.properties.getProperty("password"));
        dataSource.setUrl(this.properties.getProperty("url"));
        dataSource.setDriverClassName(this.properties.getProperty("driver"));
        dataSource.setInitialSize(Integer.parseInt(this.properties.getProperty("initialSize")));//设置初始化连接数
        dataSource.setMaxActive(Integer.parseInt(this.properties.getProperty("maxActive")));//最大活动连接数
        dataSource.setMaxWait(Integer.parseInt(this.properties.getProperty("maxWait")));//设置最大等待时间
        //  初始化连接
        try {
            dataSource.init();
        } catch (Exception e){
            e.printStackTrace();
        }
        return dataSource;
    }
}
