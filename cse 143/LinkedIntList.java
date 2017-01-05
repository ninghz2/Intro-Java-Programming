public class LinkedIntList {
   private ListNode front;

   // Initializes a new empty list.
   public LinkedIntList() {
      front = null;
   }

   // Build a new list with nodes of values n to 0.
   // Pre: n >= 0
   public LinkedIntList(int n) {
      if (n < 0) {
         throw new IllegalArgumentException();
      }
      for (int i = 0; i <= n; i++) {
         front = new ListNode(i, front);

         // same as:
         // ListNode temp = new ListNode(i, front);
         // front = temp;
      }

      // we started by writing out some examples to find pattern
      // // n = 1
      // temp = new ListNode(1, front);
      // front = temp;
      //
      // // n = 2
      // temp = new ListNode(2, front);
      // front = temp;
   }
    public void add(int value) {
      if (front == null) {
         front = new ListNode(value);
      } else {
         ListNode current = front;
         while (current.next != null) {
            current = current.next;
         }

         current.next = new ListNode(value);
      }
   }
   public String toString() {
      if (front == null) {
         return "[]";
      } else {
         String result = "[" + front.data;
         ListNode current = front.next;
         while (current != null) {
            result += ", " + current.data;
            current = current.next;
         }
         result += "]";
         return result;
      }
   }
   public boolean bubble() {
      boolean test = false;
      if(front != null && front.next != null) {
         ListNode curr = front;
         ListNode next = front.next;
         if(curr.data > next.data) {
            test = true;
            curr.next = next.next;
            next.next = curr;
            front = next;
         }
         next = curr.next;
         ListNode ahead = front;
         while(curr != null && curr.next != null && curr.next.next !=null) {
            if(curr.data > next.data) {
            test = true;
            curr.next = next.next;
            next.next = curr;
            ahead.next = next;
            ahead = ahead.next;
         }else {
            curr = curr.next;
       }
         next = curr.next;
      }
    }
    return test;  

   }
   }
   
   