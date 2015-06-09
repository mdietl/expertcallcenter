package ac.at.tuwien.wmpm.experts;

import ac.at.tuwien.wmpm.domain.model.Category;
import ac.at.tuwien.wmpm.domain.model.Expert;
import ac.at.tuwien.wmpm.domain.repository.CategoryRepository;
import ac.at.tuwien.wmpm.domain.repository.ExpertRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Service
public class TestService {

  @Autowired
  private ExpertRepository expertRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  private RabbitTemplate rabbitTemplate;
  
  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(TestService.class);

  @PostConstruct
  private void onPostConstruct() {
    //TODO only test data - should be deleted

    Category categoryTesting = new Category();
    categoryTesting.setName("Testing");
    categoryRepository.save(categoryTesting);

    Category categoryDataBase = new Category();
    categoryDataBase.setName("Data base");
    categoryRepository.save(categoryDataBase);

    Category categoryProgramming = new Category();
    categoryProgramming.setName("Programming");
    categoryRepository.save(categoryProgramming);

    Category categoryMathematics = new Category();
    categoryMathematics.setName("Mathematics");
    categoryRepository.save(categoryMathematics);
    
    Expert anna = new Expert();
    anna.setEmail("anna.sadriu@gmail.com");
    anna.getCategories().add(categoryProgramming);
    anna.getCategories().add(categoryTesting);
    anna.getCategories().add(categoryDataBase);
    anna.getCategories().add(categoryMathematics);
    expertRepository.save(anna);
    
    Expert sapolhei = new Expert();
    sapolhei.setEmail("sapolhei@hotmail.com");
    sapolhei.getCategories().add(categoryProgramming);
    sapolhei.getCategories().add(categoryTesting);
    sapolhei.getCategories().add(categoryDataBase);
    sapolhei.getCategories().add(categoryMathematics);
    expertRepository.save(sapolhei);
    
    logger.info("Experts TestService...");
    for (Expert e : expertRepository.findAll()) {
      logger.info(e.getEmail());
    }
  }
}
