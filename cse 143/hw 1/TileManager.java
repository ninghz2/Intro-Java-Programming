/*NingHe Zhang, CSE 143, Section BH.
Programming Assignment #1, 04/06/2014.
A TileManager object that allows user to manage the tiles in a z-ordering list.
Actions includes adding new tiles, moving tile to the very top,
moving tile to the very bottom, deleting tiles, and shuffling tiles.
*/

import java.awt.*;
import java.util.*;

public class TileManager {
   private ArrayList<Tile> tiles;
   
   //store tiles in a z-ordering list
   public TileManager() {
      tiles = new ArrayList<Tile>();
   }
   
   //giving a new tile, add it to the end of z-ordering list,
   //which will be drawed at last
   public void addTile(Tile rect) {
      tiles.add(rect);
   }
   
   //draw all the tiles in the list from left to right
   //using the given graphical pen
   public void drawAll(Graphics g) {
      for (int i = 0; i < tiles.size(); i++) {
         tiles.get(i).draw(g);
      }
   }
   
   //move the topmost of tiles, touched by the given x and y
   //coordinates the user clicked, to the end of list.
   public void raise(int x, int y) {
      int index = testLocation(x, y);
      if (index >= 0) {
         Tile changed = tiles.get(index);
         tiles.remove(index);
         tiles.add(changed);
      }
   }
   
   //Test each tile from right to left in the list whether or
   //not it is touched by the given X and Y coordinates the user
   //clicked. Return the index of first tile that is touched.
   //Return -1 if no tile is touched.
   private int testLocation(int startX, int startY) {
      for (int i = tiles.size() - 1; i >= 0; i--) {
         int tileX = tiles.get(i).getX();
         int tileY = tiles.get(i).getY();
         int tileWidth = tiles.get(i).getWidth();
         int tileHeight = tiles.get(i).getHeight();
         if ((startX >= tileX && startX <= tileX + tileWidth) &&
            (startY >= tileY && startY <= tileY + tileHeight)) {
            return i;
         }
      }
      return -1;
   }
   
   //move the topmost of tiles, touched by the given x and y
   //coordinates the user clicked, to the very bottom of list.
   public void lower(int x, int y) {
      int index = testLocation(x, y);
      if (index >= 0) {
         Tile changed = tiles.get(index);
         tiles.remove(index);
         tiles.add(0, changed);
      }
   }
   
   //delete the topmost of tiles, touched by the given x and y
   //coordinates the user clicked, from the list.
   public void delete(int x, int y) {
      int index = testLocation(x, y);
      if (index >= 0) {
         tiles.remove(index);
      }
   }
   
   //delete all the tiles touched by the given x and y
   //coordinates the user clicked from the list.
   public void deleteAll(int x, int y) {
      int index = testLocation(x, y);
      while (index != -1) {
         tiles.remove(index);
         index = testLocation(x, y);
      }
   }
   
   //reorder the tiles in the list into a random order and
   //move every tile on the screen to a new random position.
   //the every pixel of the tile is within the given width and height.
   public void shuffle(int width, int height) {
      Collections.shuffle(tiles);
      Random rand = new Random();
      for (int i = 0; i < tiles.size(); i++) {
         int newX = rand.nextInt(width - tiles.get(i).getWidth() + 1);
         int newY = rand.nextInt(height - tiles.get(i).getHeight() + 1);
         tiles.get(i).setX(newX);
         tiles.get(i).setY(newY);
      }
   }
}