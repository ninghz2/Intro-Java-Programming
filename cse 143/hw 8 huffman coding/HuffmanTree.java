/*NingHe Zhang, CSE 143, Section BH.
Programming Assignment #8, 06/02/2014.
A HuffmanTree object that represents the overall tree of character frequecies
and traces the tree to convert the plain text to huffman code. It functions like
zip which can also decode the compressed file to a text file. 
*/
import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode overallRoot;
   
   //a inner HuffmanNode class which store information about one character.
   private class HuffmanNode implements Comparable<HuffmanNode> {
      private int frequency;
      private char character;
      private HuffmanNode left;
      private HuffmanNode right;
      
      //construct a HuffmanNode with given frequency, character and links.
      public HuffmanNode(int frequency, char character, HuffmanNode left, HuffmanNode right) {
         this.frequency = frequency;
         this.character = character;
         this.left = left;
         this.right = right;
      }
      
      //construct a HuffmanNode with given frequency, character
      public HuffmanNode(int frequency, char character) {
         this(frequency, character, null, null);
      }
      
      //return frequency of a character
      public int getFrequency() {
         return frequency;
      }
      
      //return character
      public char getChar() {
         return character;
      }
      
      //make HuffmanNode comparable, which has lower frequency is less than the higher one
      public int compareTo(HuffmanNode other) {
         return frequency - other.frequency;
      }
      
      //return String to represent content in the HuffmanNode
      public String toString() {
         return "(" + frequency + ", " + character + ")";
      }
   }
   
   //given an array of frequencies of characters, build Huffman tree according 
   //to frequencies
   public HuffmanTree(int[] counts) {
      Queue<HuffmanNode> nodes = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < counts.length; i++) {
         if (counts[i] != 0) {
            HuffmanNode node  = new HuffmanNode(counts[i], (char) i);
            nodes.add(node);
         }
      }
      HuffmanNode EOF = new HuffmanNode(1, (char) counts.length);
      nodes.add(EOF);
      while(nodes.size() != 1) {
         HuffmanNode first = nodes.remove();
         HuffmanNode second = nodes.remove();
         HuffmanNode newNode = new HuffmanNode(first.getFrequency() + second.getFrequency(),' ',
         first, second);
         nodes.add(newNode);
      }
      overallRoot = nodes.remove();
   }
   
   //write the tree to the given output stream in the standard format contains 
   //a sequence of line pairs where each pair represents one leaf of the tree.  
   public void write(PrintStream output) {
      write(output, overallRoot, "");
   }
   
   //write the tree to the given output, trace through all the root node in the tree,
   //and store the code in the result and print it in the end.The first line of each
   //pair contain the ASCII value of the character stored in that leaf. The second 
   //line have the code for that character.
   private void write(PrintStream output, HuffmanNode root, String result) {
      if (root.left == null && root.right == null) {
         output.println((int) root.getChar());
         output.println(result);
      } else {
         write(output, root.left, result + "0");
         write(output, root.right, result + "1");
      }
   }
   
   //reconstruct a tree from a code input file which contains a tree in standard format
   public HuffmanTree(Scanner input) {
      while (input.hasNextLine()) {
         int value = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = helper(value, code, 0, overallRoot);
      }
   }
   
   //given value and code of character, read one index of the code each time(0 to left, 1 to right),
   //trace all over the root and put the node which represent the character in the right place
   private HuffmanNode helper(int value, String code, int index, HuffmanNode root) {
      if (index == code.length()) {
         root = new HuffmanNode(-1, (char) value);
      } else {
         if (root == null) {
            root = new HuffmanNode(-1, '\0');
         }
         if (code.charAt(index) == '0') {
            root.left = helper(value, code, index + 1, root.left);
         }else {
            root.right = helper(value, code, index + 1, root.right);
         }
      }
      return root;
   }
   
   //read individual bits from the input stream and write the corresponding characters
   //to the output.Stop reading when it encounters a character with value equal to the 
   //eof parameter.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode root = overallRoot;
      while ((int)root.getChar() != eof) {
         if(root.left == null && root.right == null) {
            output.write((int) root.getChar());
            root = overallRoot;
         }
         int bit = input.readBit();
         if (bit == 0) {
            root = root.left;
         } else {
            root = root.right;
         }
      }
   }
}