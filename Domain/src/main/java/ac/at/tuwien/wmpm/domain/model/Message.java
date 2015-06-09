package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Created by sadrian on 10/06/15.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Message {

    @Id
    @NotNull
    @Column(unique = true)
    private UUID id;

    private String mail;

    private String title;

    @Column(length = 10000)
    private String content;

    private Boolean valid = false;

    public Message() {
    }

    public Message(UUID id) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Message{"
                + "id="
                + id
                + ", mail='"
                + mail
                + '\''
                + ", title='"
                + title
                + '\''
                + ", content='"
                + content.substring(0, content.length() <= 15 ? content.length() : 15).replaceAll(
                "(\\r|\\n)", "") + '\'' + ", valid=" + valid + '}';
    }
}
