package Base;

import Base.Gurkins.Gurkin;
import Base.Players.Player;
import Controller.Controller;
//import com.j256.ormlite.field.DatabaseField;
//import com.j256.ormlite.field.ForeignCollectionField;
//import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//@DatabaseTable(tableName = "Board")
public class Board implements BoardObserver{ // Board class
//    @DatabaseField(generatedId = true)
    private int id;
//    @ForeignCollectionField(columnName = "Tiles", eager = true)
    private Collection<Tile> tiles = new ArrayList<>(); // 10x10 array of tiles

//    @DatabaseField(foreign = true,foreignAutoRefresh = true,columnName = "player_id")
    private Player player;

    private Set<BoardObserver> observers = new HashSet<BoardObserver>(); // List of observers of the board

    // Initialize board
    public Board() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Tile tile = new Tile();
                tile.setX(x);
                tile.setY(y);
                tiles.add(tile);
            }
        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setId(int Id){
        this.id = id;
    }

    public Tile getTile(Coordinates a) {
        for (Tile tile : tiles) {
            if (tile.getX() == a.getX() && tile.getY() == a.getY()) {
                return tile;
            }
        }
        return null;
    }

    public Collection<Tile> getTiles () {return tiles;}

    public void setTiles(Board board) {
        this.tiles = board.getTiles();
    }

    public String attack(Coordinates a) { // Attack a tile at coordinates and return the result
        if (!getTile(a).isHit()) {
            getTile(a).hitTile();
            if (getTile(a).hasGurkin()) {
                getTile(a).getGurkin().decrementLives();
                tileHit(a);
               return "hit";
            } else {
                return "miss";
            }
        } else {
            return "noob";
        }

    }

    @Override
    public void placeGurkin(Coordinates startCoor, Direction.direction dir, Gurkin g) { // Place a gurkin on the board
        for (int i = 0; i < g.getSize(); i++) {
            if (dir.equals(Direction.direction.Horizontal)) {
                getTile(new Coordinates(startCoor.getX() + i, startCoor.getY())).setGurkin(g);
            } else {
                getTile(new Coordinates(startCoor.getX(), startCoor.getY() + i)).setGurkin(g);
            }
        }

        notifyPlacedGurkin(g, dir, startCoor);
    }

//    public void endPlacement() { // End the placement of gurkins
//        for (BoardObserver observer : observers) {
//            observer.endPlacement();
//        }
//    }

    public Board deepClone() { // Deep clone the board
        Board clone = new Board();
        clone.setTiles(this);
        clone.setPlayer(this.player);
        clone.setId(this.id);
        return clone;
    }

    private void notifyPlacedGurkin(Gurkin g, Direction.direction dir, Coordinates startCoor) {
        for (BoardObserver observer : observers) {

            observer.placeGurkin(startCoor, dir, g);
        }
    }

    public void tileHit(Coordinates a) {
        notifyTileHit(a);
    }
    private void notifyTileHit(Coordinates a) {
        for (BoardObserver observer : observers) {
            observer.tileHit(a);
        }
    }

    public void registerBoardObserver(BoardObserver observer) {
        observers.add(observer);
    }
}

