package ac.at.tuwien.wmpm.monitoring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by dietl_ma on 21/04/15.
 */
@SpringBootApplication
public class MonitoringApp {

    public static void main(String[]args) {
        new SpringApplicationBuilder(MonitoringApp.class)
                .headless(false)
                .web(false)
                .run(args);
    }
}
