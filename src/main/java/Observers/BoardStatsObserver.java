package Observers;

import Base.Coordinates;

public interface BoardStatsObserver {
    public void sendCoords(Coordinates coords);
    public void removeCoords(Coordinates coords);
    public void increaseHitTiles(int hits);
    public void increaseMissTiles(int misses);
    public void increaseTotalShots(int shots);
}
