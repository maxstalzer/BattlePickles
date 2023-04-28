package Base;

import Base.Gurkins.*;
import Base.Players.Player;
import Controller.Controller;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.*;


@DatabaseTable(tableName = "Board")
public class Board implements BoardObserver{ // Board class
    @DatabaseField(generatedId = true)
    private int id;
    @ForeignCollectionField(columnName = "Tiles")
    private Collection<Tile> tiles = new ArrayList<>(); // 10x10 array of tiles

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



    public void setId(int Id){
        this.id = id;
    }
    public Integer getId() {
        return this.id;
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
    public void setTiles(Collection<Tile> t) {
        this.tiles = t;
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


    public Board deepClone() { // Deep clone the board
        Board clone = new Board();
        clone.setTiles(this);
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

    public void prepareViewGurkin() {
        Gurkin[] gurkins = new Gurkin[5];
        Coordinates[] startCoors = new Coordinates[5];
        Direction.direction[] startDirs = new Direction.direction[5];


        List<Tile> conichonList = new ArrayList<>();
        List<Tile> gherkinList = new ArrayList<>();
        List<Tile> pickleList = new ArrayList<>();
        List<Tile> yardlongList = new ArrayList<>();
        List<Tile> zuchinniList = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile.hasGurkin() && (tile.getGurkin() instanceof Conichon)) {conichonList.add(tile);}
            else if (tile.hasGurkin() && (tile.getGurkin() instanceof Gherkin)) {gherkinList.add(tile);}
            else if (tile.hasGurkin() && (tile.getGurkin() instanceof Pickle)) {pickleList.add(tile);}
            else if (tile.hasGurkin() && (tile.getGurkin() instanceof Yardlong)) {yardlongList.add(tile);}
            else if (tile.hasGurkin() && (tile.getGurkin() instanceof Zuchinni)) {zuchinniList.add(tile);}
        }
        Tile lowest;

        if (conichonList.get(0).getX() == conichonList.get(1).getX()) {
            startDirs[0] = Direction.direction.Vertical;
            lowest = conichonList.get(0);
            for (Tile tile : conichonList) {
                if (tile.getY() < lowest.getY()) {
                    lowest = tile;
                }
            }
            startCoors[0] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[0] = Direction.direction.Horizontal;
            lowest = conichonList.get(0);
            for (Tile tile : conichonList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;
                }
            }
            startCoors[0] = new Coordinates(lowest.getX(), lowest.getY());
        }
        if (gherkinList.get(0).getX() == gherkinList.get(1).getX()) {
            startDirs[1] = Direction.direction.Vertical;
            lowest = gherkinList.get(0);
            for (Tile tile : gherkinList) {
                if (tile.getY() < lowest.getY()) {
                    lowest = tile;
                }
            }
            startCoors[1] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[1] = Direction.direction.Horizontal;
            lowest = gherkinList.get(0);
            for (Tile tile : gherkinList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;
                }
            }
            startCoors[1] = new Coordinates(lowest.getX(), lowest.getY());
        }
        if (pickleList.get(0).getX() == pickleList.get(1).getX()) {
            startDirs[2] = Direction.direction.Vertical;
            lowest = pickleList.get(0);
            for (Tile tile : pickleList) {
                if (tile.getY() < lowest.getY()) {
                    lowest = tile;
                }
            }
            startCoors[2] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[2] = Direction.direction.Horizontal;
            lowest = pickleList.get(0);
            for (Tile tile : pickleList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;
                }
            }
            startCoors[2] = new Coordinates(lowest.getX(), lowest.getY());
        }
        if (yardlongList.get(0).getX() == yardlongList.get(1).getX()) {
            startDirs[3] = Direction.direction.Vertical;
            lowest = yardlongList.get(0);
            for (Tile tile : yardlongList) {
                if (tile.getY() < lowest.getY()) {
                    lowest = tile;
                }
            }
            startCoors[3] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[3] = Direction.direction.Horizontal;
            lowest = yardlongList.get(0);
            for (Tile tile : yardlongList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;
                }
            }
            startCoors[3] = new Coordinates(lowest.getX(), lowest.getY());

        }
        if (zuchinniList.get(0).getX() == zuchinniList.get(1).getX()) {
            startDirs[4] = Direction.direction.Vertical;
            lowest = zuchinniList.get(0);
            for (Tile tile : zuchinniList) {
                if (tile.getY() < lowest.getY()) {
                    lowest = tile;
                }
            }
            startCoors[4] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[4] = Direction.direction.Horizontal;
            lowest = zuchinniList.get(0);
            for (Tile tile : zuchinniList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;
                }
            }
            startCoors[4] = new Coordinates(lowest.getX(), lowest.getY());
        }
        for (int i = 0; i < 5; i++) {
            for (BoardObserver observer : observers) {
                observer.placeGurkin(startCoors[i], startDirs[i], gurkins[i]);
            }
        }
    }
}


