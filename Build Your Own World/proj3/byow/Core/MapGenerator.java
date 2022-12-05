package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class MapGenerator extends Engine {
    public TETile[][] game;
    public Random c;
    public int w;
    public int h;
    public int rooms;
    ArrayList<ArrayList<Integer>> recorder;
    ArrayList<Integer> connected;
    HashMap<Integer, ArrayList<Double>> distance;
    ArrayList<Integer> avatarLocation;
    ArrayList<ArrayList<Integer>> treasureLoc;

    /**
     * Recorder holds random points of every single room that is being generated. Why is it 40? Idek,
     * What I do know is that for some reason, in the main or engine class, when you call mapgenerator,
     * you are using a random seed to determine the amount of room numbers but for some reason it always outputs
     * 41 which makes the inner for loop stop at 40.
     * Now for the actual recorder stuff, you get a random point inside of every single room generated.
     * When you do draw halls. Example -> [[4, 1], [200, 196], [5, 6]].
     * The way you are creating halls is by comparing two adjacent rooms, but if you realize. Room 1 -> Room2 are far
     * But room1 -> room3 are close. So right now you are creating hallway between room1 -> room2 and then
     * from room2 -> room3.
     * that we want the room to take.
     * so ideal solution would be to sort by closeness (aka MST) and then do your own
     */

    public MapGenerator(TETile[][] holder, Random r, int WIDTH, int HEIGHT) {
        game = holder;
        c = r;
        w = WIDTH;
        h = HEIGHT;
        recorder = new ArrayList<>(); // nested arrayLists, first element is x coordinate, second element is y coordinate
        distance = new HashMap<>();
        rooms = 0;
        treasureLoc = new ArrayList<>();
    }

    /**
     * validates that tiles are being used in the space
     * that we want the room to take.
     */
    private boolean validater(int x, int y, int x_size, int y_size) {
        for (int i = x; i < (x + x_size); i += 1) {
            for (int j = y; j < (y + y_size); j += 1) {
                if (game[i][j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates each of the rooms by choosing a random bottom left coordinate on the grid
     * and adds points based on the random size of x and y. Also picks a random xy to use
     * as a reference point later for creating hallways. That is added to recorder that
     * holds them for later use.
     */
    public TETile[][] drawLTiles() {
        int x = RandomUtils.uniform(c, 4, w - 8);
        int y = RandomUtils.uniform(c, 4, h - 8);
        int x_size = RandomUtils.uniform(c, 2, 6);
        int y_size = RandomUtils.uniform(c, 2, 6);
        if (validater(x - 2, y - 2, x_size + 4, y_size + 4)) {
            int randx = RandomUtils.uniform(c, x, x + x_size - 1);
            int randy = RandomUtils.uniform(c, y, y + y_size - 1);
            ArrayList<Integer> randxy = new ArrayList<>();
            randxy.add(randx);
            randxy.add(randy);
            recorder.add(randxy);
            for (int i = x - 1; i <= (x + x_size); i += 1) {
                for (int j = y - 1; j <= (y + y_size); j += 1) {
                    if (j == y - 1 || j == (y + y_size) || i == x - 1 || i == (x + x_size)) {
                        game[i][j] = Tileset.WALL;
                    } else {
                        game[i][j] = Tileset.FLOOR;
                    }
                }
            }
            rooms += 1;
        }
        return game;
    }

    private ArrayList<Integer> getConnected(HashMap<Integer, ArrayList<Double>> distances, ArrayList<Integer> connect) {
        for (int i = 0; i < distances.size(); i++) {
            if (distances.containsKey(i)) {
                int room = distances.get(i).get(0).intValue();
                connect.add(room);
                connect.add(i);
            }
        }
        return connect;
    }

    /**
     * gathers data on the shortest distances between each of the rooms and inputs
     * into distance like an adj list.
     */
    private HashMap<Integer, ArrayList<Double>> distances() {
        distance = new HashMap<>();
        connected = new ArrayList<>();
        for (int i = 0; i < recorder.size(); i++) {
            ArrayList<Integer> xy = recorder.get(i);
            for (int j = 0; j < recorder.size(); j++) {
                if (j != i && (connected.isEmpty() || connected.contains(j))) {
                    ArrayList<Integer> xy2 = recorder.get(j);
                    double distancer = Math.hypot(Math.abs(xy.get(0) - xy2.get(0)), Math.abs(xy.get(1) - xy2.get(1)));
                    if (!distance.containsKey(i) || (distance.containsKey(i) && distance.get(i).get(1) > distancer)) {
                        if (!distance.containsKey(j) || (distance.containsKey(j) && distance.get(j).get(0) != i)) {
                            ArrayList<Double> putter = new ArrayList<>();
                            putter.add((double) j);
                            putter.add(distancer);
                            distance.put(i, putter);
                        }
                    }
                }
            }
            connected = getConnected(distance, connected);
        }
        return distance;
    }

    public TETile[][] drawLHall(TETile type, int x1, int x2, int y1, int y2) {
        if (y1 > y2) {
            for (int i = y1; i >= y2; i--) {
                if (game[x1][i] != Tileset.FLOOR) {
                    game[x1][i] = type;
                }
            }
        } else {
            for (int j = y1; j <= y2; j++) {
                if (game[x1][j] != Tileset.FLOOR) {
                    game[x1][j] = type;
                }
            }
        }
        if (x1 > x2) {
            x1 = x1 ^ x2 ^ (x2 = x1);
        }
        for (int i = x1; i < x2; i++) {
            if (game[i][y2] != Tileset.FLOOR) {
                game[i][y2] = type;
            }
        }
        return game;
    }

    /**
     * creates the hallways...
     */
    public TETile[][] halls() {
        distance = distances();
        int indexer = 0;
        while (indexer < rooms) {
            if (distance.containsKey(indexer)) {
                int other = (int) Math.round(distance.get(indexer).get(0));
                int x1 = recorder.get(indexer).get(0);
                int x2 = recorder.get(other).get(0);
                int y1 = recorder.get(indexer).get(1);
                int y2 = recorder.get(other).get(1);
                game = drawLHall(Tileset.FLOOR, x1, x2, y1, y2);
                game = waller(Tileset.WALL, x1, x2, y1, y2);
            }
            indexer += 1;
        }
        return game;
    }

    private TETile[][] waller(TETile type, int x1, int x2, int y1, int y2) {
        if (x1 == x2) {
            game = drawLHall(type, x1 - 1, x2 - 1, y1, y2);
            game = drawLHall(type, x1 + 1, x2 + 1, y1, y2);
        } else if (y1 == y2) {
            game = drawLHall(type, x1, x2, y1 - 1, y2 - 1);
            game = drawLHall(type, x1, x2, y1 + 1, y2 + 1);
        } else if (y1 > y2) {
            if (x1 > x2) {
                game = drawLHall(type, x1 - 1, x2, y1, y2 + 1);
                game = drawLHall(type, x1 + 1, x2, y1, y2 - 1);
            } else {
                game = drawLHall(type, x1 - 1, x2, y1, y2 - 1);
                game = drawLHall(type, x1 + 1, x2, y1, y2 + 1);
            }
        } else if (y2 > y1) {
            if (x1 > x2) {
                game = drawLHall(type, x1 - 1, x2, y1, y2 - 1);
                game = drawLHall(type, x1 + 1, x2, y1, y2 + 1);
            } else {
                game = drawLHall(type, x1 - 1, x2, y1, y2 + 1);
                game = drawLHall(type, x1 + 1, x2, y1, y2 - 1);
            }
        }
        return game;
    }

    private TETile[][] avatarCreate() {
        int avatarRoom = RandomUtils.uniform(c, rooms);
        int x = recorder.get(avatarRoom).get(0);
        int y = recorder.get(avatarRoom).get(1);
        avatarLocation = new ArrayList<>();
        avatarLocation.add(x);
        avatarLocation.add(y);
        game[x][y] = Tileset.AVATAR;
        return game;
    }

    private TETile[][] doorCreator() {
        int num = 0;
        while (num < 6) {
            ArrayList<Integer> point = new ArrayList<>();
            int indexer = RandomUtils.uniform(c, rooms);
            int x = recorder.get(indexer).get(0);
            int y = recorder.get(indexer).get(1);
            if (game[x][y] != Tileset.AVATAR && game[x][y] != Tileset.LOCKED_DOOR) {
                game[x][y] = Tileset.LOCKED_DOOR;
                point.add(x);
                point.add(y);
                treasureLoc.add(point);
                num += 1;
            }
        }
        return game;
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

    public TETile[][] createMap() {
        int rooms = RandomUtils.uniform(c, 4500, 5000);
        for (int i = 0; i < rooms; i++) {
            drawLTiles();
        }
        halls();
        avatarCreate();
        doorCreator();
        makeLight();
        for (int i = 0; i <= 150; i++) {
            int x = RandomUtils.uniform(c, 2, WIDTH - 3);
            int y = RandomUtils.uniform(c, 2, HEIGHT - 3);
            if ((game[x + 1][y] != Tileset.STAR) && (game[x - 1][y] != Tileset.STAR) && (game[x][y + 1] != Tileset.STAR) && (game[x][y - 1] != Tileset.STAR)) {
                if (game[x][y] == Tileset.NOTHING) {
                    game[x][y] = Tileset.STAR;
                }
            }
        }
        return game;
    }
}
