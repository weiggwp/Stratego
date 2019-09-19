package Stratego.repository;

import Stratego.model.Reposition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRepository extends CrudRepository<Reposition, Integer> {

}
