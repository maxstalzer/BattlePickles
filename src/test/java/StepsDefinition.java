import Base.*;
import io.cucumber.java.ast.Ya;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class StepsDefinition {
    Tile t;
    Gurkin g;

    Player p1;
    Board p2b;
    Player p2;

    Direction.direction dir;
    Coordinates startCor;

    @Given("a tile")
    public void a_tile() {
        t = new Tile();
    }
    @When("set a gurkin on the tile")
    public void set_a_gurkin_on_the_tile() {
        Coordinates start_pos = new Coordinates(0, 0);
        g = new Yardlong();
        t.setGurkin(g);
    }
    @Then("the gurkin is on the tile")
    public void the_gurkin_is_on_the_tile() {
        assertEquals(g, t.getGurkin());
    }

    @Given("a player")
    public void a_player() {
        p1 = new Player("Akira");
    }
    @Given("a board")
    public void a_board() {
       p2b = new Board();
    }
    @When("I shoot a tile with a gurkin on it")
    public void i_shoot_a_tile_with_a_gurkin_on_it() {
        g = new Gherkin();
        p2b.getTile(new Coordinates(0, 0)).setGurkin(g);
    }
    @Then("I get the string hit")
    public void i_get_the_string_hit() {

        assertEquals("hit", p2b.attack(new Coordinates(0, 0)));
    }

    @When("I shoot a tile with no gurkin on")
    public void i_shoot_a_tile_with_no_gurkin_on() {
       t =  p2b.getTile(new Coordinates(0, 1));
    }
    @Then("I get the string miss")
    public void i_get_the_string_miss() {
        assertEquals("miss", p2b.attack(new Coordinates(0, 1)));
    }

    @Given("a starting Coordinate")
    public void a_starting_coordinate() {
      startCor = new Coordinates(0, 0);
    }
    @Given("a gurkin")
    public void a_gurkin() {
        g = new Yardlong();
    }
    @Given("the direction is horizontal")
    public void the_direction_is_horizontal() {
        dir = Direction.direction.Horizontal;
    }
    @When("I place the gurkin")
    public void i_place_the_gurkin() {
        p2b.setupBoard(g, dir, startCor);
    }
    @Then("the gurkin is placed horizontally")
    public void the_gurkin_is_placed_horizontally() {
        assertEquals(g, p2b.getTile(new Coordinates(1, 0)).getGurkin());
    }

    @Given("the direction is Vertical")
    public void the_direction_is_vertical() {
        dir = Direction.direction.Vertical;
    }
    @Then("the gurkin is placed Vertically")
    public void the_gurkin_is_placed_vertically() {
        assertEquals(g, p2b.getTile(new Coordinates(0,1)).getGurkin());
    }


}
