import java.util.*;
public class test { 
   public static void main(String[] args) {
    ArrayList<String> nameList = new ArrayList<String>();
    nameList.add("nina");
    nameList.add("tina");
    nameList.add("bina");
    nameList.add("vina");



    AssassinManager manager = new AssassinManager(nameList);
    manager.printKillRing();
   }

}