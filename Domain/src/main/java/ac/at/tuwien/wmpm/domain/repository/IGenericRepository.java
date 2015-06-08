package ac.at.tuwien.wmpm.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by dietl_ma on 21/04/15. The Interface IGenericRepository.
 *
 * @param <E> the element type
 * @param <K> the key type
 */
@NoRepositoryBean
public interface IGenericRepository<E, K extends Serializable> extends CrudRepository<E, K> {

}
