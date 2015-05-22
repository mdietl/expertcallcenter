package ac.at.tuwien.wmpm.experts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by dietl_ma on 21/04/15.
 */
@SpringBootApplication
@EntityScan("ac.at.tuwien.wmpm.domain.model")
@EnableJpaRepositories({"ac.at.tuwien.wmpm.domain.repository"})
public class ExpertsApp {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(ExpertsApp.class);

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ExpertsApp.class, args);
    }
}
