package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Entity
public class IncomingRequest {

    @Id
    @NotNull
    @Column(unique = true)
    private UUID id;

    private String mail;

    private String title;

    private String question;

    private Boolean valid = false;

    private ArrayList<String> categories = new ArrayList<String>();


    public IncomingRequest() {}

    public IncomingRequest(UUID id) {
        this.id = id;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "IncomingRequest{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", title='" + title + '\'' +
                ", question='" + question.substring(0, question.length() <= 15 ? question.length() : 15).replaceAll("(\\r|\\n)", "") + '\'' +
                '}';
    }
}