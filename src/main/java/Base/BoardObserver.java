package Base;

import Base.Gurkins.Gurkin;
import Controller.*;

public interface BoardObserver {
    public void tileHit(Coordinates coords);
    public void placeGurkin(Coordinates coords, Direction.direction direction, Gurkin gurkin);

}
