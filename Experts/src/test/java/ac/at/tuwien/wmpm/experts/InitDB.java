package ac.at.tuwien.wmpm.experts;

import ac.at.tuwien.wmpm.domain.model.Category;
import ac.at.tuwien.wmpm.domain.model.Expert;
import ac.at.tuwien.wmpm.domain.repository.CategoryRepository;
import ac.at.tuwien.wmpm.domain.repository.ExpertRepository;
import ac.at.tuwien.wmpm.experts.ExpertsApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dietl_ma on 21/04/15.
 * Edited by sadrian on 21/05/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExpertsApp.class)
public class InitDB {

  @Autowired
  ExpertRepository expertRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Before
  @Transactional
  public void initDataBase() {

    expertRepository.delete(expertRepository.findAll());
    categoryRepository.delete(categoryRepository.findAll());

    // add test categories
    Category categoryTesting = new Category();
    categoryTesting.setName("Testing");
    categoryRepository.save(categoryTesting);

    Category categoryDataBase = new Category();
    categoryDataBase.setName("Data base");
    categoryRepository.save(categoryDataBase);

    Category categoryProgramming = new Category();
    categoryProgramming.setName("Programming");
    categoryRepository.save(categoryProgramming);

    Category categoryFramework = new Category();
    categoryFramework.setName("Framework");
    categoryRepository.save(categoryFramework);

    Category categoryWorkflow = new Category();
    categoryWorkflow.setName("Workflow");
    categoryRepository.save(categoryWorkflow);

    Category categoryMathematics = new Category();
    categoryMathematics.setName("Mathematics");
    categoryRepository.save(categoryMathematics);

    Category categoryAbstractAlgebra = new Category();
    categoryAbstractAlgebra.setName("Abstract algebra");
    categoryRepository.save(categoryAbstractAlgebra);

    Category categoryIdempotence = new Category();
    categoryIdempotence.setName("Idempotence");
    categoryRepository.save(categoryIdempotence);

    Category categoryTheoreticalComputerScience = new Category();
    categoryTheoreticalComputerScience.setName("Theoretical computer science");
    categoryRepository.save(categoryTheoreticalComputerScience);

    Category categoryCamel = new Category();
    categoryCamel.setName("Camel");
    categoryRepository.save(categoryCamel);

    Category categoryTechnology_Internet = new Category();
    categoryTechnology_Internet.setName("Technology_Internet");
    categoryRepository.save(categoryTechnology_Internet);

    // add test experts
    Expert martin = new Expert();
    martin.setEmail("mdietl83@gmail.com");
    martin.getCategories().add(categoryProgramming);
    martin.getCategories().add(categoryFramework);
    martin.getCategories().add(categoryDataBase);
    martin.getCategories().add(categoryWorkflow);
    martin.getCategories().add(categoryCamel);
    expertRepository.save(martin);

    Expert anna = new Expert();
    anna.setEmail("anna.sadriu@gmail.com");
    anna.getCategories().add(categoryProgramming);
    anna.getCategories().add(categoryTesting);
    anna.getCategories().add(categoryDataBase);
    anna.getCategories().add(categoryMathematics);
    expertRepository.save(anna);

    Expert georg = new Expert();
    georg.setEmail("georg.wmpm.test@gmail.com");
    georg.getCategories().add(categoryProgramming);
    georg.getCategories().add(categoryWorkflow);
    georg.getCategories().add(categoryFramework);
    georg.getCategories().add(categoryTechnology_Internet);
    georg.getCategories().add(categoryTheoreticalComputerScience);
    expertRepository.save(georg);

    Expert clemens = new Expert();
    clemens.setEmail("e0727973@student.tuwien.ac.at");
    clemens.getCategories().add(categoryTesting);
    clemens.getCategories().add(categoryProgramming);
    clemens.getCategories().add(categoryDataBase);
    clemens.getCategories().add(categoryIdempotence);
    expertRepository.save(clemens);

    Expert boris = new Expert();
    boris.setEmail("e1326205@student.tuwien.ac.at");
    boris.getCategories().add(categoryTesting);
    boris.getCategories().add(categoryProgramming);
    boris.getCategories().add(categoryWorkflow);
    boris.getCategories().add(categoryAbstractAlgebra);
    expertRepository.save(boris);
  }

  @Test
  public void testEntries() throws Exception {
    Assert.assertEquals(11, categoryRepository.count());
    Assert.assertEquals(5, expertRepository.count());

      Category categoryTesting = categoryRepository.findByName("Testing");

      List<Category> l = new ArrayList<>();
      l.add(categoryTesting);
      List<Expert> le =  expertRepository.findByCategoriesIn(l);
      for (Expert e : le) {
          System.out.println("FOUND: " + e.getEmail());
      }
  }
}
