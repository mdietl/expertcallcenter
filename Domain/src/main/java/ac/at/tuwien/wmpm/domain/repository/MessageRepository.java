package ac.at.tuwien.wmpm.domain.repository;

import ac.at.tuwien.wmpm.domain.model.Message;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by sadriu on 10/06/15.
 */
@Repository
@Transactional
public interface MessageRepository extends IGenericRepository<Message, String> {

  public Message findById(UUID id);
}
