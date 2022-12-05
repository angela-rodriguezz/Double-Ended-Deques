package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

import static java.lang.Long.parseLong;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public final static String filename = "./byow/saveFile.txt";
    public String name = "";
    public Gameplay g;
    public MapGenerator m;
    public Random checker;
    public String hover = "Hover mouse for description";
    Out out;
    In in;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        MainMenu startUp = new MainMenu();
        startUp.drawMenu();
        String string;
        boolean gameOn = false;
        String keypresses = "";
        boolean marked = false;
        boolean newGame = false;
        in = new In(filename);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                string = Character.toString(StdDraw.nextKeyTyped());
                if (string.equals(":")) {
                    marked = true;
                }
                else if (marked) {
                    if ((newGame || in.isEmpty()) && (string.equals("Q") || string.equals("q"))) {
                        out = new Out(filename);
                        boolean DNL = false;
                        if (!keypresses.equals("")) {
                            out.println(keypresses);
                        } else {
                            DNL = true;
                            out.println("DNL");
                        }
                        if (!DNL) {
                            if (!name.equals("")) {
                                out.println(name);
                            } else {
                                out.println("NoName");
                            }
                            if (startUp.lottonum.equals("")) {
                                out.println("0");
                            } else {
                                out.println(startUp.lottonum);
                            }
                        }
                        marked = false;
                        System.exit(0);
                    }
                    else if(!in.isEmpty() && (string.equals("Q") || string.equals("q"))) {
                        marked = false;
                        System.exit(0);
                    }
                }
                else if (gameOn) {
                    keypresses += string;
                    g.movement(string);
                    g.makeLight();
                    if (g.ltrue && !startUp.gameOver) {
                        ter.renderFrame(g.game);
                        startUp.lotteryNumber(checker);
                        startUp.HUDCreate(name, hover);
                        g.ltrue = false;
                    } else if (startUp.gameOver) {
                        ter.renderFrame(g.game);
                        startUp.lotteryNumber(checker);
                        startUp.HUDCreate(name, hover);
                    } else {
                        ter.renderFrame(g.game);
                        startUp.HUDCreate(name, hover);
                    }
                    StdDraw.show();
                }
                else if (string.equals("I") || string.equals("i")) {
                    startUp.instructionsWindow();
                    startUp.drawMenu();
                }
                else if (string.equals("N") || string.equals("n")) {
                    gameOn = true;
                    newGame = true;
                    startUp.seedAdder();
                    String seeder = startUp.solicitNCharsInput();
                    keypresses += seeder;
                    long seed = parseLong(seeder.replaceAll("[^0-9]", ""));
                    checker = new Random(seed);
                    TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
                    for (int x = 0; x < WIDTH; x += 1) {
                        for (int y = 0; y < HEIGHT; y += 1) {
                            finalWorldFrame[x][y] = Tileset.NOTHING;
                        }
                    }
                    m = new MapGenerator(finalWorldFrame, checker, WIDTH, HEIGHT);
                    m.createMap();
                    g = new Gameplay(m);
                    ter.initialize(WIDTH, HEIGHT);
                    ter.renderFrame(g.game);
                    startUp.HUDCreate(name, hover);
                }
                else if (string.equals("E") || string.equals("e")) {
                    startUp.nameInitializer();
                    name = startUp.solicitNameCharsInput();
                    startUp.drawMenu();
                }
                else if (string.equals("L") || string.equals("l")) {
                    String id = in.readLine();
                    if (!id.equals("DNL")) {
                        name = in.readLine();
                        String points = in.readLine();
                        long seed = parseLong(id.replaceAll("[^0-9]", ""));
                        keypresses += id;
                        char[] moves = id.replaceAll("[^A-Za-z]", "").toCharArray();
                        checker = new Random(seed);
                        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
                        for (int x = 0; x < WIDTH; x += 1) {
                            for (int y = 0; y < HEIGHT; y += 1) {
                                finalWorldFrame[x][y] = Tileset.NOTHING;
                            }
                        }
                        MapGenerator r = new MapGenerator(finalWorldFrame, checker, WIDTH, HEIGHT);
                        r.createMap();
                        g = new Gameplay(r);
                        for (char word : moves) {
                            g.movement(Character.toString(word));
                            g.makeLight();
                            if (g.ltrue) {
                                startUp.lotteryNumber(checker);
                                g.ltrue = false;
                            }
                        }
                        gameOn = true;
                        ter.renderFrame(g.game);
                        startUp.HUDCreate(name, hover);
                        StdDraw.setFont(new Font("Monaco", Font.BOLD, 14));
                        StdDraw.show();
                    } else {
                        startUp.loadError();
                        startUp.drawMenu();
                    }
                }
            }
            if (gameOn) {
                int x = (int) StdDraw.mouseX();
                int y = (int) StdDraw.mouseY();
                String value;
                if (x < WIDTH && x >= 0 && y < HEIGHT && y >= 0) {
                    value = g.game[x][y].description();
                    if (!hover.equals(value)) {
                        hover = value;
                        ter.renderFrame(g.game);
                        if (startUp.gameOver) {
                            startUp.lotteryNumber(checker);
                        }
                        startUp.HUDCreate(name, hover);
                        StdDraw.show();
                    }
                }
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        // initializes and adds empty tiles to the board.
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        if (!input.equals("")) {
            long seed = parseLong(input.replaceAll("[^0-9]", ""));
            char[] moves = input.replaceAll("[^A-Za-z]", "").toCharArray();
            Random checker = new Random(seed);
            MainMenu m = new MainMenu();
            MapGenerator r = new MapGenerator(finalWorldFrame, checker, WIDTH, HEIGHT);
            r.createMap();
            Gameplay g = new Gameplay(r);
            if (moves.length != 0) {
                for (char word : moves) {
                    g.movement(Character.toString(word));
                    g.makeLight();
                    if (g.ltrue) {
                        m.lotteryNumber(checker);
                        g.ltrue = false;
                    }
                }
            }
            finalWorldFrame = g.game;
        }
        return finalWorldFrame;
    }

    public static void main(String[] args) {
        Engine e = new Engine();
        TERenderer ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);
        TETile[][] p = interactWithInputString("12");
        e.interactWithKeyboard();
        //ter.renderFrame(p);
    }
}