import Base.*;
import Base.AI;
import Base.Player;
import Base.Database;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;
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

    Database database;

    Game loadedGame;

    Character c;

    ShotResults shotResults;

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
        valid = cords.validCoords(Horizontal, g2, p2b);
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
        p2b.placeGurkin(cords, Direction.direction.Vertical, g);
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
       game.getAIPlayer().setDifficulty(String.valueOf(AI.Difficulty.Hard));
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

    @Given("a game")
    public void a_game() {
        game = new Game(true);
    }

    @Given("a game with all gurkins placed")
    public void a_game_with_all_gurkins_placed() {
        game = new Game(true);
        game.getPlayer1().validGurkinSetup(new Gherkin(), Horizontal, new Coordinates(0, 0));
        game.getPlayer1().validGurkinSetup(new Conichon(), Horizontal, new Coordinates(0, 1));
        game.getPlayer1().validGurkinSetup(new Yardlong(), Horizontal, new Coordinates(0, 2));
        game.getPlayer1().validGurkinSetup(new Zuchinni(), Horizontal, new Coordinates(0, 3));
        game.getPlayer1().validGurkinSetup(new Pickle(), Horizontal, new Coordinates(0, 4));

        game.getPlayer2().validGurkinSetup(new Gherkin(), Horizontal, new Coordinates(0, 0));
        game.getPlayer2().validGurkinSetup(new Conichon(), Horizontal, new Coordinates(0, 1));
        game.getPlayer2().validGurkinSetup(new Yardlong(), Horizontal, new Coordinates(0, 2));
        game.getPlayer2().validGurkinSetup(new Zuchinni(), Horizontal, new Coordinates(0, 3));
        game.getPlayer2().validGurkinSetup(new Pickle(), Horizontal, new Coordinates(0, 4));
    }

    @Given("all {int} gurkins have been placed")
    public void all_gurkins_have_been_placed(Integer int1) {

        game.getPlayer1().validGurkinSetup(new Gherkin(), Horizontal, new Coordinates(0, 0));
        game.getPlayer1().validGurkinSetup(new Conichon(), Horizontal, new Coordinates(0, 1));
        game.getPlayer1().validGurkinSetup(new Yardlong(), Horizontal, new Coordinates(0, 2));
        game.getPlayer1().validGurkinSetup(new Zuchinni(), Horizontal, new Coordinates(0, 3));
        game.getPlayer1().validGurkinSetup(new Pickle(), Horizontal, new Coordinates(0, 4));
    }
    @Then("The turn changes")
    public void The_turn_changes() {
        assertEquals("2", Turn.getTurn());
    }

    @Given("its player1s turn")
    public void its_player1s_turn() {
    }
    @When("I shoot a tile that has a gurkin and has not been shot before")
    public void i_shoot_a_tile_that_has_a_gurkin_and_has_not_been_shot_before() {
        cords = new Coordinates(0,0);
        game.getPlayer2().validGurkinSetup(new Conichon(), Horizontal, cords);
        game.getPlayer1().shoot(game.getPlayer2().getGurkinBoard(), cords);
    }
    @Then("the shot result x is on that coordinate")
    public void the_shot_result_x_is_on_that_coordinate() {
        assertEquals('x', game.getPlayer1().getShotResults()[cords.getX()][cords.getY()].charValue());
    }
    @Then("the turn is changed")
    public void the_turn_is_changed() {
        assertEquals("2", Turn.getTurn());
    }
    @Then("the gurkins lives has decreased")
    public void the_gurkins_lives_has_decreased() {
        int expected_lives = game.getPlayer2().getGurkinBoard().getTile(cords).getGurkin().getSize()-1;
        int remaining_lives = game.getPlayer2().getGurkinBoard().getTile(cords).getGurkin().getLives();
        assertEquals(expected_lives, remaining_lives);
    }

    @When("I shoot a tile that doesnt have a gurkin on it and has not been shot before")
    public void i_shoot_a_tile_that_doesnt_have_a_gurkin_on_it_and_has_not_been_shot_before() {
        cords = new Coordinates(0,0);
        game.getPlayer1().shoot(game.getPlayer2().getGurkinBoard(), cords);
    }
    @Then("the shot result is o on that coordinate")
    public void the_shot_result_is_o_on_that_coordinate() {
        assertEquals('o', game.getPlayer1().getShotResults()[cords.getX()][cords.getY()].charValue());
    }
    @Then("the turn is not changed")
    public void the_turn_is_not_changed() {
        assertEquals("1", Turn.getTurn());
    }
    @When("I shoot a tile that i hit again")
    public void i_shoot_a_tile_that_I_hit_again() {
        Turn.changeTurn();
        game.getPlayer1().shoot(game.getPlayer2().getGurkinBoard(), cords);
    }

    @Then("the gurkins life has only decreased by {int}")
    public void the_gurkins_life_has_only_decreased_by(Integer int1) {
        int expected_lives = game.getPlayer2().getGurkinBoard().getTile(cords).getGurkin().getSize()-1;
        int remaining_lives = game.getPlayer2().getGurkinBoard().getTile(cords).getGurkin().getLives();
        assertEquals(expected_lives, remaining_lives);
    }

    @When("I shoot all tiles of that gurkin")
    public void i_shoot_all_tiles_of_that_gurkin() {
        cords = new Coordinates(0,0);
        game.getPlayer2().validGurkinSetup(new Conichon(), Horizontal, cords);
        game.getPlayer1().shoot(game.getPlayer2().getGurkinBoard(), cords);
        Turn.changeTurn();
        game.getPlayer1().shoot(game.getPlayer2().getGurkinBoard(), new Coordinates(1,0));
    }
    @Then("the shot result is k on those coordinates")
    public void the_shot_result_is_k_on_those_coordinates() {
        assertEquals('k', game.getPlayer1().getShotResults()[0][0].charValue());
        assertEquals('k', game.getPlayer1().getShotResults()[1][0].charValue());
    }
    @Then("the gurkin has no lives")
    public void the_gurkin_has_no_lives() {
       assertEquals(0, game.getPlayer2().getGurkinBoard().getTile(cords).getGurkin().getLives());
    }

    @Given("player1 has to kill the last gurkin")
    public void player1_has_to_kill_the_last_gurkin() {
        game.getPlayer1().setRemaining_gurkins(1);
    }
    @Then("then player1 wins")
    public void then_player1_wins() {
        Assert.assertTrue(game.getPlayer1().checkWin());
    }

    @Given("its player2s turn")
    public void its_player2s_turn() {
        Turn.changeTurn();
    }
    @Then("player1 does not win")
    public void player1_does_not_win() {
        Assert.assertFalse(game.getPlayer1().checkWin());
    }

    @Given("a singleplayer game")
    public void a_singleplayer_game() {
        game = new Game(false);
        game.getAIPlayer().setDifficulty(AI.Difficulty.Easy, game.getPlayer1());
    }
    @When("I set the difficulty of the AI to easy")
    public void i_set_the_difficulty_of_the_ai_to_easy() {
        game.getAIPlayer().setDifficulty(AI.Difficulty.Easy, game.getPlayer1());
    }
    @Then("the difficulty of the AI should be easy")
    public void the_difficulty_of_the_ai_should_be_easy() {
       assertTrue(game.getAIPlayer().getDifficulty().equals(AI.Difficulty.Easy) );
    }

    @Then("the board should be randomly generated")
    public void the_board_should_be_randomly_generated() {
        assertEquals(5,game.getPlayer2().getRemaining_gurkins());
    }

    @Given("an Easy AI")
    public void an_easy_ai() {
        game.getAIPlayer().setDifficulty(AI.Difficulty.Easy, game.getPlayer1());
    }
    @When("the AI shoots")
    public void the_ai_shoots() {
        cords = game.getAIPlayer().generateAttack();
        game.getAIPlayer().shoot(game.getPlayer1().getGurkinBoard(), cords);
    }
    @Then("the AI should shoot at a random location")
    public void the_ai_should_shoot_at_a_random_location() {
        assertTrue(game.getPlayer1().getGurkinBoard().getTile(cords).isHit());
        assertNotNull(game.getAIPlayer().getShotResults()[cords.getX()][cords.getY()]);
    }

    @Given("a Medium AI")
    public void a_medium_ai() {
        game.getAIPlayer().setDifficulty(AI.Difficulty.Medium, game.getPlayer1());
    }

    @Given("a board with all gurkins placed")
    public void a_board_with_all_gurkins_placed() {
        p2b = game.getAIPlayer().getGurkinBoard().deepClone();
    }
    @When("the AI shoots again")
    public void the_ai_shoots_again() {
        for (int i = 0; i < 100; i++) {
            cords = game.getAIPlayer().generateAttack();
            game.getAIPlayer().shoot(p2b, cords);
        }

    }

    @Then("there should be no gurkins left")
    public void there_should_be_no_gurkins_left() {
        assertEquals(0, game.getAIPlayer().getRemaining_gurkins());
    }
    @Then("all tiles should be hit")
    public void all_tiles_should_be_hit() {
        boolean result = true;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(game.getAIPlayer().getShotResults()[j][i].charValue() == 'o' || game.getAIPlayer().getShotResults()[j][i].charValue() == 'k');
                result = result && p2b.getTile(new Coordinates(j,i)).isHit();
            }
        }
        assertTrue(result);
    }

    @Then("the AI should have won the game")
    public void the_ai_should_have_won_the_game() {
        assertTrue(game.getAIPlayer().checkWin());
    }

    @Given("a database")
    public void a_database() throws Exception {
        database = new Database("tester");
    }

    @When("I save a game")
    public void i_save_a_game() throws Exception {
        game.getPlayer1().setName("testPlayer1");
        game.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).setGurkin(new Pickle());
        database.saveGame(game);
    }

    @Then("I should be able to see it in the list of available saves")
    public void i_should_be_able_to_see_it_in_the_list_of_available_saves() throws Exception {
        String nameOf = "notTest";
        Database database2 = new Database();
        for (String name : database2.getDatabases()) {
            if (name.equals("tester")) {
                nameOf = name;
            }
        }
        assertEquals("tester",nameOf);
    }
    @Then("I should be able to load the game from the database")
    public void i_should_be_able_to_load_the_game_from_the_database() throws Exception {
        Game loadedGame = database.loadGame("tester");
        loadedGame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).hitTile();
        assertEquals(game.getMultiplayer(), loadedGame.getMultiplayer());
        assertEquals(game.getPlayer1().getName(), loadedGame.getPlayer1().getName());
        assertEquals(game.getPlayer2().getName(), loadedGame.getPlayer2().getName());
        assertEquals(game.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives(), loadedGame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives());
        database.deleteDatabase("tester");
    }

    @When("my turn is over")
    public void my_turn_is_over() {
        game.getPlayer1().setCurrentPlayer(false);
        game.getPlayer2().setCurrentPlayer(true);
    }
    @Then("i should be able to upload my game to the database")
    public void i_should_be_able_to_upload_my_game_to_the_database() throws Exception {
        Database database1 = new Database("yoyo");
        database1.saveGame(game);
        Game gametest = database1.loadGame("yoyo");
        assertEquals(game,gametest);
        database1.deleteDatabase("yoyo");
    }

    @When("I get the char value")
    public void i_get_the_char_value() {
        c = g.toChar();
    }
    @Then("the char value should be y")
    public void the_char_value_should_be_y() {
        assertEquals('y', c.charValue());
    }
    @Then("the char value should be c")
    public void the_char_value_should_be_c() {
        assertEquals('c', c.charValue());
    }
    @Then("the char value should be g")
    public void the_char_value_should_be_g() {
        assertEquals('g', c.charValue());
    }
    @Then("the char value should be p")
    public void the_char_value_should_be_p() {
        assertEquals('p', c.charValue());
    }
    @Then("the char value should be z")
    public void the_char_value_should_be_z() {
        assertEquals('z', c.charValue());
    }

    @When("I initialise the terrain")
    public void i_initialise_the_terrain() {
        game = new Game(false);
        game.initTerrain();
    }
    @Then("the terrain should be initialised")
    public void the_terrain_should_be_initialised() {
        Boolean hasTerrain1 = false;
        Boolean hasTerrain2 = false;

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Board p1Board = game.getPlayer1().getGurkinBoard();
                Board p2Board = game.getPlayer2().getGurkinBoard();
                System.out.println(p1Board.getTile(new Coordinates(x,y)).getGurkin());
                System.out.println(p2Board.getTile(new Coordinates(x,y)).getGurkin());
                if (p1Board.getTile(new Coordinates(x,y)).getGurkin() != null && p1Board.getTile(new Coordinates(x,y)).getGurkin() instanceof Terrain) {
                    hasTerrain1 = true;
                }
                if (p2Board.getTile(new Coordinates(x,y)).getGurkin() != null && p2Board.getTile(new Coordinates(x,y)).getGurkin() instanceof Terrain) {
                    hasTerrain2 = true;
                }

            }
        }
        assertTrue(hasTerrain1);
        assertTrue(hasTerrain2);

    }

    @Then("I should get the char value t")
    public void i_should_get_the_char_value_t() {
        assertEquals('t', c.charValue());
    }

    @When("i set the turn to {int}")
    public void i_set_the_turn_to(Integer int1) {
        Turn.setTurn(int1.toString());
    }
    @Then("the turn should be {int}")
    public void the_turn_should_be(Integer int1) {
        assertEquals(int1.toString(), Turn.getTurn());
    }

    @When("I check if the gurkin is a yardlong")
    public void i_check_if_the_gurkin_is_a_yardlong() {
        t = new Tile();
        t.setGurkin(g);
        valid =t.check(g);
    }
    @Then("the gurkin should be a yardlong")
    public void the_gurkin_should_be_a_yardlong() {
        assertTrue(valid);
    }

    @When("I set the board on the tile")
    public void i_set_the_board_on_the_tile() {
        t.setBoard(p2b);
    }
    @Then("the tile should have a board")
    public void the_tile_should_have_a_board() {
        assertEquals(p2b, t.getBoard());
    }
    @Given("a multiplayer game")
    public void a_multiplayer_game() {
        game = new Game(true);
    }

    @Given("the turn is two")
    public void the_turn_is_two() {
        Turn.setTurn("2");
        System.out.println(Turn.getTurn());
    }
    @When("I get the current player")
    public void i_get_the_current_player() {
        p1 = game.getCurrentPlayer();
    }
    @Then("the current player should be player2")
    public void the_current_player_should_be_player2() {
        assertEquals(game.getPlayer2(), p1);
    }

    @Given("the turn is one")
    public void the_turn_is_one() {
        Turn.setTurn("1");
    }

    @Then("the current player should be player1")
    public void the_current_player_should_be_player1() {
        assertEquals(game.getPlayer1(), p1);
    }

    @Then("the AI player should be null")
    public void the_ai_player_should_be_null() {
        assertNull(game.getAIPlayer());
    }

    @Then("the AI player should be player2")
    public void the_ai_player_should_be_player2() {
        assertEquals(game.getPlayer2(), game.getAIPlayer());
    }

    @Then("the opponent should be player2")
    public void the_opponent_should_be_player2() {
        assertEquals(game.getPlayer2(), game.getOpponent());
    }

    @Then("the opponent should be player1")
    public void the_opponent_should_be_player1() {
        assertEquals(game.getPlayer1(), game.getOpponent());
    }

    @When("I attack a coordinate")
    public void i_attack_a_coordinate() {
        game.attack(new Coordinates(0,0));
    }

    @Then("the shot result should be on that coordinate")
    public void the_shot_result_should_be_on_that_coordinate() {
        Turn.changeTurn();
        assertNotNull(game.getCurrentPlayer().getShotResults()[0][0]);
    }
    @Then("the tile should be hit")
    public void the_tile_should_be_hit() {
        assertTrue(game.getOpponent().getGurkinBoard().getTile(new Coordinates(0,0)).isHit());
    }

    @When("I set the initial turn")
    public void i_set_the_initial_turn() {
        game.setInitial_turn();
    }


    @Given("initial turn is player1")
    public void initial_turn_is_player1() {
        Turn.setTurn("1");
        game.setInitial_turn();
    }
    @Then("the initial turn should be {int}")
    public void the_initial_turn_should_be(Integer int1) {
        assertEquals(int1.toString(), game.getInitial_turn());
    }

    @Given("a board with all gurkins paced in valid positions")
    public void a_board_with_all_gurkins_paced_in_valid_positions() {
        p2b = new Board();
        p2b.placeGurkin(new Coordinates(0,0), Direction.direction.Horizontal, new Conichon());
        p2b.placeGurkin(new Coordinates(0,1), Direction.direction.Horizontal, new Gherkin());
        p2b.placeGurkin(new Coordinates(0,2), Direction.direction.Horizontal, new Pickle());
        p2b.placeGurkin(new Coordinates(0,3), Direction.direction.Horizontal, new Yardlong());
        p2b.placeGurkin(new Coordinates(0,4), Horizontal, new Zuchinni());
    }
    @When("I initialise the board views")
    public void i_initialise_the_board_views() {
        p2b.prepareViewGurkin();
    }
    @Then("the board views should be initialised")
    public void the_board_views_should_be_initialised() {
        assertTrue(p2b.getplacedGurkins()[0] instanceof Conichon);
        assertTrue(p2b.getplacedGurkins()[1] instanceof Gherkin);
        assertTrue(p2b.getplacedGurkins()[2] instanceof Pickle);
        assertTrue(p2b.getplacedGurkins()[3] instanceof Yardlong);
        assertTrue(p2b.getplacedGurkins()[4] instanceof Zuchinni);

        for (int i = 0; i < 5; i++) {
            assertNotNull(p2b.getStartCoors()[i]);
        }

        assertSame(p2b.getStartDirs()[0], Horizontal);
        assertSame(p2b.getStartDirs()[1], Horizontal);
        assertSame(p2b.getStartDirs()[2], Horizontal);
        assertSame(p2b.getStartDirs()[3], Horizontal);
        assertSame(p2b.getStartDirs()[4], Horizontal);

    }
    @Given("a board with all gurkins paced in valid vertical positions")
    public void a_board_with_all_gurkins_paced_in_valid_vertical_positions() {
        p2b = new Board();
        p2b.placeGurkin(new Coordinates(0,0), Vertical, new Conichon());
        p2b.placeGurkin(new Coordinates(1,0), Vertical, new Gherkin());
        p2b.placeGurkin(new Coordinates(2,0), Vertical, new Pickle());
        p2b.placeGurkin(new Coordinates(3,0), Vertical, new Yardlong());
        p2b.placeGurkin(new Coordinates(4,0), Vertical, new Zuchinni());
    }

    @Then("the board views should be initialised vertically")
    public void the_board_views_should_be_initialised_vertically() {
        assertTrue(p2b.getplacedGurkins()[0] instanceof Conichon);
        assertTrue(p2b.getplacedGurkins()[1] instanceof Gherkin);
        assertTrue(p2b.getplacedGurkins()[2] instanceof Pickle);
        assertTrue(p2b.getplacedGurkins()[3] instanceof Yardlong);
        assertTrue(p2b.getplacedGurkins()[4] instanceof Zuchinni);

        for (int i = 0; i < 5; i++) {
            assertNotNull(p2b.getStartCoors()[i]);
            assertSame(Vertical, p2b.getStartDirs()[i]);
        }

    }

    @When("i initialise a player with attributes")
    public void i_initialise_a_player_with_attributes() {
        p1 = new Player(1, "red", true);
    }
    @Then("the player should have those attributes")
    public void the_player_should_have_those_attributes() {
        assertEquals(1, p1.getId());
        assertEquals("red", p1.getName());
        assertTrue(p1.getCurrent());
    }

    @When("I reset the placement")
    public void i_reset_the_placement() {
        p1.resetPlacement();
    }

    @Then("the board should be reset")
    public void the_board_should_be_reset() {
        Boolean empty = true;
        for(Tile t : p1.getGurkinBoard().getTiles()){
            if (t.hasGurkin()) {
                empty = false;
            }
        }

        assertTrue(empty);
        assertEquals(0, p1.getRemaining_gurkins());
        assertEquals(5, p1.getUnplacedGurks().size());
    }



    @When("I delete the database {string}")
    public void i_delete_the_database(String string) throws Exception {
        database.deleteDatabase("tester");
    }

    @Then("the database {string} should not exist")
    public void the_database_should_not_exist(String string) throws Exception{
        assertFalse(database.getDatabases().stream().anyMatch(str -> str.equals("tester")));
        //assertFalse(database.TestConnection("tester"));
    }

    @Given("Collection of a Result")
    public void collection_of_a_result() {
        shotResults = new ShotResults();
        shotResults.setHit(new Coordinates(1,1));
        shotResults.setHit(new Coordinates(2,2));
        shotResults.setKill(new Coordinates(3,3));
        shotResults.setMiss(new Coordinates(4,4));
        shotResults.toShotCollection();
        ShotResults shotResults1 = shotResults;
        shotResults1.transformation();
        assertEquals(shotResults.getShotBoard(),shotResults1.getShotBoard());
    }

    @When("I have information about a shotResult from my database")
    public void i_have_information_about_a_shot_result_from_my_database() {
        ShotResults shotResults2 = new ShotResults();
        shotResults2.setHit(new Coordinates(1,1));
        shotResults2.setHit(new Coordinates(2,2));
        shotResults2.setKill(new Coordinates(3,3));
        shotResults2.setMiss(new Coordinates(4,4));
        shotResults2.toShotCollection();
    }
    @Then("i need to be able to get the id, Character, x coordinate, y coordinante and which shotResult class it is associated with")
    public void i_need_to_be_able_to_get_the_id_character_x_coordinate_y_coordinante_and_which_shot_result_class_it_is_associated_with() {
        ShotResults shotResults1 = new ShotResults();
        for (Result result : shotResults.getShotCollection()) {
            Result result1 = new Result();
            result1.setId(result.getId());
            result1.setX(result.getX());
            result1.setY(result.getY());
            result1.setCharacter(result.getCharacter());
            result1.setShotResults(result.getShotResults());
            shotResults1.addShotBoardCollection(result1);
            assertEquals(result1.getId(), result.getId());
            assertEquals(result1.getX(), result.getX());
            assertEquals(result1.getY(), result.getY());
            assertEquals(result1.getCharacter(), result.getCharacter());
            assertEquals(result1.getShotResults(), result.getShotResults());

        }
    }
    @Then("set all this information into a new Result and add it to a new collection of Results.")
    public void set_all_this_information_into_a_new_result_and_add_it_to_a_new_collection_of_results() {
        ShotResults shotResults1 = new ShotResults();
        for (Result result : shotResults.getShotCollection()) {
            Result result1 = new Result();
            result1.setId(result.getId());
            result1.setX(result.getX());
            result1.setY(result.getY());
            result1.setCharacter(result.getCharacter());
            result1.setShotResults(result.getShotResults());
            shotResults1.addShotBoardCollection(result1);
        }
        shotResults.transformation();
        shotResults1.transformation();
        assertEquals(shotResults.getShotBoard(), shotResults1.getShotBoard());
    }

    @Given("an existing database")
    public void an_existing_database() throws Exception{
        database = new Database("existing_database");
    }
    @When("I update a game")
    public void i_update_a_game() throws Exception {
        game.getPlayer1().setName("Akira is a beast");
        database.updateGame(game);
    }
    @Then("The game should be the same as the one i save")
    public void the_game_should_be_the_same_as_the_one_i_save() throws Exception {
        Game loadedGame = database.loadGame("existing_database");
        assertEquals(game.getPlayer1().getName(), loadedGame.getPlayer1().getName());
        database.deleteDatabase("existing_database");
    }


    @Given("a board with terrain")
    public void a_board_with_terrain() {
        p2b = new Board();
        p2b.placeGurkin(new Coordinates(0,0), Horizontal, new Terrain());
    }

    @When("I hit a terrain")
    public void i_hit_a_terrain() {
        p1 = new Player();
    }
    @Then("the terrain should be hit")
    public void the_terrain_should_be_hit() {
        p1.shoot(p2b, new Coordinates(0,0));
        assertTrue(p2b.getTile(new Coordinates(0,0)).isHit());
    }


}
