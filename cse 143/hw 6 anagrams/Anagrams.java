/*NingHe Zhang, CSE 143, Section BH.
Programming Assignment #6, 05/18/2014.
An Anagrams object that uses a dictionary to find all anagram phrases 
that match a given word or phrase and print them.
*/
import java.util.*;

public class Anagrams {
   
   private Set<String> allWords;
   private Set<String> wordContained;
   
   //store all the words from the given dictionary in alphabetical order
   //throw an IllegalArgumentException if the dictionary passed is null
   public Anagrams(Set<String> dictionary) {
      if (dictionary == null) {
         throw new IllegalArgumentException();
      }
      allWords = new TreeSet<String>(dictionary);
      wordContained = new TreeSet<String>();
   }
   
   //return all of words from the dictionary that can be made using some
   //or all of the letters in the given phrase in alphabetical order.
   //throw IllegalArgumentException if the phrase passed in is null.
   public Set<String> getWords(String phrase) {
      if (phrase == null) {
         throw new IllegalArgumentException();
      }
      LetterInventory test = new LetterInventory(phrase);
      Set<String> matchedWords = new TreeSet<String>();
      for (String word : allWords) {
         if (test.contains(word)) {
            matchedWords.add(word);
         }
      }
      wordContained = matchedWords;
      return matchedWords;
   }
   
   //print all anagrams that can be formed using all of the 
   //letters of the given phrase.
   //throw IllegalArgumentException if the phrase passed in is empty.
   public void print(String phrase) {
      if (phrase == null) {
         throw new IllegalArgumentException();
      }
      LetterInventory chosen = new LetterInventory(phrase);
      List<String> result = new ArrayList<String>();
      explore(chosen, result, Integer.MAX_VALUE);
   }
   
   //find all anagrams that can be formed using all of the letters of the given
   //chosen phrase, add them to a list of result, and print all possible anagrams
   //with numbers of words less than the given max number.  
   private void explore(LetterInventory chosen, List<String> result, int max) {
      if (chosen.isEmpty()) {
         System.out.println(result);
      } else {
         for (String word : wordContained) {
            if (chosen.contains(word) && (result.size() < max || max == 0)) {
               result.add(word);
               chosen.subtract(word);
               explore(chosen, result, max);
               chosen.add(word);
               result.remove(result.size() - 1);
            }
         }
      }
   }
   
   //print all anagrams that can be formed using all of the letters of the 
   //given phrase and that include at most max words total. if max is 0, print
   //all anagrams regardless of how many words they contain.
   //throw IllegalArgumentException if the phrase is null or max is less than 0.
   public void print(String phrase, int max) {
      if (phrase == null || max < 0) {
         throw new IllegalArgumentException();
      }
      LetterInventory chosen = new LetterInventory(phrase);
      List<String> result = new ArrayList<String>();
      explore(chosen, result, max);
   }
}