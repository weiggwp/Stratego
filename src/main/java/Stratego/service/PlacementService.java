package Stratego.service;

import Stratego.model.Placement;
import Stratego.repository.PlacementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementService {

    @Autowired
    private PlacementRepository placementRepository;

    public void addPlacement(Placement placement) {
        placementRepository.save(placement);
    }

    public void addPlacements(Iterable<Placement> placements) {
        for (Placement placement : placements)
            placementRepository.save(placement);
    }

    public List<Placement> getPlacements(long gameId) {
        List<Placement> placementList = placementRepository.findByMatchId(gameId);
        return placementList;
    }

}
