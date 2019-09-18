package Stratego.repository;

import Stratego.model.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

//    List<Match> findByUserId(long userId);
}
