package Base.Players;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Result")
public class Result {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String character;
    @DatabaseField
    private int x;
    @DatabaseField
    private int y;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private ShotResults shotResults;

    public Result() {}
    public void setId(int id) {this.id=id;}
    public void setCharacter(String character) {this.character=character;}
    public void setX(int x) {this.x=x;}
    public void setY(int y) {this.y=y;}
    public void setShotResults(ShotResults shotResults) {this.shotResults = shotResults;}

    public int getId() {return this.id;}
    public String getCharacter() {return this.character;}
    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public ShotResults getShotResults() {return this.shotResults;}

}
