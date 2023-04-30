package Observers;

import Base.Gurkin;

import java.util.ArrayList;

public interface PlayerObserver {
    public void updateSidePanel(ArrayList<Gurkin> gurk);

    public void finalisePlacement();


}
