package ai.madlibs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Properties;
import java.util.Scanner;
import com.lilittlecat.chatgpt.offical.ChatGPT;

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
          System.out.println("Error has occured. File likely does not exist. Exiting...");
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
      Properties props = new Properties();
      InputStream inputStream = App.class.getClassLoader().getResourceAsStream("api.properties");

      if (inputStream != null) {
        props.load(inputStream);
      }
      final String apiKey = props.getProperty("openAIkey");

      ChatGPT chatGPT = new ChatGPT(apiKey);

      System.out.println("Enter a sentence for the AI to complete: ");
      String input = scanner.nextLine();

      String prompt = chatGPT.ask(
          "Welcome to the Madlibs AI! Please enter a sentence. I will transform it into a funny madlibs game by replacing random nouns, verbs, and adjectives with blank spaces surrounded by parentheses. Let's get started!\nExample input: The quick brown fox jumped over the lazy dog.\nExample output: The (adjective) (adjective) (noun) (verb) over the (adjective) (noun).\n\n"
              + input);
      System.out.println("\n--------------------------------------\n");

      String array[] = prompt.split(" ");
      for (int i = 0; i < array.length; i++) {
        if (array[i].contains("(") && array[i].contains(")")) {
          System.out.println("\n\nEnter a word to replace: " + array[i]);
          array[i] = scanner.nextLine();
          System.out.println();
        } else {
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
