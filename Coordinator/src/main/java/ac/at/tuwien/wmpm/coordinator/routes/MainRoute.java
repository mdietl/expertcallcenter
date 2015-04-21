package ac.at.tuwien.wmpm.coordinator.routes;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Service
public class MainRoute extends RouteBuilder {
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(MainRoute.class);
    @Override
    public void configure() throws Exception {

        from("timer://foo?fixedRate=true&period=10000")
                .to("direct:audit");

        from("direct:audit")
                .setBody(constant("HELLO WORLD 123")).convertBodyTo(String.class)
                .to("direct:logme");

        from("direct:logme")
                .to("log:com.mdw360.SampleRoutes");
    }
}
