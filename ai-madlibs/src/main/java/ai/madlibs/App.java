package ai.madlibs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Arrays;
// import java.util.Arrays;
// import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
// import com.lilittlecat.chatgpt.offical.ChatGPT;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class App {

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    System.out.println(
        "Welcome to Madlibs AI! Would you like to read in a completed madlib or generate a new one? (Press 1 for existing, or any other key for new)");
    if (scanner.nextLine().trim().equals("1")) {
      System.out.println("What's the file name of the existing madlib?");
      String readName = scanner.nextLine();
      if (!readName.isEmpty()) {
        StringBuilder existingMadlib = new StringBuilder();
        try {
          File targetFile = new File(readName);
          Scanner readFile = new Scanner(targetFile);
          while (readFile.hasNextLine()) {
            existingMadlib.append(readFile.nextLine());
          }
          readFile.close();
          System.out.println();
          System.out.println(existingMadlib.toString());
        } catch (IOException e) {
          System.out.println(
              "Error has occured. File likely does not exist. Exiting...");
          scanner.close();
          return;
        }
      } else {
        System.out.println("Invalid file name. Exiting...");
        scanner.close();
        return;
      }
      System.exit(0);
    } else {
      // read in API key for OpenAI from properties file
      // Properties props = new Properties();
      // InputStream inputStream =
      // App.class.getClassLoader().getResourceAsStream("api.properties");

      // opennlp to tag POS for input
      InputStream inputStreamNLP =
          App.class.getClassLoader().getResourceAsStream(
              "opennlp-en-ud-ewt-pos-1.0-1.9.3.bin"); // loads the model file
      POSModel model = new POSModel(inputStreamNLP); // init the model
      POSTaggerME tagger = new POSTaggerME(model);  // init the tagger
      WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE; // init the tokenizer

      // if (inputStream != null) {
      //   props.load(inputStream);
      // }
      // final String apiKey = props.getProperty("openAIkey");

      // ChatGPT chatGPT = new ChatGPT(apiKey);

      System.out.println(
          "Enter a sentence for the AI to turn into a madlibs: ");
      String input = scanner.nextLine();

      // String prompt = chatGPT.ask(
      //     "Welcome to the Madlibs AI! Please enter a sentence. I will
      //     transform it into a funny madlibs game by replacing random nouns,
      //     verbs, and adjectives with blank spaces surrounded by parentheses.
      //     Let's get started!\nExample input: The quick brown fox jumped over
      //     the lazy dog.\nExample output: The (adjective) (adjective) (noun)
      //     (verb) over the (adjective) (noun).\n\n"
      //         + input);

      String[] tokens = whitespaceTokenizer.tokenize(input); // tokenize the input
      String[] tags = tagger.tag(tokens); // tag the tokens
      POSSample sample = new POSSample(tokens, tags); // init the sample

      System.out.println("\n--------------------------------------\n");

      String[] array = sample.toString().split(" "); // split the sample into an array

      // System.out.print(Arrays.toString(array));
      // System.exit(0);
      for (int i = 0; i < array.length; i++) {
        Random chance = new Random(); // random number generator

        if (array[i].contains("_NOUN") || array[i].contains("_VERB") ||
            array[i].contains("_ADJ")) {
          int random = chance.nextInt(10); // random number between 0 and 9

          if (random <= 2) { // 30% chance of replacing the word
            String partOfSpeech =
                array[i].substring(array[i].lastIndexOf("_") + 1); // get the part of speech
            // get string for POS
            if (partOfSpeech.equals("NOUN")) {
              partOfSpeech = "noun";
            } else if (partOfSpeech.equals("VERB")) {
              partOfSpeech = "verb";
            } else if (partOfSpeech.equals("ADJ")) {
              partOfSpeech = "adjective";
            }

            System.out.println("\n\nEnter a " + partOfSpeech +
                               " to replace: " + array[i].split("_")[0]);
            boolean correct = false;
            while (correct == false) { // loop until the user enters a valid input
              // same deal as previous NLP stuff
              String currentInput = scanner.nextLine().trim();
              String[] token = whitespaceTokenizer.tokenize(currentInput);
              String[] tag = tagger.tag(token);
              POSSample currentSample = new POSSample(token, tag);
              String[] currentArray = currentSample.toString().split(" ");

              if (array[i]
                      .substring(array[i].lastIndexOf("_") + 1)
                      .equals(currentArray[0].substring(
                          currentArray[0].lastIndexOf("_") + 1))) { // check if the POS matches
                correct = true;
              } else { // if not, tell the user what they entered and what they should have entered
                String currentPartOfSpeech = currentArray[0].substring(
                    currentArray[0].lastIndexOf("_") + 1);

                if (currentPartOfSpeech.equals("NOUN")) {
                  currentPartOfSpeech = "noun";
                } else if (currentPartOfSpeech.equals("VERB")) {
                  currentPartOfSpeech = "verb";
                } else if (currentPartOfSpeech.equals("ADJ")) {
                  currentPartOfSpeech = "adjective";
                }

                System.out.println("You have entered a " + currentPartOfSpeech +
                                   ", please try again and enter a " +
                                   partOfSpeech + ".");
              }
            }
          } else {
            array[i] = array[i].substring(0, array[i].indexOf("_"));
            System.out.print(array[i] + " ");
          }

        } else {
          array[i] = array[i].substring(0, array[i].indexOf("_"));
          System.out.print(array[i] + " ");
        }
      }

      System.out.println("Your completed madlib is: ");
      String madlib = "";
      for (int i = 0; i < array.length; i++) {
        madlib += array[i] + " ";
      }

      System.out.println(madlib);

      System.out.println("Would you like to save to a file? (y/n)");
      if (scanner.nextLine().trim().equals("y")) {
        System.out.println("What should the story be named? (filename)");
        String filename = scanner.nextLine().trim();
        if (!filename.isEmpty()) {
          File target = new File(filename);
          target.createNewFile();
          Writer targetFileWriter = new FileWriter(target);
          targetFileWriter.write(madlib);
          targetFileWriter.close();
        } else {
          System.out.println("Invalid file name. Exiting....");
        }
      }
      scanner.close();
      System.exit(0);
    }
  }
}