package ai.madlibs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import com.lilittlecat.chatgpt.offical.ChatGPT;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) throws IOException {
    // read in API key for OpenAI from properties file
    Properties props = new Properties();
    InputStream inputStream = App.class.getClassLoader().getResourceAsStream("api.properties");

    if (inputStream != null) {
      props.load(inputStream);
    }
    final String apiKey = props.getProperty("openAIkey");

    ChatGPT chatGPT = new ChatGPT(apiKey);
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter a sentence for the AI to complete: ");
    String input = scanner.nextLine();

    String prompt = chatGPT.ask(
        "Create a fill in the blanks madlibs with type of speech from this, with each type of speech surrounded by parenthesis: "
            + input);
    System.out.println("\n--------------------------------------\n");

    String array[] = prompt.split(" ");
    for (int i = 0; i < array.length; i++) {
      if (array[i].contains("(") && array[i].contains(")")) {
        System.out.println("\n\nEnter a word to replace: " + array[i]);
        array[i] = scanner.nextLine();
      }
      System.out.print(array[i] + " ");
    }

    System.out.println("Your madlib is: ");
    String madlib = "";
    for (int i = 0; i < array.length; i++) {
      madlib += array[i] + " ";
    }

    System.out.println(madlib);

  }
}
