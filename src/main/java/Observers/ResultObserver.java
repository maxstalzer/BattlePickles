package Observers;

import Base.Coordinates;

public interface ResultObserver {
    public void setHit(Coordinates coords);

    public void setMiss(Coordinates coords);

    public void setKill(Coordinates coords);



}
