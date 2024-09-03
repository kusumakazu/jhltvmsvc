package id.co.kusumakazu.repository;

import id.co.kusumakazu.domain.AiSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AiSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AiSessionRepository extends JpaRepository<AiSession, Long> {}
