package Stratego.repository;


import Stratego.model.Placement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacementRepository extends CrudRepository<Placement, Long> {

    List<Placement> findByMatchId(long matchId);
}
