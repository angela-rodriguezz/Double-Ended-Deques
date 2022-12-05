package byow.TileEngine;

import org.knowm.xchart.internal.Utils;

import java.io.File;
import java.nio.file.Paths;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {


    //public static final TETile WALL1 = new TETile("#", new Color())
    //public static final TETile CLOUD = new TETile('-', Color.white, new Color(14, 11, 41), "cave wall", "./byow/cavewall.png");
    public static final TETile STAR = new TETile('+', Color.WHITE, new Color(12, 10, 28), "star");
    public static final TETile AVATAR = new TETile('☺', Color.white, Color.black, "you");
    public static final TETile WALL = new TETile('⊞', new Color(40, 37, 34), new Color (27, 20, 6),
            "wall");
    public static final TETile WALL0 = new TETile('⊞', new Color(64, 59, 55), new Color (41, 32, 22),
            "wall");
    public static final TETile WALL1 = new TETile('⊞', new Color(107, 96, 88), new Color (60, 47, 24),
            "wall");
    public static final TETile WALL2 = new TETile('⊞', new Color(156, 142, 130), new Color (101, 80, 56),
            "wall");
    public static final TETile WALL3 = new TETile('⊞', new Color(213, 192, 173), new Color (164, 129, 90),
            "wall");
    public static final TETile WALL4 = new TETile('⊞', new Color(249, 224, 202), new Color (218, 167, 109),
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(4, 35, 50), Color.black,
            "floor");
    public static final TETile FLOOR0 = new TETile('·', new Color(7, 53, 75), Color.black,
            "floor");
    public static final TETile FLOOR1 = new TETile('·', new Color(19, 88, 122), Color.black,
            "floor");
    public static final TETile FLOOR2 = new TETile('·', new Color(57, 132, 169), Color.black,
            "floor");
    public static final TETile FLOOR3 = new TETile('·', new Color(103, 180, 219), Color.black,
            "floor");
    public static final TETile FLOOR4 = new TETile('·', new Color(146, 255, 249), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, new Color(12, 10, 28), "sky");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('⬤', new Color(252, 255, 186), Color.black,
            "treasure?");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}


