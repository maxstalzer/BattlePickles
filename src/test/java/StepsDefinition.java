import Base.*;
import Base.Gurkins.*;
import io.cucumber.java.ast.Ya;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class StepsDefinition {
    Tile t;
    Gurkin g;
    Gurkin g2;
    Character gChar;

    Turn turn;
    Player p1;
    Board p2b;
    Player p2;

    Coordinates cords;

    Boolean valid;

    Game game;

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
        p1 = new Player();
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

    @When("I shoot a tile without a gurkin on it")
    public void i_shoot_a_tile_without_a_gurkin_on_it() {
        cords = new Coordinates(0,1);
    }

    @Then("I get the string miss")
    public void i_get_the_string_miss() {

        assertEquals("miss", p2b.attack(cords));
    }

    @When("I shoot a tile I've shot before")
    public void i_shoot_a_tile_i_ve_shot_before() {
       cords = new Coordinates(0,3);
       p2b.attack(cords);
    }

    @Then("I get the string noob")
    public void i_get_the_string_noob() {
        assertEquals("noob", p2b.attack(new Coordinates(0, 3)));
    }

    @Given("a tile with a gurkin")
    public void a_tile_with_a_gurkin() {
        p2b = new Board();
        g = new Gherkin();
        p2b.getTile(new Coordinates(0,4)).setGurkin(g);
    }
    @When("I try to place another gurkin")
    public void i_try_to_place_another_gurkin() {
        g2 = new Gherkin();
        cords = new Coordinates(0,4);
        valid = cords.validCoords(Direction.direction.Horizontal, g2, p2b);
    }
    @Then("the coordinates are not valid")
    public void the_coordinates_are_not_valid() {
        assertEquals(false, valid);
    }


    @Given("a valid coordinate")
    public void a_valid_coordinate() {
        cords = new Coordinates(0,0);
    }
    @When("I try to place a yardlong")
    public void i_try_to_place_a_yardlong() {
        g = new Yardlong();
        p2b.getTile(cords).setGurkin(g);
    }
    @Then("the yardlong is placed")
    public void the_yardlong_is_placed() {
        assertEquals(g, p2b.getTile(cords).getGurkin());
    }
    @When("I try to place a Zuchinni")
    public void i_try_to_place_a_Zuchinni() {
        g = new Zuchinni();
        p2b.getTile(cords).setGurkin(g);
    }
    @Then("the Zuchinni is placed")
    public void the_Zuchinni_is_placed() {
        assertEquals(g, p2b.getTile(cords).getGurkin());
    }
    @When("I try to place a Pickle")
    public void i_try_to_place_a_Pickle() {
        g = new Pickle();
        p2b.getTile(cords).setGurkin(g);
    }
    @Then("the Pickle is placed")
    public void the_Pickle_is_placed() {
        assertEquals(g, p2b.getTile(cords).getGurkin());
    }
    @When("I try to place a Conichon")
    public void i_try_to_place_a_Conichon() {
        g = new Conichon();
        p2b.getTile(cords).setGurkin(g);
    }
    @Then("the Conichon is placed")
    public void the_Conichon_is_placed() {
        assertEquals(g, p2b.getTile(cords).getGurkin());
    }
    @When("I try to place Terrain")
    public void i_try_to_place_terrain() {
        g = new Terrain();
        p2b.getTile(cords).setGurkin(g);
    }
    @Then("the Terrain is placed")
    public void the_terrain_is_placed() {
        assertEquals(g, p2b.getTile(cords).getGurkin());
    }

    @When("I miss a shot")
    public void i_miss_a_shot() {
        cords = new Coordinates(0,0);
        p2b = new Board();
        p1.shoot(p2b, cords);
    }
    @Then("My shotResults board has the character o")
    public void my_shot_results_board_has_the_character_o() {
        int x = cords.getX();
        int y = cords.getY();
        assertEquals('o', p1.getShotResults()[x][y].charValue());
    }
    @When("I hit a gurkin")
    public void i_hit_a_gurkin() {
        cords = new Coordinates(0,0);
        p2b = new Board();
        g = new Gherkin();
        p2b.getTile(cords).setGurkin(g);
        p1.shoot(p2b, cords);
    }
    @Then("My shotResults board has the character x")
    public void my_shot_results_board_has_the_character_x() {
        int x = cords.getX();
        int y = cords.getY();
        assertEquals('x', p1.getShotResults()[x][y].charValue());
    }

    @When("i hit all points of a gurkin")
    public void i_hit_all_points_of_a_gurkin() {
        cords = new Coordinates(4,4);
        Coordinates nextCor = new Coordinates(4,5);
        g = new Conichon();
        p2b.placeGurkin(g, Direction.direction.Vertical, cords);
        p1.shoot(p2b, cords);
        p1.shoot(p2b, nextCor);
    }

    @Then("all of the gurkins coordinates on the shot result board should be k")
    public void all_of_the_gurkins_coordinates_on_the_shot_result_board_should_be_k() {
        assertEquals('k', p1.getShotResults()[4][4].charValue());
        assertEquals('k', p1.getShotResults()[4][5].charValue());
    }



    @When("I create a singleplayer game")
    public void i_create_a_singleplayer_game() {
       game = new Game(false);
    }
    @Then("I should create a new Player and AI")
    public void i_should_create_a_new_player_and_ai() {
        assertTrue(game.getPlayer1() instanceof Player);
        assertFalse(game.getMultiplayer());
        assertTrue(game.getPlayer2() instanceof AI);
    }

    @When("I create a multiplayer game")
    public void i_create_a_multiplayer_game() {
        game = new Game(true);
    }
    @Then("I should create two new players")
    public void i_should_create_two_new_players() {
        assertTrue(game.getPlayer1() instanceof Player);
        assertTrue(game.getPlayer2() instanceof Player);
        assertTrue(game.getMultiplayer());
    }

    @When("I create a new Player")
    public void i_create_a_new_player() {
        p1 = new Player();
    }
    @Then("the player should have a board and a shot results board")
    public void the_player_should_have_a_board_and_a_shot_results_board() {
        assertNotNull(p1.getShotResults());
        assertNotNull(p1.getGurkinBoard());
    }


    @Given("a Conichon")
    public void a_conichon() {
        g = new Conichon();
    }
    @When("use the conichon to string method")
    public void use_the_conichon_to_string_method() {
        gChar = g.toChar();
    }
    @Then("the character c should be returned")
    public void the_character_c_should_be_returned() {
        assertEquals('c', gChar.charValue());
    }

    @Given("a Gherkin")
    public void a_gherkin() {
        g = new Gherkin();
    }
    @When("use the Gherkin to string method")
    public void use_the_gherkin_to_string_method() {
        gChar = g.toChar();
    }
    @Then("the character g should be returned")
    public void the_character_g_should_be_returned() {
        assertEquals('g', gChar.charValue());
    }


    @Given("a Pickle")
    public void a_pickle() {
        g = new Pickle();
    }
    @When("use the Pickle to string method")
    public void use_the_pickle_to_string_method() {
        gChar = g.toChar();
    }
    @Then("the character p should be returned")
    public void the_character_p_should_be_returned() {
        assertEquals('p', gChar.charValue());
    }


    @Given("a Terrain")
    public void a_terrain() {
        g = new Terrain();
    }
    @When("use the terrain to string method")
    public void use_the_terrain_to_string_method() {
        gChar = g.toChar();
    }
    @Then("the character t should be returned")
    public void the_character_t_should_be_returned() {
        assertEquals('t', gChar.charValue());
    }

    @Given("a yardlong")
    public void a_yardlong() {
        g = new Yardlong();
    }
    @When("use the yardlong to string method")
    public void use_the_yardlong_to_string_method() {
        gChar = g.toChar();
    }
    @Then("the character y should be returned")
    public void the_character_y_should_be_returned() {
        assertEquals('y', gChar.charValue());
    }


    @Given("a Zuchinni")
    public void a_zuchinni() {
        g = new Zuchinni();
    }
    @When("use the zuzhinni to string method")
    public void use_the_zuzhinni_to_string_method() {
        gChar = g.toChar();
    }
    @Then("the character z should be returned")
    public void the_character_z_should_be_returned() {
        assertEquals('z', g.toChar().charValue());
    }

    @Given("an invalid coordinate")
    public void an_invalid_coordinate() {
        cords = new Coordinates(9,9);
    }
    @When("I try to place a yardlong incorrectly")
    public void i_try_to_place_a_yardlong_incorrectly() {
        g = new Yardlong();
        p2b = new Board();
        valid = cords.validCoords(Direction.direction.Vertical, g, p2b);
    }
    @Then("I should be told it is invalid")
    public void i_should_be_told_it_is_invalid() {
        assertFalse(valid);
    }

    @When("I update the players name")
    public void i_update_the_players_name() {
        p1.setName("Akira");
    }
    @Then("the player name should be the name")
    public void the_player_name_should_be_the_name() {
        assertEquals("Akira", p1.getName());
    }


    @Given("its player1s turn")
    public void its_player1s_turn() {
        turn = new Turn();
    }
//    @When("the player has placed all their gurkins")
//    public void the_player_has_placed_all_their_gurkins() {
//
//
//    }
//    @Then("the turn changes to player2")
//    public void the_turn_changes_to_player2() {
//        assertEquals("2", turn.getTurn());
//    }

    @Then("The turn changes")
    public void the_turn_changes() {
        assertEquals("2", turn.getTurn());
    }



}
