package com.mission.cure.api.repository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mission.cure.api.entity.Note;


/**
 * Specifies methods used to obtain and modify note related information which
 * is stored in the database.
 */
@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Note, Long> {

	List<Note> findByUserIdOrderByDateCreated(Long userId);
}
