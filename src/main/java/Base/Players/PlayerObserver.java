package Base.Players;

import Base.Gurkins.Gurkin;

import java.util.ArrayList;

public interface PlayerObserver {
    public void updateSidePanel(ArrayList<Gurkin> gurk);

    public void finalisePlacement();
}
