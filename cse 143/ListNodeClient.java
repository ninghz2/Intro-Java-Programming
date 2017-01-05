public class ListNodeClient {
   public static void main(String[] args) {
      // for jGRASP debugger (so we can see other lists' names)
     LinkedIntList list = new LinkedIntList();
     list.add(5);
     list.add(1);
     list.add(4);
     list.add(2);
     list.add(8);
      System.out.println(list);
      list.bubble();
      System.out.println(list);
    }
    }