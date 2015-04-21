package ac.at.tuwien.wmpm.experts;

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


       /* logger.info("TestService");
        //add random user
        int random = (int)(Math.random()*(10000000));
        User newUser = new User();
        newUser.setEmail("dietl" + String.valueOf(random) + "@gmail.com");
        logger.info("TestService:" + newUser.getEmail());
        userRepository.save(newUser);

        newUser = new User();
        newUser.setEmail("dietl" + String.valueOf(random+10) + "@gmail.com");
        userRepository.save(newUser);
*/


        logger.info("TestService");
        for (User u : userRepository.findAll()) {
            logger.info(u.getEmail());
        }
    }
}
