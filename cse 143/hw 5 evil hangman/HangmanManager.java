/*NingHe Zhang, CSE 143, Section BH.
Programming Assignment #5, 05/06/2014.
A HangmanManager object that manage the state of the game.It records how
many guesses the player has left, sorts each word from the given dictionary
into different groups of word families according to its patterns, 
and chooses the largest of the remaining word families to continue the game.
*/
import java.util.*;

public class HangmanManager {
   
   private Set<String> targetWord;
   private SortedSet<Character> charGuessed;
   private int guessLeft;
   private String pattern;
   
   //giving a dictionary of words,a target word length and the maximum
   //number of wrong guesses the player is allowed to make,initialize
   //the state of the game.Pick out all words that are of the given length.
   //throw an IllegalArgumentException if length is less than 1 or if
   //max is less than 0.
   public HangmanManager(List<String> dictionary, int length, int max) {
      if(length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      targetWord = new TreeSet<String>();
      for(String possibleWord : dictionary) {
         if (possibleWord.length() == length) {
            targetWord.add(possibleWord);
         }
      }
      pattern = "";
      for (int i = 0; i < length; i++) {
         pattern += "- ";
      }
      guessLeft = max;
      charGuessed = new TreeSet<Character>();
   }
   
   //return the current set of words being considered by the HangmanManager
   public Set<String> words() {
      return targetWord;
   }
   
   //return how many guesses the player has left
   public int guessesLeft() {
      return guessLeft;
   }
   
   //return current set of letters that have been guessed by the user
   public SortedSet<Character> guesses() {
      return charGuessed;
   }
   
   //return the current pattern to be displayed for the hangman game
   //taking into account guesses that have been made.Letters that have not
   //been guessed displayed as a dash.
   //throw an IllegalStateException if the set of words is empty.
   public String pattern() {
      if(targetWord.isEmpty()) {
         throw new IllegalStateException();
      }
      return pattern;
   }
   
   //giving the guess made by user,decide what set of words to use going
   //forward.Return the number of occurrences of the guessed letter in
   //the new pattern and update the number of guesses left.
   //throw an IllegalStateException if the number of guesses left is not at
   //least 1 or if the list of words is empty.
   //throw an IllegalArgumentException if character being guessed was guessed
   //previously.
   public int record(char guess) {
      if(guessLeft < 1|| targetWord.isEmpty()) {
         throw new IllegalStateException();
      }
      if(charGuessed.contains(guess)) {
         throw new IllegalArgumentException();
      }
      charGuessed.add(guess);
      Map<String, Set<String>> words = new TreeMap<String, Set<String>>();
      recordPatterns(guess, words);
      int totalRight = choosePattern(guess, words);
      if(totalRight == 0) {
         guessLeft--;
      }
      return totalRight;
   }
   
   //giving the guess made by user and relationship between words and their patterns,
   //go through all the possible words and store them in different groups corresponding
   //to their unique patterns.
   private void recordPatterns(char guess, Map<String, Set<String>> words) {
      for(String oneWord : targetWord) {
         String currentPattern = "";
         for(int i = 0; i < pattern.length() / 2; i++) {
            if(oneWord.charAt(i) == guess) {
               currentPattern += guess + " ";
            }else {
               currentPattern += pattern.charAt(i * 2) + " ";
            }
         }
         if(!words.containsKey(currentPattern)) {
            words.put(currentPattern, new TreeSet<String>());
            Set<String> patternFamily = words.get(currentPattern);
            patternFamily.add(oneWord);
         } else {
            words.get(currentPattern).add(oneWord);
         }
      }
   }
   
   //giving the guess made by user and words which are divided into groups
   //according to patterns, choose the pattern which has the largest of the
   //remaining word family.
   //return the the number of occurrences of the guessed letter in the new pattern
   private int choosePattern(char guess, Map<String, Set<String>> words) {
      int max = 0;
      for (String eachPattern : words.keySet()) {
         int size = words.get(eachPattern).size();
         if (size > max) {
            max = size;
            pattern = eachPattern;
         }
      }
      targetWord = words.get(pattern);
      int total = 0;
      for(int i = 0; i < pattern.length(); i++) {
         if(pattern.charAt(i) == guess) {
            total++;
         }
      }
      return total;
   }
}