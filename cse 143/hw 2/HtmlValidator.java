/*NingHe Zhang, CSE 143, Section BH.
Programming Assignment #2, 04/14/2014.
A HtmlValidator object that examines HTML of whether it represents valid
sequences of tags.It also allows user to add tags, get tags and remove tags 
which contain given element.
*/
import java.util.*;

public class HtmlValidator {
   private Queue<HtmlTag> copy;
   
   //create an empty queue to store tags
   public HtmlValidator() {
      copy = new LinkedList<HtmlTag>();
   }
   
   //giving a sequence of tags, get a copy of them
   //and store them
   //throw IllegalArgumentException if tags passed is null
   public HtmlValidator(Queue<HtmlTag> tags) {
      if(tags == null) {
         throw new IllegalArgumentException();
      }else {
         copy = getCopy(tags);
      }
   }
   
   //giving a sequence of tags, get an entirely separate 
   //copy of it and return the copy sequence
   private Queue<HtmlTag> getCopy(Queue<HtmlTag> name) {
      Queue<HtmlTag> temperate = new LinkedList<HtmlTag>();
      int originalSize = name.size();
      for (int i = 0; i < originalSize; i++) {
         HtmlTag removed = name.remove();
         temperate.add(removed);
         name.add(removed);
      }
      return temperate;
   }
   
   //giving the tag user want to add, add it to the end of 
   //the sequence of tags
   //throw IllegalArgumentException if tag passed is null
   public void addTag(HtmlTag tag) {
      if (tag == null) {
         throw new IllegalArgumentException();
      }else {
         copy.add(tag);
      }
   }
   
   //return a copy of the sequence of tags
   public Queue<HtmlTag> getTags() {
      return getCopy(copy);
   }
   
   //giving the element user want to remove, remove all tags
   //which matches that element
   //throw IllegalArgumentException if element passed is null
   public void removeAll(String element) {
      if (element == null) {
         throw new IllegalArgumentException();
      }else {
         int size = copy.size();
         for (int i = 0; i < size; i++) {
            HtmlTag testedTag = copy.remove();
            if (!testedTag.getElement().equals(element)) {
               copy.add(testedTag);
            }
         }
      }
   }
   
   //print an indented text of the HTML tags. Every opening tag increases 
   //the level of indentation until its closing tag is reached.
   //print error message if a closing tag does not match the most recently
   //opened tag. And print all the tags still open after reaching
   //the end of the input.
   public void validate() {
      Stack<HtmlTag> s = new Stack<HtmlTag>();
      int size = copy.size();
      int indentation = 0;
      for (int i = 0; i < size; i++) {
         HtmlTag getTag = copy.remove();
         if(getTag.isOpenTag()) {
            for (int j = 0; j < indentation; j++) {
               System.out.print(" ");
            }
            indentation += 4;
            if(getTag.isSelfClosing()) {
               indentation -= 4;
            }else {
               s.push(getTag);
            }
            System.out.println(getTag);
         }else{
            if(!s.isEmpty()) {
               HtmlTag tagInStack = s.pop();
               if(!getTag.matches(tagInStack)) {
                  System.out.println("ERROR unexpected tag: " + getTag);
                  s.push(tagInStack);
               }else {
                  indentation -= 4;
                  for (int j = 0; j < indentation; j++) {
                     System.out.print(" ");
                  }
                  System.out.println(getTag);
               }
            }else {
               System.out.println("ERROR unexpected tag: " + getTag);
            }
         }
         copy.add(getTag);
      }
      while(!s.isEmpty()) {
         System.out.println("ERROR unclosed tag: " + s.pop());
      }
   }
}
