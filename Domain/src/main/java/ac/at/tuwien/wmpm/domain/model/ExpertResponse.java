package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.UUID;

/**
 * Created by sadrian on 09/06/15.
 */
@Entity
public class ExpertResponse extends Message {

    @Column(length = 10000)
    private String answer;
    
    @ManyToOne
    @JoinColumn(name = "question")
    private IncomingRequest question;

    public ExpertResponse() {super();}
    
    public ExpertResponse(UUID id) {
      super.setId(id);
    }
    
    public String getAnswer() {
        return super.getContent();
    }

    public void setAnswer(String answer) {
        super.setContent(answer);
    }

    public IncomingRequest getIncomingRequest() {
      return this.question;
    }
    
    public void setIncomingRequest(IncomingRequest question) {
      this.question = question;
    }
    
    @Override
    public String toString() {
        return "ExpertResponse{"
                + "id="
                + super.getId()
                + ", mail='"
                + super.getMail()
                + '\''
                + ", title='"
                + super.getTitle()
                + '\''
                + ", answer='"
                + super.getContent().substring(0, super.getContent().length() <= 15 ? super.getContent().length() : 15).replaceAll(
                "(\\r|\\n)", "") + '\'' + ", valid=" + super.getValid() + '}';
    }
}
