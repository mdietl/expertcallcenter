package ac.at.tuwien.wmpm.accounting;

import ac.at.tuwien.wmpm.domain.model.User;
import ac.at.tuwien.wmpm.domain.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dietl_ma on 21/04/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccountingApp.class)
public class InitDB {

    @Autowired
    UserRepository userRepository;

    @Before
    public void initDataBase() {

        userRepository.delete(userRepository.findAll());

        User newUser = new User();
        int random = (int)(Math.random()*(10000000));
        newUser.setEmail("dietl" + String.valueOf(random) + "@gmail.com");
        userRepository.save(newUser);
        newUser.setEmail("dietl" + String.valueOf(random+10) + "@gmail.com");
        userRepository.save(newUser);
        newUser.setEmail("dietl" + String.valueOf(random+20) + "@gmail.com");
        userRepository.save(newUser);
    }



    @Test
    public void testEntries() throws Exception {
        Assert.assertEquals(3, userRepository.count());

    }

}
