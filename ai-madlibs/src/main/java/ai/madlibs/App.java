package ai.madlibs;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
    String hello = chatGPT.ask("Create a fill in the blanks madlibs with type of speech from this: After flying a long distance, a thirsty crow was wandering the forest in search of water. Finally, he saw a pot half-filled with water. He tried to drink from it but his beak wasn’t long enough to reach the water inside. He then saw pebbles on the ground and one by one, he put them in the pot until the water rose to the brim. The crow then hastily drank from it and quenched his thirst.");
      System.out.println(hello); // will be "\n\nHello! How may I assist you today?"
  }
}
