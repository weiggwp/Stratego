package Stratego.controller;

import Stratego.model.Placement;
import Stratego.service.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class TestController {

    @Autowired
    private PlacementService placementService;


    @GetMapping("/test")
    public void test() {

        // dummy saving
        placementService.addPlacement(new Placement(1, 2, 2, "2", 1));
        placementService.addPlacement(new Placement(1, 2, 3, "1", 1));
        placementService.addPlacement(new Placement(1, 2, 4, "3", 1));
        placementService.addPlacement(new Placement(1, 2, 5, "5", 1));
        placementService.addPlacement(new Placement(1, 2, 6, "7", 1));
        placementService.addPlacement(new Placement(1, 7, 7, "2", 0));

        // dummy retrieve
        List<Placement> placements = placementService.getPlacements(1);
        for (Placement placement: placements) {
            System.out.println(placement.toString());
        }

    }





}
