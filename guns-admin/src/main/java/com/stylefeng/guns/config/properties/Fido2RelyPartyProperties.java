package com.stylefeng.guns.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author:shengquan
 * @Date:2019/4/25 16:00
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = Fido2RelyPartyProperties.FIDO2_RELYPARTY_DATASOURCE)
public class Fido2RelyPartyProperties {

    public static final String FIDO2_RELYPARTY_DATASOURCE = "spring.muti-rp-datasource";
    // 多数据源的数据库账号
    private String username = "root";

    /*开启多数据源模式，不需要在此java文件中配置数据库*/
    // 测试环境：
    private String url = "jdbc:mysql://localhost:3306/fido_rest?autoReconnect=true&useUnicode=true" +
            "&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    private String password = "1234";

    // 生产环境：多数据源的链接
    //private String url =
    //"jdbc:mysql://rm-bp1wcewx09y9laon3ho.mysql.rds.aliyuncs.com:3306/guns_flowable?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false";
    //private String password = "DB@whoareyou&224610";

    public void config(DruidDataSource dataSource) {
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }
}
