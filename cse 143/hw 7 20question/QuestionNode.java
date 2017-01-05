//QuestionNode object stores a single node of a binary tree of strings.

public class QuestionNode {
   public String text; //text stored in this node
   public QuestionNode yes; // reference to left subtree
   public QuestionNode no; // reference to right subtree
   
   //construct a leaf node with the given text
   public QuestionNode(String text) {
      this(text, null, null);
   }
   
   //construct a leaf or branch node with the given text and links.
   public QuestionNode(String text, QuestionNode yes, QuestionNode no) {
      this.text = text;
      this.yes = yes;
      this.no = no;
   }
}