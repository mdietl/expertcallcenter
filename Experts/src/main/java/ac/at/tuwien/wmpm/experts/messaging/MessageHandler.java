package ac.at.tuwien.wmpm.experts.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Service
public class MessageHandler {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

  public void handleMessage(Object message) {
    logger.info("GOT MESSAGE:" + message.toString());
  }
}
