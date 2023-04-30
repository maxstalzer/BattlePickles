package Base.Players;

import Base.Coordinates;
import Observers.ResultObserver;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@DatabaseTable(tableName = "ShotResults")
public class ShotResults implements ResultObserver {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @DatabaseField(generatedId = true)
    private int id;
    @ForeignCollectionField(columnName = "Results")
    private Collection<Result> shotCollection;
    private Character[][] shotBoard;
    public void transformation() {
        for (Result result : shotCollection) {
            shotBoard[result.getX()][result.getY()] = result.getCharacter().charAt(0);
        }
    }
    public void toShotCollection() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (shotBoard[j][i] != null) {
                    Result result = new Result();
                    result.setCharacter(Character.toString(shotBoard[j][i]));
                    result.setX(j);
                    result.setY(i);
                    result.setShotResults(this);
                    shotCollection.add(result);
                }
            }
        }
    }
    // This method also need to have the placement x and y
    //public void addShotBoard(Character cha) {this.shotCollection.add(cha);}

    public void addShotBoardCollection(Result result) {this.shotCollection.add(result);}

    private Set<ResultObserver> observers = new HashSet<ResultObserver>();

    public ShotResults() {
        shotBoard = new Character[10][10];
        shotCollection = new ArrayList<>();
    }

    public Character[][] getShotBoard() {
        return shotBoard;
    }
    public Collection<Result> getShotCollection() {
        return shotCollection;
    }
    public void setShotCollection(Collection<Result> shotCollection) {this.shotCollection = shotCollection;}

    @Override
    public void setHit(Coordinates coords) {
        shotBoard[coords.getX()][coords.getY()] = 'x';
        notifyHit(coords);
    }
    @Override
    public void setMiss(Coordinates coords) {
        shotBoard[coords.getX()][coords.getY()] = 'o';
        notifyMiss(coords);
    }

    @Override
    public void setKill (Coordinates coords) {
        shotBoard[coords.getX()][coords.getY()] = 'k';
        notifyKill(coords);
    }
    public void addObserver(ResultObserver observer) {
        observers.add(observer);
    }

    public void notifyHit(Coordinates coords) {
        for (ResultObserver observer : observers) {
            observer.setHit(coords);}}
    public void notifyMiss(Coordinates coords) {
        for (ResultObserver observer : observers) {
            observer.setMiss(coords);}}

    public void notifyKill(Coordinates coords) {
        for (ResultObserver observer : observers) {
            observer.setKill(coords);}}

    public void registerObserver(ResultObserver observer) {
        observers.add(observer);
    }
}
