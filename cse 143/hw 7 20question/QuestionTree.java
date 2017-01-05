/*NingHe Zhang, CSE 143, Section BH.
Programming Assignment #7, 05/27/2014.
A QuestionTree object that constructs and keeps track of a binary tree whose
nodes represent questions and answers.It also traverses the tree to ask the 
human player a series of questions.It can also ask player to add new questions
for future games and record the number of games and how many times computer win.  
*/
import java.io.*;
import java.util.Scanner;

public class QuestionTree {
   
   private int totalGame;
   private int gameWon;
   private QuestionNode overallRoot;
   private UserInterface ui;
   
   //construct a new question tree with a single answer "computer",
   //and use giving user interface for printing out messages and 
   //asking questions in the game.
   //throw IllegalArgumentException if user interface passed in is null.
   public QuestionTree(UserInterface ui) {
      if (ui == null) {
         throw new IllegalArgumentException();
      }
      totalGame = 0;
      gameWon = 0;
      overallRoot = new QuestionNode("computer");
      this.ui = ui;
   }
   
   //play one complete guessing game with user, asking yes/no question until
   //reaching an answer object to guess.
   public void play() {
      totalGame++;
      overallRoot = play(overallRoot, ui);
   }
   
   //using giving user interface, asking yes/no question begins with the given
   //root node of the tree and ends upon reaching an answer node.
   //If the computer loses the game, ask user what object he/she was thinking of,
   //a question distinguish that object from the player's guess, and yes or no
   //answer for that question and store those new information.
   //return the every root node after changing.  
   private QuestionNode play(QuestionNode root, UserInterface ui) {
      if(root.yes == null && root.no == null) {
         ui.print("Would your object happen to be " + root.text + "?");
         if (ui.nextBoolean()) {
            ui.println("I win!");
            gameWon++;
         } else {
            ui.print("I lose. What is your object?");
            String answer = ui.nextLine();
            ui.print("Type a yes/no question to distinguish your item from "
            + root.text +":");
            String newQuestion = ui.nextLine();
            ui.print("And what is the answer for your object?");
            if (ui.nextBoolean()) {
               root.yes = new QuestionNode(answer);
               root.no = new QuestionNode(root.text);
            } else {
               root.yes = new QuestionNode(root.text);
               root.no = new QuestionNode(answer);
            }
            root.text = newQuestion;
         }
      } else {
         ui.print(root.text);
         if (ui.nextBoolean()) {
            root.yes = play(root.yes, ui);
         } else {
            root.no = play(root.no, ui);
         }
      }
      return root;
   }
   
   //store the current tree state to a giving output file.
   //throw IllegalArgumentException if the output file passed in is null.
   public void save(PrintStream output) {
      if (output == null) {
         throw new IllegalArgumentException();
      }
      save(output, overallRoot);
   }
   
   //store the entire tree from root node to a giving output file which is specified 
   //by a sequence of lines, one for each node.
   //Each line starts with either Q to indicate a question node or A 
   //to indicate an answer.After these is the text of question or answer.
   private void save(PrintStream output, QuestionNode root) {
      if (root.yes == null && root.no == null) {
         output.println("A:" + root.text);
      } else {
         output.println("Q:" + root.text);
         save(output, root.yes);
         save(output, root.no);
      }
   }
   
   //using input scanner, reads from a file and replace the current tree nodes
   //with a new tree using the information in the file.
   //throw IllegalArgumentException if scanner passed in is null.
   public void load(Scanner input) {
      if (input == null) {
         throw new IllegalArgumentException();
      }
      overallRoot = loadHelper(input);
   }
   
   //using input scanner, replace the current tree by reading another tree 
   //from a file. return the root node after changing.
   private QuestionNode loadHelper(Scanner input) {
      String line = input.nextLine();
      String type = line.substring(0,1);
      QuestionNode root = new QuestionNode(line.substring(2));
      if (type.equals("Q")) {
         root.yes = loadHelper(input);
         root.no = loadHelper(input);
      }
      return root;
   }
   
   //return the total number of games that have been played on this tree so far.
   public int totalGames() {
      return totalGame;
   }
   
   //return the number of games the computer has won by correctly guessing 
   //your object.
   public int gamesWon() {
      return gameWon;
   }
}