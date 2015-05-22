package ac.at.tuwien.wmpm.coordinator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by dietl_ma on 21/04/15.
 */
@SpringBootApplication
@EntityScan("ac.at.tuwien.wmpm.domain.model")
@EnableJpaRepositories({"ac.at.tuwien.wmpm.domain.repository"})
@PropertySources({
        @PropertySource(value = "classpath:default.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:custom.properties", ignoreResourceNotFound = true)
})
public class CoordinatorApp extends SpringBootServletInitializer {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(CoordinatorApp.class);

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CoordinatorApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CoordinatorApp.class);
    }
}
