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
  String menu(Scanner scanner) {
    System.out.println(
        "Welcome to Madlibs AI! Would you like to read in a completed madlib or generate a new one? (Press 1 for new, or 2 for replaying an existing madlib, 3 for reading an existing madlib)");
    return scanner.nextLine().trim();
  }

  String readMadlib(Scanner scanner) {
    System.out.println("What's the file name of the existing madlib?");
    String readName = scanner.nextLine().trim();
    if (!readName.isEmpty()) {
      try (Scanner readFile = new Scanner(new File(readName))) {
        String data = "";
        while (readFile.hasNextLine()) {
          data = readFile.nextLine().trim();
        }
        return data;
      } catch (IOException e) {
        System.out.println(
            "Error has occurred. File likely does not exist. Exiting...");
        scanner.close();
        System.exit(1);
      }
    } else {
      System.out.println("Invalid file name. Exiting...");
      scanner.close();
      System.exit(1);
    }
    return "";
  }

  String newMadLib(Scanner scanner) throws IOException {
    System.out.println("Enter a sentence for the AI to turn into a madlibs: ");
    String input = scanner.nextLine().trim();
    return generateMadLib(scanner, input);
  }

  int saveMadLib(Scanner scanner, String madlib) throws IOException {
    System.out.println("What should the story be named? (filename)");
    String filename = scanner.nextLine().trim();
    if (!filename.isEmpty()) {
      File target = new File(filename);
      if (target.createNewFile()) {
        Writer targetFileWriter = new FileWriter(target);
        targetFileWriter.write(madlib);
        targetFileWriter.close();
      } else {
        System.err.println("File already exists. Exiting....");
      }

    } else {
      System.out.println("Invalid file name. Exiting....");
    }
    return 0;
  }

  String generateMadLib(Scanner scanner, String input) throws IOException {
    // opennlp to tag POS for input
    InputStream inputStreamNLP = App.class.getClassLoader().getResourceAsStream(
        "opennlp-en-ud-ewt-pos-1.0-1.9.3.bin"); // loads the model file
    POSModel model = new POSModel(inputStreamNLP); // init the model
    POSTaggerME tagger = new POSTaggerME(model); // init the tagger
    WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE; // init the tokenizer

    // if (inputStream != null) {
    // props.load(inputStream);
    // }
    // final String apiKey = props.getProperty("openAIkey");

    // ChatGPT chatGPT = new ChatGPT(apiKey);

    // String prompt = chatGPT.ask(
    // "Welcome to the Madlibs AI! Please enter a sentence. I will
    // transform it into a funny madlibs game by replacing random nouns,
    // verbs, and adjectives with blank spaces surrounded by parentheses.
    // Let's get started!\nExample input: The quick brown fox jumped over
    // the lazy dog.\nExample output: The (adjective) (adjective) (noun)
    // (verb) over the (adjective) (noun).\n\n"
    // + input);

    String[] tokens = whitespaceTokenizer.tokenize(input); // tokenize the input
    String[] tags = tagger.tag(tokens); // tag the tokens
    POSSample sample = new POSSample(tokens, tags); // init the sample

    System.out.println("\n--------------------------------------\n");

    String[] array = sample.toString().split(" "); // split the sample into an array

    // System.out.print(Arrays.toString(array));
    // System.exit(0);
    Random chance = new Random(); // random number generator
    int x = -1;

    for (String value : array) {
      x++;
      if (value.contains("_NOUN") || value.contains("_VERB") ||
          value.contains("_ADJ")) {
        int random = chance.nextInt(10); // random number between 0 and 9

        if (random <= 2) { // 30% chance of replacing the word

          String partOfSpeech = value.substring(value.lastIndexOf("_") +
              1); // get the part of speech
          // get string for POS
          switch (partOfSpeech) {
            case "NOUN":
              partOfSpeech = "noun";
              break;
            case "VERB":
              partOfSpeech = "verb";
              break;
            case "ADJ":
              partOfSpeech = "adjective";
              break;
            default:
              partOfSpeech = "noun"; // default to noun in case of somehow not
                                     // being one of the above
              break;
          }

          System.out.println("\n\nEnter a " + partOfSpeech + ": ");
          // " to replace: " + value.split("_")[0]);
          boolean correct = false;
          String POS = value.substring(value.lastIndexOf("_") + 1);
          while (!correct) { // loop until the user enters a valid input
            // same deal as previous NLP stuff
            String currentInput = scanner.nextLine().trim();

            POSSample currentSample = new POSSample(
                whitespaceTokenizer.tokenize(currentInput),
                tagger.tag(whitespaceTokenizer.tokenize(currentInput)));

            String[] currentArray = currentSample.toString().split(" ");

            if (POS.equals(
                currentArray[0].substring(currentArray[0].lastIndexOf("_") +
                    1))) { // check if the POS matches
              correct = true;
              array[x] = currentInput;
            } else { // if not, tell the user what they entered and what they
                     // should have entered
              String currentPartOfSpeech = currentArray[0].substring(
                  currentArray[0].lastIndexOf("_") + 1);

              switch (currentPartOfSpeech) {
                case "NOUN":
                  currentPartOfSpeech = "noun";
                  break;
                case "VERB":
                  currentPartOfSpeech = "verb";
                  break;
                case "ADJ":
                  currentPartOfSpeech = "adjective";
                  break;
                default:
                  currentPartOfSpeech = "noun"; // default to noun in case of somehow
                                                // not being one of the above
                  break;
              }

              System.out.println("You have entered a " + currentPartOfSpeech +
                  ", please try again and enter a " +
                  partOfSpeech + ".");
            }
          }
        } else {
          array[x] = value.substring(0, value.indexOf("_"));
          System.out.print(array[x] + " ");
        }

      } else {
        array[x] = value.substring(0, value.indexOf("_"));
        System.out.print(array[x] + " ");
      }
    }

    StringBuilder madlibBuilder = new StringBuilder();
    for (int i = 0; i < array.length; i++) {
      madlibBuilder.append(array[i]).append(" ");
    }

    String madlib = madlibBuilder.toString();
    return madlib;
  }

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    String choice = new App().menu(scanner);
    if (choice.equals("1")) {
      String madlib = new App().newMadLib(scanner);
      System.out.println("\n");
      System.out.println("Your completed madlib is: ");
      System.out.println(madlib);
      System.out.println("Would you like to save to a file? (y/n)");
      if (scanner.nextLine().trim().equals("y")) {
        new App().saveMadLib(scanner, madlib);
      }
    } else if (choice.equals("2")) {
      String rawSentence = new App().readMadlib(scanner);
      String madlib = new App().generateMadLib(scanner, rawSentence);
      System.out.println("\n");
      System.out.println("Your completed madlib is: ");
      System.out.println(madlib);
      System.out.println("Would you like to save to a file? (y/n)");
      if (scanner.nextLine().trim().equals("y")) {
        new App().saveMadLib(scanner, madlib);
      }
    } else if (choice.equals("3")) {
      String data = new App().readMadlib(scanner);
      System.out.println(data);
    } else {
      System.out.println("Invalid input. Exiting....");
    }
    scanner.close();
    System.exit(0);
  }
}