package Stratego.util;

import Stratego.model.Placement;
import Stratego.model.Reposition;

import java.util.List;

public class ReplayObject {

    private List<Reposition> movesList;
    private List<Placement> placementList;

    public ReplayObject(List<Reposition> movesList, List<Placement> placementList) {
        this.movesList = movesList;
        this.placementList = placementList;
    }

    public List<Reposition> getMovesList() {

        return movesList;
    }

    public void setMovesList(List<Reposition> movesList) {
        this.movesList = movesList;
    }

    public List<Placement> getPlacementList() {
        return placementList;
    }

    public void setPlacementList(List<Placement> placementList) {
        this.placementList = placementList;
    }
}
