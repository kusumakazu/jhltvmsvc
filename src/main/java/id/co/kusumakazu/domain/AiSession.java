package id.co.kusumakazu.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AiSession.
 */
@Entity
@Table(name = "ai_session")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AiSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "session_name")
    private String sessionName;

    @Column(name = "session_created_time")
    private Instant sessionCreatedTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AiSession id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionName() {
        return this.sessionName;
    }

    public AiSession sessionName(String sessionName) {
        this.setSessionName(sessionName);
        return this;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Instant getSessionCreatedTime() {
        return this.sessionCreatedTime;
    }

    public AiSession sessionCreatedTime(Instant sessionCreatedTime) {
        this.setSessionCreatedTime(sessionCreatedTime);
        return this;
    }

    public void setSessionCreatedTime(Instant sessionCreatedTime) {
        this.sessionCreatedTime = sessionCreatedTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AiSession)) {
            return false;
        }
        return getId() != null && getId().equals(((AiSession) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AiSession{" +
            "id=" + getId() +
            ", sessionName='" + getSessionName() + "'" +
            ", sessionCreatedTime='" + getSessionCreatedTime() + "'" +
            "}";
    }
}
