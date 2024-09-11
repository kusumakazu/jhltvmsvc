package id.co.kusumakazu.repository;

import id.co.kusumakazu.domain.SessionMessage;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SessionMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SessionMessageRepository extends JpaRepository<SessionMessage, Long> {
    List<SessionMessage> findAllByAiSessionId(Long id);
}
