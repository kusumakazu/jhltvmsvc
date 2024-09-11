package id.co.kusumakazu.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link id.co.kusumakazu.domain.SessionMessage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SessionMessageDTO implements Serializable {

    private Long id;

    private String role;

    private String content;

    private Long aiSessionId;

    public SessionMessageDTO(Long id, String role, String content, Long aiSessionId) {
        this.id = id;
        this.role = role;
        this.content = content;
        this.aiSessionId = aiSessionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAiSessionId() {
        return aiSessionId;
    }

    public void setAiSessionId(Long aiSessionId) {
        this.aiSessionId = aiSessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionMessageDTO)) {
            return false;
        }

        SessionMessageDTO sessionMessageDTO = (SessionMessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sessionMessageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SessionMessageDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", content='" + getContent() + "'" +
            ", aiSessionId=" + getAiSessionId() +
            "}";
    }
}
