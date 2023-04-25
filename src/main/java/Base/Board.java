package Base;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Board")
public class Board {
    @DatabaseField(generatedId = true)
    private int id;

    @ForeignCollectionField(columnName = "tiles", eager = true)
    private Collection<Tile> tiles = new ArrayList<>();

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "player_id")
    private Player player;

    public Board(Player p) {
        this.player = p;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Tile tile = new Tile();
                tile.setX(x);
                tile.setY(y);
                tiles.add(tile);
            }
        }
    }

    public Board() {}
    // Initialize board


    public Tile getTile(Coordinates a) {
        for (Tile tile : tiles) {
            if (tile.getX() == a.getX() && tile.getY() == a.getY()) {
                return tile;
            }
        }
        return null;
    }

    public Collection<Tile> getTiles() {
        return tiles;
    }

    public String attack(Coordinates a) {
        if (!getTile(a).isHit()) {
            getTile(a).hitTile();
            if (getTile(a).hasGurkin()) {
                getTile(a).getGurkin().decrementLives();
               return "hit";
            } else {
                return "miss";
            }
        } else {
            return "noob";
        }

    }

    public void displayBoard() {
        System.out.println("+---------------------+");
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        for (int i = 0; i < 10; i++) {
            System.out.print("| ");
            for (int j = 0; j < 10; j++) {
                System.out.print( getTile(new Coordinates(i,j)).toChar() + " ");
            }
            System.out.println(" |");
        }
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        System.out.println("+---------------------+");

    }

    public void setupBoard(Gurkin g, Direction.direction dir, Coordinates startCoor) {

        for (int i = 0; i < g.getSize(); i++) {
            if (dir.equals(Direction.direction.Horizontal)) {
                getTile(new Coordinates(startCoor.getX() + i, startCoor.getY())).setGurkin(g);
            } else {
                getTile(new Coordinates(startCoor.getX(), startCoor.getY() + i)).setGurkin(g);
            }
        }
    }
}



