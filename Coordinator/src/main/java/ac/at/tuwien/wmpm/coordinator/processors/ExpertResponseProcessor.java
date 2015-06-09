package ac.at.tuwien.wmpm.coordinator.processors;

import ac.at.tuwien.wmpm.domain.model.ExpertResponse;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import ac.at.tuwien.wmpm.domain.repository.IncomingRequestRepository;
import ac.at.tuwien.wmpm.domain.repository.UserRepository;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Component
public class ExpertResponseProcessor implements Processor {

  @Autowired
  private IncomingRequestRepository incomingRequestRepository;
  
  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ExpertResponseProcessor.class);

  @Override
  public void process(Exchange exchange) throws Exception {

    ExpertResponse er = new ExpertResponse(UUID.randomUUID());
    er.setTitle(exchange.getIn().getHeader("Subject").toString());
    er.setMail(parseFrom(exchange.getIn().getHeader("From").toString()));
    er.setAnswer(exchange.getIn().getBody(String.class));
    
    UUID uid = UUID.fromString(er.getTitle());
    IncomingRequest ir = incomingRequestRepository.findById(uid);
    er.setIncomingRequest(ir);
    
    //TODO should be checked
    er.setValid(true);

    logger.info("Expert Response processed, ER: " + er);

    exchange.getIn().setBody(er, ExpertResponse.class);
  }

  /**
   * Parse email
   * 
   * @param from
   * @return
   */
  private String parseFrom(String from) {

    Pattern p =
        Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
    Matcher matcher = p.matcher(from);
    ArrayList<String> emails = new ArrayList<String>();
    while (matcher.find()) {
      emails.add(matcher.group());
    }

    return emails.get(0);
  }
}
