/*NingHe Zhang, CSE 143, Section BH.
Programming Assignment #4, 04/27/2014.
A GrammarSolver object that manipulates a grammar according to the symbols
and rules passed in. It generates random elements of the grammar as output.
*/
import java.util.*;

public class GrammarSolver {
   
   private Map<String, String[]> grammarRules;
   
   //break apart the rules passed in, and store them into
   //a map, where one symbol corresponds to one rule.
   //throw IllegalArgumentException if the list is null or empty
   //or if the grammar contains more than one line for the same non-terminal
   public GrammarSolver(List<String> rules) {
      grammarRules = new TreeMap<String, String[]>();
      if(rules == null || rules.size() == 0) {
         throw new IllegalArgumentException();
      }
      for(int i = 0; i < rules.size(); i++) {
         String line = rules.get(i);
         String[] splited = line.split("::=");
         if(!grammarRules.containsKey(splited[0])) {
            String[] splitRules = splited[1].split("[|]");
            grammarRules.put(splited[0], splitRules);
         }else{
            throw new IllegalArgumentException();
         }
      }
   }
   
   //return true if the given symbol is a non-terminal in the
   //grammar and false otherwise.
   //throw IllegalArgumentException if the string passed in is null or empty
   public boolean contains(String symbol) {
      if(symbol == null || symbol.length() == 0) {
         throw new IllegalArgumentException();
      }
      return grammarRules.containsKey(symbol);
   }
   
   //return all non-terminal symbols of grammar as a sorted set of strings
   public Set<String> getSymbols() {
      return grammarRules.keySet();
   }
   
   //use grammar to generate a random occurrence of the given symbol
   //and return it.Return the symbol passed in if it is a terminal, and
   //apply rules randomly if it is a non-terminal until reach a terminal.
   //throw IllegalArgumentException if the string is null or has a length of 0.
   public String generate(String symbol) {
      if(symbol == null || symbol.length() == 0) {
         throw new IllegalArgumentException();
      }
      if(!grammarRules.containsKey(symbol)) {
         return symbol;
      }else {
         Random num = new Random();
         int random = num.nextInt(grammarRules.get(symbol).length);
         String[] nextLevel = grammarRules.get(symbol)[random].trim().split("[ \t]+");
         String result = "";
         for (int i = 0; i < nextLevel.length; i++) {
            if(!grammarRules.containsKey(nextLevel[i])) {
               result += nextLevel[i] + " ";
            }else {
               result += generate(nextLevel[i]).trim() + " ";
            }
         }
         return result;
      }
   }
}