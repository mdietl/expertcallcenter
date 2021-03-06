package ac.at.tuwien.wmpm.accounting;

import ac.at.tuwien.wmpm.domain.model.User;
import ac.at.tuwien.wmpm.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Service
public class TestService {

  @Autowired
  private UserRepository userRepository;

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(TestService.class);

  @PostConstruct
  private void onPostConstruct() {
    logger.info("Accounting TestService...");
    for (User u : userRepository.findAll()) {
      logger.info(u.getEmail());
    }
  }
}
