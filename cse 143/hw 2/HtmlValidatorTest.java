// This testing program stub creates a queue of HTML tags 
// in a valid sequence.
// You may use this as a starting point for testing
// your removeAll method.
import java.util.*;

public class HtmlValidatorTest {
	public static void main(String[] args) {
		// <b>Hi</b><br/>
		Queue<HtmlTag> tags = new LinkedList<HtmlTag>();
		tags.add(new HtmlTag("b", true));      // <b>
		tags.add(new HtmlTag("b", false));     // </b>
		tags.add(new HtmlTag("br"));           // <br/>
		
		HtmlValidator validator = new HtmlValidator(tags);
      validator.removeAll("b");
      System.out.println(validator.getTags());    //remove "b", <br/> should be left
      validator.removeAll("br");                //remove "br", output should be empty
      System.out.println(validator.getTags());
      
      tags.add(new HtmlTag("pr", true));
      tags.add(new HtmlTag("pr", false));
      tags.add(new HtmlTag("p", true));
      tags.add(new HtmlTag("prl", false));  
      HtmlValidator validatorTest2 = new HtmlValidator(tags);          
      validatorTest2.removeAll("pr");  //remove "pr", <b> </b> <br/> <p> </prl> should be left
      System.out.println(validatorTest2.getTags()); 
            
      validatorTest2.removeAll("");         //remove an empty string
      System.out.println(validatorTest2.getTags());  //should have no change to output
      
      validatorTest2.removeAll("prl");     //remove "prl", <b> </b> <br/> <p> should be left
      System.out.println(validatorTest2.getTags());
      
      validatorTest2.removeAll("/b");     //element is "/b", no change to output
      System.out.println(validatorTest2.getTags());
      
      validatorTest2.removeAll("a");      //no change to output
      System.out.println(validatorTest2.getTags());
            
      validatorTest2.removeAll(null);            //should throw IllegalArgumentException
      System.out.println(validatorTest2.getTags());
   }
}
