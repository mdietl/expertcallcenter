package ac.at.tuwien.wmpm.coordinator.processors;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import mx.bigdata.jcalais.CalaisClient;
import mx.bigdata.jcalais.CalaisObject;
import mx.bigdata.jcalais.CalaisResponse;
import mx.bigdata.jcalais.rest.CalaisRestClient;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Component
public class EnrichWithCategoriesProcessor implements Processor {

    private static final String URL = "http://api.opencalais.com/tag/rs/enrich";
    @Override
    public void process(Exchange exchange) throws Exception {

        //enrich message with tags
        IncomingRequest ir = (IncomingRequest) exchange.getIn().getBody();

        CalaisClient calaisClient = new CalaisRestClient("akdmgty85pj9d3mzv3y2fjb5");
        CalaisResponse calaisResponse = calaisClient.analyze(ir.getQuestion());

        for (CalaisObject tags : calaisResponse.getSocialTags()){
            System.out.println(tags.getField("_typeGroup") + ":"
                    + tags.getField("name"));

            ir.getCategories().add(tags.getField("name"));
        }
    }
}
