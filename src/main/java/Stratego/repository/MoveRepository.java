package Stratego.repository;

import Stratego.model.Match;
import Stratego.model.Reposition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends CrudRepository<Reposition, Integer> {


    List<Reposition> findRepositionByMatchIdOrderByTurnId(long GameID);
}
