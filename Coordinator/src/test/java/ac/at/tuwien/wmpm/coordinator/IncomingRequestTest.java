package ac.at.tuwien.wmpm.coordinator;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

/**
 * Created by dietl_ma on 09/05/15.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = CamelSpringDelegatingTestContextLoader.class
)
@MockEndpoints
public class IncomingRequestTest {

    @Produce(uri = "direct:incomingRequest")
    protected ProducerTemplate testProducer;

    @Test
    public void testRequest() throws InterruptedException {

        IncomingRequest ir = new IncomingRequest(UUID.randomUUID());
        ir.setTitle("Test title");
        ir.setMail("mdietl83@gmail.com");
        ir.setQuestion("my question");
        testProducer.sendBody(ir);
    }
}
