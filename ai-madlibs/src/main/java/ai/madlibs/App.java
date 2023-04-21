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
  // public static void main( String[] args )
  // {
  //     System.out.println( "Hello World!" );
  // }

  public static void main(String[] args) throws IOException {
    // read in API key for OpenAI from properties file
    Properties props = new Properties();
    InputStream inputStream =
        App.class.getClassLoader().getResourceAsStream("api.properties");

    if (inputStream != null) {
      props.load(inputStream);
    }
    final String apiKey = props.getProperty("openAIkey");

    ChatGPT chatGPT = new ChatGPT(apiKey);
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter a sentence for the AI to complete: ");
    String input = scanner.nextLine();

    String prompt = chatGPT.ask("Create a fill in the blanks madlibs with type of speech from this: " + input);
    System.out.println(prompt); // will be "\n\nHello! How may I assist you today?"

    String array[] = prompt.split(" ");
    for(int i = 0; i < array.length; i++){
      if (array[i].contains("(") && array[i].contains(")"))
      {
        System.out.println("Enter a word to replace: " + array[i]);
        array[i] = scanner.nextLine();
      }

      System.out.println(array[i]);
    }

    System.out.println("Your madlib is: ");
    String madlib = "";
    for(int i = 0; i < array.length; i++){
      madlib += array[i] + " ";
    }
    
      System.out.println(madlib);

  }
}
