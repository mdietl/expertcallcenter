package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by dietl_ma on 09/05/15.
 * Updated by sadrian on 10/05/15.
 */
@Entity
public class IncomingRequest extends Message {

    @Column(length = 10000)
    private String question;

    private ArrayList<String> categories = new ArrayList<String>();

    private ArrayList<String> experts = new ArrayList<>();
    
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)

    private List<ExpertResponse> answers;

    public IncomingRequest() {super();}
    
    public IncomingRequest(UUID id) {
      super.setId(id);
    }
    
    public String getQuestion() {
        return super.getContent();
    }

    public void setQuestion(String question) {
        super.setContent(question);
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<Category> getCategoryObjects() {
        List<Category> categoryObjects = new ArrayList<Category>();
        for (String cat : categories) {
            categoryObjects.add(new Category(cat));
        }
        return categoryObjects;
    }

    public ArrayList<String> getExperts() {
        return experts;
    }

    public void setExperts(ArrayList<String> experts) {
        this.experts = experts;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category) {
        categories.add(category);
    }
    
    public List<ExpertResponse> getAnswers() {
      return answers;
    }
    
    public void setAnswers(List<ExpertResponse> answers) {
      this.answers = answers;
    }
  
    public void addAnswer(ExpertResponse answer) {
      answers.add(answer);
    }

    @Override
    public String toString() {
        return "IncomingRequest{"
                + "id="
                + super.getId()
                + ", mail='"
                + super.getMail()
                + '\''
                + ", title='"
                + super.getTitle()
                + '\''
                + ", question='"
                + super.getContent().substring(0, super.getContent().length() <= 15 ? super.getContent().length() : 15).replaceAll(
                "(\\r|\\n)", "") + '\'' + ", valid=" + super.getValid() + ", categories=" + categories + ", answers=" + answers + '}';
    }
}
