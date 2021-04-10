package io.pivotal.microservices.windpark;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * The windpark Spring configuration.
 * 
 * @author Paul Chapman
 */
@Configuration
@ComponentScan
@EntityScan("io.pivotal.microservices.windpark")
@EnableJpaRepositories("io.pivotal.microservices.windpark")
@PropertySource("classpath:db-config.properties")
public class WindparkConfiguration {

	protected Logger logger;

	public WindparkConfiguration() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource() invoked");

		// Create an in-memory H2 relational database containing some demo
		// windparks.
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/windpark_schema.sql")
				.addScript("classpath:testdb/windpark_data.sql").build();

		logger.info("dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> windpark = jdbcTemplate.queryForList("SELECT number FROM T_WINDPARK");
		logger.info("System has " + windpark.size() + " windpark");

		// Populate with random balances
		Random rand = new Random();

		for (Map<String, Object> item : windpark) {
			String number = (String) item.get("number");
			BigDecimal balance = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, RoundingMode.HALF_UP);
			jdbcTemplate.update("UPDATE T_WINDPARK SET balance = ? WHERE number = ?", balance, number);
		}

		return dataSource;
	}
}
