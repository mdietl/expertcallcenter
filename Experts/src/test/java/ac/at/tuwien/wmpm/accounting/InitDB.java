package ac.at.tuwien.wmpm.accounting;

import ac.at.tuwien.wmpm.domain.model.Expert;
import ac.at.tuwien.wmpm.domain.repository.ExpertRepository;
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
@SpringApplicationConfiguration(classes = ExpertsApp.class)
public class InitDB {

    @Autowired
    ExpertRepository expertRepository;

    @Before
    public void initDataBase() {

        expertRepository.delete(expertRepository.findAll());

        Expert newExpert = new Expert();
        int random = (int)(Math.random()*(10000000));
        newExpert.setEmail("dietl" + String.valueOf(random) + "@gmail.com");
        expertRepository.save(newExpert);
        newExpert.setEmail("dietl" + String.valueOf(random+10) + "@gmail.com");
        expertRepository.save(newExpert);
        newExpert.setEmail("dietl" + String.valueOf(random+20) + "@gmail.com");
        expertRepository.save(newExpert);
    }



    @Test
    public void testEntries() throws Exception {
        Assert.assertEquals(3, expertRepository.count());

    }

}
