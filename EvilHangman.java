import java.io.*;
import java.util.*;

public class EvilHangman
{

   public static Map<String, Integer> readDictionary() throws FileNotFoundException
   {
      Map<String, Integer> words = new TreeMap<>();
      Scanner file = new Scanner( new File("dictionary.txt"));
      while(file.hasNextLine())
      {
         String word = file.nextLine();
         int wordLength = word.length();
         words.put(word, wordLength);
      }
      return words;
   }

   public static Set<Integer> findValidLengths(Map<String, Integer> words)
   {
      Set<Integer> validLengths = new TreeSet<>();
      for(String word : words.keySet())
      {
         validLengths.add(words.get(word));
      }
      return validLengths;
   }

   public static int getValidWordLength(Set<Integer> validWordLengths)
   {
      
      Scanner consoleInput = new Scanner(System.in);
      boolean knowValidLength = false;
      int wordLength = -1;
      while(!knowValidLength)
      {
         System.out.print("Enter a valid word length: ");
         wordLength = consoleInput.nextInt();
         knowValidLength = validWordLengths.contains(wordLength);
      }
      consoleInput.nextLine();
      return wordLength;
   }

   public static int getValidNumberOfGuesses()
   {
      
      Scanner consoleInput = new Scanner(System.in);
      boolean validNumberOfGuesses = false;
      int numberOfGuesses = -1;
      while(!validNumberOfGuesses)
      {
         System.out.print("Enter a valid number of guesses: ");
         numberOfGuesses = consoleInput.nextInt();
         validNumberOfGuesses = (numberOfGuesses > 0);
      }
      consoleInput.nextLine();
      return numberOfGuesses;
   }

   public static boolean getYNInput(String prompt)
   {
      
      Scanner consoleInput = new Scanner(System.in);
      boolean validInput = false;
      String userInput = "";
      while(!validInput)
      {
         System.out.print(prompt);
         userInput = consoleInput.nextLine().toLowerCase();
         validInput = (userInput.equals("y") || userInput.equals("n"));
      }
      return userInput.equals("y");
   }

   public static Map<Integer, Set<String>> reverseWordsAndLengths(Map<String, Integer> wordMap)
   {
      Map<Integer, Set<String>> reversedMap = new TreeMap<>();
      for(String word : wordMap.keySet())
      {
         int wordLength = wordMap.get(word);
         if(reversedMap.containsKey(wordLength))
         {
            Set<String> setOfLengthN = reversedMap.get(wordLength);
            setOfLengthN.add(word);
            reversedMap.put(wordLength, setOfLengthN);
         }
         else
         {
            Set<String> setOfLengthN = new TreeSet<>();
            setOfLengthN.add(word);
            reversedMap.put(wordLength, setOfLengthN);
         }
      }
      return reversedMap;
   }

   public static Set<String> getWordsOfLength(Map<String, Integer> wordMap, int wordLength)
   {
      return reverseWordsAndLengths(wordMap).get(wordLength);
   }

   public static char getValidLetter(Set<Character> guessedLetters)
   {
      char chosenLetter = 0;
      boolean validLetterChosen = false;
      Scanner consoleInput = new Scanner(System.in);
      while(!validLetterChosen)
      {
         System.out.print("Enter a valid letter: ");
         String inputData = consoleInput.nextLine();
         chosenLetter = inputData.charAt(0);
         validLetterChosen = guessedLetters.add(chosenLetter);
      }
      return chosenLetter;
   }

   public static String createBaseFamily(int wordLength)
   {
      String baseFamily = "";
      for(int i = 0; i < wordLength; i++)
      {
         baseFamily = baseFamily + "-";
      }
      return baseFamily;
   }

	public static String getFamily(String word, char c)
   {
      int wordLength = word.length();
      String baseFamily = createBaseFamily(wordLength);
      if(word.indexOf(c) == -1)
      {
         return baseFamily;
      }
      char[] familyArray = baseFamily.toCharArray();
      char[] wordArray = word.toCharArray();
      for(int i = 0; i < wordArray.length; i++)
      {
         if(wordArray[i] == c)
         {
            familyArray[i] = c;
         }
      }
      String family = new String(familyArray);
      return family;
   }

   public static Map<String, String> getAllWordFamilies(Set<String> wordSet, char letter)
   {
      Map<String, String> wordFamilyMap = new TreeMap<>();
      for(String word : wordSet)
      {
         wordFamilyMap.put(word, getFamily(word, letter));
      }
      return wordFamilyMap;
   }

   public static Map<String, Set<String>> reverseWordsAndFamilies(Map<String, String> wordMap)
   {
      Map<String, Set<String>> reversedMap = new TreeMap<>();
      for(String word : wordMap.keySet())
      {
         String wordFamily = wordMap.get(word);
         if(reversedMap.containsKey(wordFamily))
         {
            Set<String> setOfFamilyX = reversedMap.get(wordFamily);
            setOfFamilyX.add(word);
            reversedMap.put(wordFamily, setOfFamilyX);
         }
         else
         {
            Set<String> setOfFamilyX = new TreeSet<>();
            setOfFamilyX.add(word);
            reversedMap.put(wordFamily, setOfFamilyX);
         }
      }
      return reversedMap;
   }

   public static String chooseBestFamily(Map<String, Set<String>> mapOfFamilies)
   {
      String bestFamily = "";
      int familyLength = -1;
      for(String family : mapOfFamilies.keySet())
      {
         Set<String> setOfFamilyX = mapOfFamilies.get(family);
         if(setOfFamilyX.size() > familyLength)
         {
            familyLength = setOfFamilyX.size();
            bestFamily = family;
         }
      }
      return bestFamily;
   }

   public static Set<String> getWordsFromFamily(Map<String, Set<String>> mapOfFamilies, String family)
   {
      return mapOfFamilies.get(family);
   }

   public static String combineFamilies(String family1, String family2)
   {
      char[] family1Array = family1.toCharArray();
      char[] family2Array = family2.toCharArray();
      char[] familyArray = createBaseFamily(family1.length()).toCharArray();
      for(int i = 0; i < familyArray.length; i++)
      {
         if(family1Array[i] != '-')
         {
            familyArray[i] = family1Array[i];
         }
         else if(family2Array[i] != '-')
         {
            familyArray[i] = family2Array[i];
         }
      }
      String combinedFamily = new String(familyArray);
      return combinedFamily;
   }

   public static boolean checkVictory(String family)
   {
      boolean gameWon = true;
      char[] familyArray = family.toCharArray();
      for(char letter : familyArray)
      {
         if(letter == '-')
         {
            return false;
         }
      }
      return gameWon;
   }

   public static void main(String[] args) throws FileNotFoundException
   {
      boolean running = true;
      while(running)
      {
         boolean gameWon = false;

         Map<String, Integer> allWords = readDictionary();

         Set<Integer> validWordLengths = findValidLengths(allWords);

         int wordLength = getValidWordLength(validWordLengths);

         Set<String> validWords = getWordsOfLength(allWords, wordLength);

         int numberOfGuesses = getValidNumberOfGuesses();

         boolean debugMode = getYNInput("Activate debug mode? (y/n): ");

         boolean inGame = true;

         Set<Character> guessedLetters = new TreeSet<>();

         String cumulativeFamily = createBaseFamily(wordLength);

         System.out.println("\n");

         while(inGame)
         {
            char letter = getValidLetter(guessedLetters);
           
            Map<String, String> allWordsToFamilies = getAllWordFamilies(validWords, letter);

            Map<String, Set<String>> allFamiliesToWords = reverseWordsAndFamilies(allWordsToFamilies);

            String bestFamily = chooseBestFamily(allFamiliesToWords);

            validWords = getWordsFromFamily(allFamiliesToWords, bestFamily);

            cumulativeFamily = combineFamilies(cumulativeFamily, bestFamily);

            gameWon = checkVictory(cumulativeFamily);

            int remainingWords = validWords.size();

            System.out.println(cumulativeFamily);

            System.out.println("Guessed letters: " + guessedLetters.toString());

            if(debugMode)
            {
               System.out.println("Words remaining: " + remainingWords);
            }

            if(cumulativeFamily.indexOf(letter) == -1)
            {
               numberOfGuesses--;
            }

            if(numberOfGuesses <= 0 || gameWon == true)
            {
               inGame = false;
            }
            System.out.println("" + numberOfGuesses + " guesses remaining\n");
         }

         if(gameWon)
         {
            System.out.println("Congratulations! You won!");
            System.out.println("The word was " + cumulativeFamily + "!");
         }

         else
         {
            System.out.println("Sorry, you lost.");
            String correctWord = validWords.iterator().next();
            System.out.println("The correct word was " + correctWord);
         }

         boolean playAgain = getYNInput("\nPlay again? (y/n): ");
         if(!playAgain)
         {
            running = false;
         }
      }
   }
}