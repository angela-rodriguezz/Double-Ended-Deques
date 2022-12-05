package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Long.parseLong;

public class Gameplay extends Engine {
    public TETile[][] game;
    public Random c;
    public int w;
    public int h;
    public int rooms;
    ArrayList<ArrayList<Integer>> recorder;
    MapGenerator m;
    int five;
    ArrayList<ArrayList<Integer>> treasureLoc;

    boolean ltrue;

    public Gameplay(MapGenerator reference) {
        game = reference.game;
        m = reference;
        c = reference.c;
        w = WIDTH;
        h = HEIGHT;
        recorder = reference.recorder; // nested arrayLists, first element is x coordinate, second element is y coordinate
        rooms = recorder.size();
        five = 0;
        treasureLoc = reference.treasureLoc;
    }

    public void makeLight() {
        for (ArrayList<Integer> item : treasureLoc) {
            int x = item.get(0);
            int y = item.get(1);
            double distance;
            for (int i = x - 5; i <= x + 5; i++) {
                for (int j = y - 5; j <= y + 5; j++) {
                    if (i >= 0 && i < WIDTH && j >= 0 && j < HEIGHT) {
                        if (game[i][j] != Tileset.NOTHING && game[i][j] != Tileset.LOCKED_DOOR) {
                            distance = Math.hypot(Math.abs(x - i), Math.abs(y - j));
                            if (distance <= 1) {
                                if (game[i][j].description().equals("wall")) {
                                    game[i][j] = Tileset.WALL4;
                                } else if (game[i][j].description().equals("floor")) {
                                    game[i][j] = Tileset.FLOOR4;
                                }
                            } else if (distance < 2) {
                                if (game[i][j].description().equals("wall")) {
                                    game[i][j] = Tileset.WALL3;
                                } else if (game[i][j].description().equals("floor")) {
                                    game[i][j] = Tileset.FLOOR3;
                                }
                            } else if (distance < 2.5) {
                                if (game[i][j].description().equals("wall")) {
                                    game[i][j] = Tileset.WALL2;
                                } else if (game[i][j].description().equals("floor")) {
                                    game[i][j] = Tileset.FLOOR2;
                                }
                            } else if (distance < 3.5) {
                                if (game[i][j].description().equals("wall")) {
                                    game[i][j] = Tileset.WALL1;
                                } else if (game[i][j].description().equals("floor")) {
                                    game[i][j] = Tileset.FLOOR1;
                                }
                                else if (distance < 4.5) {
                                    if (game[i][j].description().equals("wall")) {
                                        game[i][j] = Tileset.WALL0;
                                    } else if (game[i][j].description().equals("floor")) {
                                        game[i][j] = Tileset.FLOOR0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public TETile[][] movement(String move) {
        int x = m.avatarLocation.get(0);
        int y = m.avatarLocation.get(1);
        if (move.equals("W") || move.equals("w")) {
            // When the input is W
            // Some implementation
            if (!game[x][y + 1].description().equals("wall")) {
                if (game[x][y + 1] == Tileset.LOCKED_DOOR) {
                    ltrue = true;
                }
                    game[x][y] = Tileset.FLOOR;
                    game[x][y + 1] = Tileset.AVATAR;
                    m.avatarLocation = new ArrayList<>();
                    m.avatarLocation.add(x);
                    m.avatarLocation.add(y + 1);
            }
        }
        else if (move.equals("A") || move.equals("a")) {
            // When the input is A
            // Some implementation
            if (!game[x - 1][y].description().equals("wall")) {
                if (game[x - 1][y] == Tileset.LOCKED_DOOR) {
                    ltrue = true;
                }
                    game[x][y] = Tileset.FLOOR;
                    game[x - 1][y] = Tileset.AVATAR;
                    m.avatarLocation = new ArrayList<>();
                    m.avatarLocation.add(x - 1);
                    m.avatarLocation.add(y);
            }

        } else if (move.equals("S") || move.equals("s")) {
            // When the input is B
            // Some implementation
            if (!game[x][y - 1].description().equals("wall")) {
                if (game[x][y - 1] == Tileset.LOCKED_DOOR) {
                    ltrue = true;
                }
                    game[x][y] = Tileset.FLOOR;
                    game[x][y - 1] = Tileset.AVATAR;
                    m.avatarLocation = new ArrayList<>();
                    m.avatarLocation.add(x);
                    m.avatarLocation.add(y - 1);
            }
        } else if (move.equals("D") || move.equals("d")) {
            // When the input is D
            // Some implementation
            if (!game[x + 1][y].description().equals("wall")) {
                if (game[x + 1][y] == Tileset.LOCKED_DOOR) {
                    ltrue = true;
                }
                    game[x][y] = Tileset.FLOOR;
                    game[x + 1][y] = Tileset.AVATAR;
                    m.avatarLocation = new ArrayList<>();
                    m.avatarLocation.add(x + 1);
                    m.avatarLocation.add(y);
            }
        }
        return game;
    }
}
