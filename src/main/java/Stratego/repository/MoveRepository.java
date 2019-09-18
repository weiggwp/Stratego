package Stratego.repository;

import Stratego.model.Move;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRepository extends CrudRepository<Move, Integer> {

}
