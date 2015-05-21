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

/**
 * Created by dietl_ma on 21/04/15.
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

        //add test categories
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
        
        //add test experts
        Expert martin = new Expert();
        martin.setEmail("mdietl83@gmail.com");
        martin.getCategories().add(categoryProgramming);
        martin.getCategories().add(categoryFramework);
        martin.getCategories().add(categoryDataBase);
        martin.getCategories().add(categoryWorkflow);
        expertRepository.save(martin);

        Expert anna = new Expert();
        anna.setEmail("anna.sadriu@gmail.com");
        anna.getCategories().add(categoryProgramming);
        anna.getCategories().add(categoryTesting);
        anna.getCategories().add(categoryDataBase);
        expertRepository.save(anna);

        Expert georg = new Expert();
        georg.setEmail("e0726622@student.tuwien.ac.at");
        georg.getCategories().add(categoryProgramming);
        georg.getCategories().add(categoryWorkflow);
        georg.getCategories().add(categoryFramework);
        expertRepository.save(georg);
        
        Expert clemens = new Expert();
        clemens.setEmail("e0727973@student.tuwien.ac.at");
        clemens.getCategories().add(categoryTesting);
        clemens.getCategories().add(categoryProgramming);
        clemens.getCategories().add(categoryDataBase);
        expertRepository.save(clemens);
        
        Expert boris = new Expert();
        boris.setEmail("e1326205@student.tuwien.ac.at");
        boris.getCategories().add(categoryTesting);
        boris.getCategories().add(categoryProgramming);
        boris.getCategories().add(categoryWorkflow);
        expertRepository.save(boris);
    }

    @Test
    public void testEntries() throws Exception {
      Assert.assertEquals(5, categoryRepository.count());
      Assert.assertEquals(5, expertRepository.count());
    }
}
