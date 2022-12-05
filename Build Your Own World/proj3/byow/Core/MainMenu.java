package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

public class MainMenu extends Engine {

    public int five;
    public String lottonum = "";
    public boolean gameOver = false;
    public MainMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 14));
        StdDraw.enableDoubleBuffering();
    }

    public void drawMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, 6.5 * (HEIGHT / 8), "CAVE OF LUCK");
        Font fontMedium = new Font("Monaco", Font.ROMAN_BASELINE, 30);
        StdDraw.setFont(fontMedium);
        StdDraw.text(WIDTH / 2, 5.5 * (HEIGHT / 8), "New Game (N)");
        StdDraw.text(WIDTH / 2, 4.5 * (HEIGHT / 8), "Load Game (L)");
        StdDraw.text(WIDTH / 2, 3.5 * (HEIGHT / 8), "Instructions (I)");
        StdDraw.text(WIDTH / 2, 2.5 * (HEIGHT / 8), "Name Character (E)");
        StdDraw.text(WIDTH / 2, 1.5 * (HEIGHT / 8), "Quit Game (Q)");
        StdDraw.show();
    }
    public void instructionsWindow() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, 6 * (HEIGHT / 8), "IT'S YOUR LUCKY DAY!");
        Font fontMedium = new Font("Monaco", Font.ROMAN_BASELINE, 30);
        StdDraw.setFont(fontMedium);
        StdDraw.text(WIDTH / 2, 5 * (HEIGHT / 8), "You found an abandoned cave. You've heard from the people in your town");
        StdDraw.text(WIDTH / 2, 4.5 * (HEIGHT / 8),"that there are hidden treasures that when opened give lucky numbers. You");
        StdDraw.text(WIDTH / 2, 4 * (HEIGHT / 8), "can use them in your town's lottery! Make sure to get all of the");
        StdDraw.text(WIDTH / 2, 3.5 * (HEIGHT / 8), "treasures so you can get a higher chance of success!");
        StdDraw.text(WIDTH / 2, 2 * (HEIGHT / 8), "Press P to return to the main menu.");
        StdDraw.show();
        boolean marked = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String string = Character.toString(StdDraw.nextKeyTyped());
                if (string.equals(":")) {
                    marked = true;
                }
                else if (marked) {
                    if (string.equals("Q") || string.equals("q")) {
                        System.exit(0);
                    }
                }
                else if (string.equals("P") || string.equals("p")) {
                    break;
                }
            }
        }
    }

    public void seedAdder() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, 6 * (HEIGHT / 8), "Please enter a seed.");
        Font fontMedium = new Font("Monaco", Font.ROMAN_BASELINE, 30);
        StdDraw.setFont(fontMedium);
        StdDraw.text(WIDTH / 2, 5 * (HEIGHT / 8), "Click S when complete.");
        StdDraw.show();
    }

    public void nameInitializer() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, 6 * (HEIGHT / 8), "Please enter a name.");
        Font fontMedium = new Font("Monaco", Font.ROMAN_BASELINE, 30);
        StdDraw.setFont(fontMedium);
        StdDraw.text(WIDTH / 2, 5 * (HEIGHT / 8), "Click P when complete. Click :Q to quit.");
        StdDraw.show();
    }

    public String solicitNameCharsInput() {
        String collector = "";
        boolean marked = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String string = Character.toString(StdDraw.nextKeyTyped());
                if (string.equals(":")) {
                    marked = true;
                }
                else if (marked) {
                    if (string.equals("Q") || string.equals("q")) {
                        System.exit(0);
                    }
                }
                 else if (collector != "" && (string.equals("P") || string.equals("p"))) {
                    break;
                } else {
                    StdDraw.clear(Color.BLACK);
                    nameInitializer();
                    collector += string;
                    StdDraw.setPenColor(Color.WHITE);
                    Font fontMedium = new Font("Monaco", Font.TRUETYPE_FONT, 30);
                    StdDraw.setFont(fontMedium);
                    StdDraw.text(WIDTH / 2, 4 * (HEIGHT / 8), collector);
                }
            }
            StdDraw.show();
        }
        return collector;
    }
    public String solicitNCharsInput() {
        String collector = "";
        boolean marked = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String string = Character.toString(StdDraw.nextKeyTyped());
                if (string.equals(":")) {
                    marked = true;
                }
                else if (marked) {
                    if (string.equals("Q") || string.equals("q")) {
                        System.exit(0);
                    }
                }
                else if (collector != "" && (string.equals("S") || string.equals("s"))) {
                    break;
                }
                else if (string.replaceAll("[^0-9]","").equals("")) {
                    continue;
                } else {
                    StdDraw.clear(Color.BLACK);
                    seedAdder();
                    collector += string;
                    StdDraw.setPenColor(Color.WHITE);
                    Font fontMedium = new Font("Monaco", Font.TRUETYPE_FONT, 30);
                    StdDraw.setFont(fontMedium);
                    StdDraw.text(WIDTH / 2, 4 * (HEIGHT / 8), collector);
                }
            }
            StdDraw.show();
        }
        return collector;
    }

    public int randLotto(Random c) {
        int returner = 0;
        if (five < 5) {
            returner = RandomUtils.uniform(c, 1,69);
            five += 1;

        } else if (five == 5) {
            returner = RandomUtils.uniform(c, 1, 26);
            five += 1;
            gameOver = true;
        }
        return returner;
    }

    public void HUDCreate(String name, String clicker) {
        StdDraw.setPenColor(Color.WHITE);
        Font fontSmall = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontSmall);
        StdDraw.textLeft(1, HEIGHT - 1, clicker);
        if (!lottonum.equals("")) {
            StdDraw.text(WIDTH / 2, HEIGHT - 1, lottonum);
        } else {
            StdDraw.text(WIDTH / 2, HEIGHT - 1, "0");
        }
        if (name.equals("")) {
            StdDraw.textRight(WIDTH - 1, HEIGHT - 1, "Player NoName");
        } else {
            StdDraw.textRight(WIDTH - 1, HEIGHT - 1, "Player " + name);
        }
        fontSmall = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(fontSmall);
    }
    public void lotteryNumber(Random c) {
        int num = randLotto(c);
        if (num != 0) {
            lottonum += num;
        }
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontBig);
        if (!gameOver) {
            StdDraw.text(WIDTH / 2, 1, "CONGRATS! YOUR LUCKY NUMBER IS: " + num);
        } else {
            StdDraw.text(WIDTH / 2, 1, "CONGRATS! YOU COLLECTED ALL NUMBERS! PRESS :Q TO GO TO EXIT.");
        }
        fontBig = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(fontBig);
    }
    public void loadError() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, 6 * (HEIGHT / 8), "YOU HAVE NO PREVIOUS SAVE");
        Font fontMedium = new Font("Monaco", Font.ROMAN_BASELINE, 30);
        StdDraw.setFont(fontMedium);
        StdDraw.text(WIDTH / 2, 5 * (HEIGHT / 8), "Click P to return to main menu.");
        StdDraw.text(WIDTH / 2, 4 * (HEIGHT / 8), "Click :Q to quit.");
        StdDraw.show();
        boolean marked = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String string = Character.toString(StdDraw.nextKeyTyped());
                if (string.equals("P") || string.equals("p")) {
                    break;
                }
                else if (string.equals(":")) {
                    marked = true;
                }
                else if (marked) {
                    if (string.equals("Q") || string.equals("q")) {
                        System.exit(0);
                    }
                }
            }
        }
    }
}
