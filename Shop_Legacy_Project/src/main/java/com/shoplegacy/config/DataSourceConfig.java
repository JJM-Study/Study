//package com.shoplegacy.config;
//
//import javax.sql.DataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean
//    public DataSource manualDataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        ds.setUrl("jdbc:mysql://localhost:3306/shop_legacy?useSSL=false&serverTimezone=Asia/Seoul");
//        ds.setUsername("root");
//        ds.setPassword("7711");
//        return ds;
//    }
//}
