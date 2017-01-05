import java.util.*;
public class ArrayListDemo {
   public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<Integer>();
    list.add(72);
    list.add(20);
     for (int i = list.size() - 2; i > 0; i--) { 
        int a = list.get(i); 
        int b = list.get(i + 1); 
        list.set(i, a + b); 
    } 
    System.out.println(list); 
 
    }
   }