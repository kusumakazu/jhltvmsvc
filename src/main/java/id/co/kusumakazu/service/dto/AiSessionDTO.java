package id.co.kusumakazu.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link id.co.kusumakazu.domain.AiSession} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AiSessionDTO implements Serializable {

    private Long id;

    private String sessionName;

    private Instant sessionCreatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Instant getSessionCreatedTime() {
        return sessionCreatedTime;
    }

    public void setSessionCreatedTime(Instant sessionCreatedTime) {
        this.sessionCreatedTime = sessionCreatedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AiSessionDTO)) {
            return false;
        }

        AiSessionDTO aiSessionDTO = (AiSessionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, aiSessionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AiSessionDTO{" +
            "id=" + getId() +
            ", sessionName='" + getSessionName() + "'" +
            ", sessionCreatedTime='" + getSessionCreatedTime() + "'" +
            "}";
    }
}
