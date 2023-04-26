package Base.Players;

import Base.Coordinates;

import java.util.Set;
import java.util.HashSet;

public class ShotResults implements ResultObserver {
    private Character[][] shotBoard;

    private Set<ResultObserver> observers = new HashSet<ResultObserver>();

    public ShotResults() {
        shotBoard = new Character[10][10];
    }

    public Character[][] getShotBoard() {
        return shotBoard;
    }

    @Override
    public void setHit(Coordinates coords) {
        shotBoard[coords.getX()][coords.getY()] = 'x';
    }
    @Override
    public void setMiss(Coordinates coords) {
        shotBoard[coords.getX()][coords.getY()] = 'o';
    }

    @Override
    public void setKill (Coordinates coords) {
        shotBoard[coords.getX()][coords.getY()] = 'k';
    }
    public void addObserver(ResultObserver observer) {
        observers.add(observer);
    }

    public void notifyHit(Coordinates coords) {
        for (ResultObserver observer : observers) {
            observer.setHit(coords);
        }
    }
    public void notifyMiss(Coordinates coords) {
        for (ResultObserver observer : observers) {
            observer.setMiss(coords);
        }
    }

    public void notifyKill(Coordinates coords) {
        for (ResultObserver observer : observers) {
            observer.setKill(coords);
        }
    }

}
