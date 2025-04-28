package market.analyses.parkour.config;

import org.springframework.jdbc.core.JdbcTemplate;

public class DataBaseTestHelper {

    private final JdbcTemplate jdbcTemplate;

    public DataBaseTestHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void resetDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE switch_price_history, switches, companies RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("""
            INSERT INTO companies (id, name_company) VALUES
            (1, 'TFortis'),
            (2, 'MASTERMANN'),
            (3, 'OSNOVO'),
            (4, 'РЕЛИОН');
        """);

        jdbcTemplate.execute("""
            INSERT INTO switches (id, id_company, title, price, poeports, sfpports, ups, controllable, is_there_a_switch) VALUES
            (1, 1, 'Catalyst 9200', 1200, 24, 4, true, true, true),
            (2, 2, 'TL-SG3428X', 400, 24, 4, false, true, true),
            (3, 3, 'CRS328-24P-4S+', 600, 24, 4, true, false, true),
            (4, 4, 'DGS-1210-28P', 300, 24, 2, false, true, true);
        """);

        jdbcTemplate.execute("""
            INSERT INTO switch_price_history (id, id_switch, new_price, change_date) VALUES
            (1, 1, 1100, '2025-04-01'),
            (2, 1, 1150, '2025-04-15'),
            (3, 2, 390, '2025-03-20'),
            (4, 3, 580, '2025-03-25');
        """);

        jdbcTemplate.execute("""
            SELECT setval('companies_id_seq', (SELECT MAX(id) FROM companies));
            SELECT setval('switches_id_seq', (SELECT MAX(id) FROM switches));
            SELECT setval('switch_price_history_id_seq', (SELECT MAX(id) FROM switch_price_history));
        """);
    }
}
