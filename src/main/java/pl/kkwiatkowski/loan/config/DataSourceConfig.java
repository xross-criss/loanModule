package pl.kkwiatkowski.loan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${API_MANAGER_DB_URL}")
    private String databaseUrl;
    @Value("${API_MANAGER_DB_USER_NAME}")
    private String userName;
    @Value("${API_MANAGER_DB_PASSWORD}")
    private String password;
    @Value("${API_MANAGER_DB_DRIVER}")
    private String driver;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.api-manager")
    public DataSource dataSource() {

        return DataSourceBuilder
                .create()
                .username(userName)
                .password(password)
                .url(databaseUrl)
                .driverClassName(driver)
                .build();
    }
}