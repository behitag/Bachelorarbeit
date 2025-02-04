package HKR.MSA.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createTimestampTrigger() {

        String sql = "ALTER TABLE msa ADD COLUMN IF NOT EXISTS time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;";
        jdbcTemplate.execute(sql);
    }
}
