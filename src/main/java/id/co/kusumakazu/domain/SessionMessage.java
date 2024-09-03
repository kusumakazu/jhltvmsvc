package id.co.kusumakazu.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SessionMessage.
 */
@Entity
@Table(name = "session_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SessionMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "content")
    private String content;

    @Column(name = "ai_session_id")
    private Long aiSessionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SessionMessage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public SessionMessage role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return this.content;
    }

    public SessionMessage content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAiSessionId() {
        return this.aiSessionId;
    }

    public SessionMessage aiSessionId(Long aiSessionId) {
        this.setAiSessionId(aiSessionId);
        return this;
    }

    public void setAiSessionId(Long aiSessionId) {
        this.aiSessionId = aiSessionId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionMessage)) {
            return false;
        }
        return getId() != null && getId().equals(((SessionMessage) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SessionMessage{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", content='" + getContent() + "'" +
            ", aiSessionId=" + getAiSessionId() +
            "}";
    }
}
