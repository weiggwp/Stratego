package Stratego.repository;

import Stratego.model.Match;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    /* pull matches sorted by date. used in history */
    List<Match> findByUserIdOrderByDateDesc(long userId);


}
