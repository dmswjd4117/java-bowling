package qna.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import qna.domain.entity.Answer;
import qna.domain.entity.Question;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionAndDeletedFalse(Question question);

    Optional<Answer> findByIdAndDeletedFalse(Long id);
}