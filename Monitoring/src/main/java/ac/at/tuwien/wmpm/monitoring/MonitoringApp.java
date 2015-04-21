package ac.at.tuwien.wmpm.monitoring;

import ac.at.tuwien.wmpm.domain.configuration.CommonRabbitConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

/**
 * Created by dietl_ma on 21/04/15.
 */
@SpringBootApplication
@Import({CommonRabbitConfiguration.class})
public class MonitoringApp {

    public static void main(String[]args) {
        new SpringApplicationBuilder(MonitoringApp.class)
                .headless(false)
                .web(false)
                .run(args);
    }
}
