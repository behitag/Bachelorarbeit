package HKR.MSB.config;

import HKR.MSB.repository.BelegRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BelegRepository belegRepository;

    @PostConstruct
    public void createBelegIDTrigger() {
        String sql1 = "CREATE SEQUENCE IF NOT EXISTS beleg_counter_seq START 1;";
        String sql2 = "ALTER TABLE msb3 ALTER COLUMN belegid SET DEFAULT 0;";
        String sql3 = "ALTER TABLE msb3 ADD COLUMN IF NOT EXISTS time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;";
        String sql4 = "CREATE TABLE IF NOT EXISTS var3_counter_reset_tracker(last_reset_time TEXT);";
        String sql5 = "INSERT INTO var3_counter_reset_tracker (last_reset_time) SELECT '' WHERE NOT EXISTS (SELECT 1 FROM var3_counter_reset_tracker);";
        String sql6 = "CREATE OR REPLACE FUNCTION increment_db_counter()\n" +
                        "RETURNS TRIGGER AS $$\n" +
                        "DECLARE\n" +
                        "    current_minute TEXT := TO_CHAR(NOW(), 'YYYYMMDD-HH24MI');\n" +
                        "    last_reset TEXT;\n" +
                        "BEGIN\n" +
                        "    SELECT last_reset_time INTO last_reset FROM var3_counter_reset_tracker;\n" +
                        "    IF last_reset IS DISTINCT FROM current_minute THEN\n" +
                        "        PERFORM setval('beleg_counter_seq', 1, FALSE);\n" +
                        "        UPDATE var3_counter_reset_tracker SET last_reset_time = current_minute;\n" +
                        "    END IF;\n" +
                        "    NEW.belegid := current_minute || '-' || NEXTVAL('beleg_counter_seq');\n" +
                        "    RETURN NEW;\n" +
                        "END;\n" +
                        "$$ LANGUAGE plpgsql;";
        String sql7 =
                //--- this DO is there, so only 1 trigger is opened even if we have many msb-instances(otherwise error)
                "DO $$\n" +
                        "Begin\n" +
                        "IF NOT EXISTS (\n" +
                        "SELECT 1 FROM pg_trigger WHERE tgname='trigger_increment_db_counter'\n" +
                        " ) THEN\n" +
                "CREATE TRIGGER trigger_increment_db_counter\n" +
                        "BEFORE INSERT ON msb3\n" +
                        "FOR EACH ROW\n" +
                        "EXECUTE FUNCTION increment_db_counter();\n" +
                        "END IF; \n"+
                        "END $$;";

        jdbcTemplate.execute(sql1);
        jdbcTemplate.execute(sql2);
        jdbcTemplate.execute(sql3);
        jdbcTemplate.execute(sql4);
        jdbcTemplate.execute(sql5);
        jdbcTemplate.execute(sql6);
        jdbcTemplate.execute(sql7);
    }
}