/*NingHe Zhang, CSE 143, Section BH.
Programming Assignment #3, 04/20/2014.
A AssassinManager object that keeps track of and print out who is stalking whom
and the history of who killed whom. Report the winner and whether or not the game
is ended.
*/
import java.util.*;

public class AssassinManager {
   
   private AssassinNode killRing;
   private AssassinNode graveYard;
   
   //create a list of killRing to store the names of players in the game
   //create a list of graveYard to keep track of who is being killed
   //throw IllegalArgumentException if the names passed in is empty or null
   public AssassinManager(ArrayList<String> names) {
      killRing = new AssassinNode(null);
      graveYard = new AssassinNode(null);
      AssassinNode temperate = new AssassinNode(null);
      if(names == null || names.size() == 0) {
         throw new IllegalArgumentException();
      }
      killRing = new AssassinNode(names.get(0));
      temperate = killRing;
      for (int i = 1; i < names.size(); i++) {
         temperate.next = new AssassinNode(names.get(i));
         temperate = temperate.next;
      }
   }
   
   //print the names of people in the kill ring
   public void printKillRing() {
      AssassinNode temp = killRing;
      while(temp.next != null) {
         String murder = temp.name;
         String victim = temp.next.name;
         System.out.println("  " + murder + " is stalking " + victim);
         temp = temp.next;
      }
      System.out.println("  " + temp.name + " is stalking " + killRing.name);
   }
   
   //print the names of people in the graveyard in the opposite
   //order in which they were killed
   public void printGraveyard() {
      if(graveYard != null) {
         AssassinNode temp = graveYard;
         while(temp.name != null) {
            System.out.println("  " + temp.name + " was killed by " + temp.killer);
            temp = temp.next;
         }
      }
   }
   
   //return true if the given name is in the current kill ring
   //and false otherwise
   public boolean killRingContains(String name) {
      AssassinNode temp = killRing;
      while(temp != null) {
         String nameInList = temp.name;
         if(nameInList.equalsIgnoreCase(name)) {
            return true;
         }
         temp = temp.next;
      }
      return false;
   }
   
   //return true if the given name is in the current graveyard
   //and false otherwise
   public boolean graveyardContains(String name) {
      AssassinNode temp = graveYard;
      while(temp != null) {
         String nameInList = temp.name;
         if(name.equalsIgnoreCase(nameInList)) {
            return true;
         }
         temp = temp.next;
      }
      return false;
   }
   
   //return true if the game is over and false otherwise
   public boolean isGameOver() {
      if(killRing.next == null) {
         return true;
      }
      return false;
   }
   
   //return the winner of the game, or null if the game is not over.
   public String winner() {
      if(!isGameOver()) {
         return null;
      }
      return killRing.name;
   }
   
   //giving the name of person being killed, transfer this person
   //from the kill ring to the front of the graveyard.
   //throw IllegalStateException if the game is over
   //throw IllegalArgumentException if given name is not part of the kill ring
   public void kill(String name) {
      if(isGameOver()) {
         throw new IllegalStateException();
      }
      AssassinNode temp = killRing;
      AssassinNode current = null;
      if(temp.name.equalsIgnoreCase(name)) { //first person in killRing is killed
         while(temp.next != null) {
            temp = temp.next;              //forward to last person who is the killer
         }
         killRing.killer=temp.name;
         current = killRing;
         killRing = killRing.next;
         current.next = null;
      }else {
         boolean test = false;                   //true if find the name
         while(!test && temp.next != null && temp.next.next != null) {
            if(temp.next.name.equalsIgnoreCase(name)) {
               temp.next.killer = temp.name;
               current = temp.next;        //store person being killed in current node
               temp.next = temp.next.next;
               current.next = null;
               test = true;
            }
            temp = temp.next;
         }
         if(!test && temp.next.name.equalsIgnoreCase(name)) { //last person in killRing 
            temp.next.killer = temp.name;                     //is killed
            current = temp.next;
            temp.next = null;
         }
      }
      if(current == null) {
         throw new IllegalArgumentException();
      }
      if(graveYard == null) {
         graveYard = current;
      }else {
         current.next = graveYard;
         graveYard = current;
      }
   }
}