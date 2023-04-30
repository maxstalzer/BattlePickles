package Base;

import Observers.BoardObserver;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import Observers.BoardStatsObserver;

import java.util.*;


@DatabaseTable(tableName = "Board")
public class Board implements BoardObserver, BoardStatsObserver { // Board class
    @DatabaseField(generatedId = true)
    private int id;
    @ForeignCollectionField(columnName = "Tiles")
    private Collection<Tile> tiles = new ArrayList<>(); // 10x10 array of tiles
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Conichon conichon;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Gherkin gherkin;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Pickle pickle;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Yardlong yardlong;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Zuchinni zuchinni;
    @ForeignCollectionField(columnName = "FoundCoords")
    private Collection<Coordinates> foundCoords;

    public void setUpSaveDatabase() {
        for (Tile tile : tiles) {
            if (tile.hasGurkin() && (tile.getGurkin() instanceof Conichon)) {
                this.conichon = (Conichon) tile.getGurkin();
                ((Conichon) tile.getGurkin()).setBoard(this);
                //((Conichon) tile.getGurkin()).addTile(tile);
                tile.setConichon((Conichon) tile.getGurkin());

            }
            if (tile.hasGurkin() && (tile.getGurkin() instanceof Gherkin)) {
                this.gherkin = (Gherkin) tile.getGurkin();
                ((Gherkin) tile.getGurkin()).setBoard(this);
                //((Gherkin) tile.getGurkin()).addTile(tile);
                tile.setGherkin((Gherkin) tile.getGurkin());
            }
            if (tile.hasGurkin() && (tile.getGurkin() instanceof Pickle)) {
                this.pickle = (Pickle) tile.getGurkin();
                ((Pickle) tile.getGurkin()).setBoard(this);
                //((Pickle) tile.getGurkin()).addTile(tile);
                tile.setPickle((Pickle) tile.getGurkin());
            }
            if (tile.hasGurkin() && (tile.getGurkin() instanceof Yardlong)) {
                this.yardlong = (Yardlong) tile.getGurkin();
                ((Yardlong) tile.getGurkin()).setBoard(this);
                //((Yardlong) tile.getGurkin()).addTile(tile);
                tile.setYardlong((Yardlong) tile.getGurkin());
            }
            if (tile.hasGurkin() && (tile.getGurkin() instanceof Zuchinni)) {
                this.zuchinni = (Zuchinni) tile.getGurkin();
                ((Zuchinni) tile.getGurkin()).setBoard(this);
                //((Zuchinni) tile.getGurkin()).addTile(tile);
                tile.setZuchinni((Zuchinni) tile.getGurkin());
            }
        }
    }

    public Conichon getConichon() {return conichon;}
    public Gherkin getGherkin() {return gherkin;}
    public Pickle getPickle() {return pickle;}
    public Yardlong getYardlong() {return yardlong;}
    public Zuchinni getZuchinni() {return zuchinni;}


    private Set<BoardObserver> observers = new HashSet<BoardObserver>(); // List of observers of the board
    private Set<BoardStatsObserver> statsObservers = new HashSet<BoardStatsObserver>(); // List of observers of the board's stats


    private Gurkin[] gurkins;
    private Coordinates[] startCoors;
    private Direction.direction[] startDirs;



    // Initialize board
    public Board() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Tile tile = new Tile();
                tile.setBoard(this);
                tile.setX(x);
                tile.setY(y);
                tiles.add(tile);
            }
        }
        this.foundCoords = new ArrayList<>();

    }



    public void setId(int id){
        this.id = id;
    }
    public Integer getId() {
        return this.id;
    }

    public Tile getTile(Coordinates a) {
        for (Tile tile : tiles) {
            if (tile.getX() == a.getX() && tile.getY() == a.getY()) {
                return tile;}}
        return null;}

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
            tileHit(a);
            increaseTotalShots(1);
            removeCoords(a);
            if (getTile(a).hasGurkin()) {
                getTile(a).getGurkin().decrementLives();
                if (getTile(a).getGurkin() instanceof Terrain) { // If the gurkin is a terrain gurkin, it is destroyed
                    Boolean found = false;
                    for (int y = 0; y < 10; y++) {
                        for (int x = 0; x < 10; x++) {  // finds another unhit gurkin and sends coordinates to the stats observer
                            if (getTile(new Coordinates(x,y)).hasGurkin() && !getTile(new Coordinates(x,y)).isHit() && notKnown(new Coordinates(x,y))) {
                                found = true;
                                sendCoords(new Coordinates(x,y));
                                break;}}
                        if (found) {
                            break;}}}
                increaseHitTiles(1);
                return "hit";} else {
                increaseMissTiles(1);
                return "miss";}
            
        } else {
            return "noob";}

    }

    private Boolean notKnown(Coordinates coords) {
        for (Coordinates c : foundCoords) {
            if (c.getX() == coords.getX() && c.getY() == coords.getY()) {
                return false;}}
        return true;}

    @Override
    public void placeGurkin(Coordinates startCoor, Direction.direction dir, Gurkin g) { // Place a gurkin on the board
        for (int i = 0; i < g.getSize(); i++) {
            if (dir.equals(Direction.direction.Horizontal)) {
                getTile(new Coordinates(startCoor.getX() + i, startCoor.getY())).setGurkin(g);} else {
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
        for (BoardObserver observer : observers) {observer.placeGurkin(startCoor, dir, g);}}

    public void tileHit(Coordinates a) {
        notifyTileHit(a);
    }
    private void notifyTileHit(Coordinates a) {
        for (BoardObserver observer : observers) {
            observer.tileHit(a);}}

    public void registerBoardObserver(BoardObserver observer) {
        observers.add(observer);
    }


    public void prepareViewGurkin() {
        this.gurkins = new Gurkin[5];
        this.startCoors = new Coordinates[5];
        this.startDirs = new Direction.direction[5];

        List<Tile> conichonList = new ArrayList<>();
        List<Tile> gherkinList = new ArrayList<>();
        List<Tile> pickleList = new ArrayList<>();
        List<Tile> yardlongList = new ArrayList<>();
        List<Tile> zuchinniList = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile.hasGurkin() && (tile.getGurkin() instanceof Conichon)) {
                conichonList.add(tile);
                gurkins[0] = tile.getGurkin();
            }
            else if (tile.hasGurkin() && (tile.getGurkin() instanceof Gherkin)) {
                gherkinList.add(tile);
                gurkins[1] = tile.getGurkin();
            }
            else if (tile.hasGurkin() && (tile.getGurkin() instanceof Pickle)) {
                pickleList.add(tile);
                gurkins[2] = tile.getGurkin();
            }
            else if (tile.hasGurkin() && (tile.getGurkin() instanceof Yardlong)) {
                yardlongList.add(tile);
                gurkins[3] = tile.getGurkin();
            }
            else if (tile.hasGurkin() && (tile.getGurkin() instanceof Zuchinni)) {
                zuchinniList.add(tile);
                gurkins[4] = tile.getGurkin();
            }
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
                    lowest = tile;}}
            startCoors[1] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[1] = Direction.direction.Horizontal;
            lowest = gherkinList.get(0);
            for (Tile tile : gherkinList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;}}
            startCoors[1] = new Coordinates(lowest.getX(), lowest.getY());
        }
        if (pickleList.get(0).getX() == pickleList.get(1).getX()) {
            startDirs[2] = Direction.direction.Vertical;
            lowest = pickleList.get(0);
            for (Tile tile : pickleList) {
                if (tile.getY() < lowest.getY()) {
                    lowest = tile;}}
            startCoors[2] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[2] = Direction.direction.Horizontal;
            lowest = pickleList.get(0);
            for (Tile tile : pickleList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;}}
            startCoors[2] = new Coordinates(lowest.getX(), lowest.getY());
        }
        if (yardlongList.get(0).getX() == yardlongList.get(1).getX()) {
            startDirs[3] = Direction.direction.Vertical;
            lowest = yardlongList.get(0);
            for (Tile tile : yardlongList) {
                if (tile.getY() < lowest.getY()) {
                    lowest = tile;}}
            startCoors[3] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[3] = Direction.direction.Horizontal;
            lowest = yardlongList.get(0);
            for (Tile tile : yardlongList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;}}
            startCoors[3] = new Coordinates(lowest.getX(), lowest.getY());

        }
        if (zuchinniList.get(0).getX() == zuchinniList.get(1).getX()) {
            startDirs[4] = Direction.direction.Vertical;
            lowest = zuchinniList.get(0);
            for (Tile tile : zuchinniList) {
                if (tile.getY() < lowest.getY()) {
                    lowest = tile;}}
            startCoors[4] = new Coordinates(lowest.getX(), lowest.getY());
        } else {
            startDirs[4] = Direction.direction.Horizontal;
            lowest = zuchinniList.get(0);
            for (Tile tile : zuchinniList) {
                if (tile.getX() < lowest.getX()) {
                    lowest = tile;}}
            startCoors[4] = new Coordinates(lowest.getX(), lowest.getY());
        }


        for (int i = 0; i < 5; i++) {
            for (BoardObserver observer : observers) {
                observer.placeGurkin(startCoors[i], startDirs[i], gurkins[i]);}}}
    public void initTerrain() {
        // randomly place terrain object on board tiles
        Random rand = new Random();
        int numTerrain = rand.nextInt(5) + 1;
        for (int i = 0; i < numTerrain; i++) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            Coordinates coors = new Coordinates(x, y);
            if (getTile(coors).hasGurkin()) {
                i--;
            } else {
                placeTerrain(coors);}}}

    public void placeTerrain(Coordinates coors) {
        System.out.println("terrain placed at " + coors.getX() + ", " + coors.getY() + "\n");
        getTile(coors).setGurkin(new Terrain());
        notifyPlacedGurkin(new Terrain() , Direction.direction.Horizontal, coors);}

    @Override
    public void increaseHitTiles(int hit) {
        for (BoardStatsObserver o : statsObservers) {
            o.increaseHitTiles(hit);}}

    @Override
    public void increaseMissTiles(int miss) {
        for (BoardStatsObserver o : statsObservers) {o.increaseMissTiles(miss);}}

    @Override
    public void increaseTotalShots(int shots) {
        for (BoardStatsObserver o : statsObservers) {
            o.increaseTotalShots(shots);}}
    public void registerStatsObserver(BoardStatsObserver o) {
        statsObservers.add(o);
    }

    public void sendCoords(Coordinates coors) {
        foundCoords.add(coors);
        for (BoardStatsObserver observer : statsObservers) {observer.sendCoords(coors);}}

    @Override
    public void removeCoords(Coordinates coords) {
        for (Coordinates coordinates : foundCoords) {
            if( coordinates.getX() == coords.getX() && coordinates.getY() == coords.getY()) {
                for (BoardStatsObserver observer : statsObservers) {observer.removeCoords(coords);}}}}

    public Gurkin[] getplacedGurkins() {
        return gurkins;
    }

    public void setConichon(Conichon conichon1) {
    }

    public void setGherkin(Gherkin gherkin1) {
    }

    public void setPickle(Pickle pickle1) {
    }

    public void setYardlong(Yardlong yardlong1) {
    }

    public void setZuchinni(Zuchinni zuchinni1) {
    }

    public Collection<Coordinates> getFoundCoords() {return this.foundCoords;}
    public void addFoundCoords(Coordinates foundCoords) {this.foundCoords.add(foundCoords);}

    public Coordinates[] getStartCoors() {
        return startCoors;
    }

    public Direction.direction[] getStartDirs() {
        return startDirs;
    }
}