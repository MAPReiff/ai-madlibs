package ai.madlibs;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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

    // create a new instance of the OpenAI class
    // OpenAiService service = new OpenAiService(apiKey);
    // CompletionRequest completionRequest =
    //     CompletionRequest.builder()
    //         .prompt("Somebody once told me the world is gonna roll me")
    //         .model("ada")
    //         .echo(true)
    //         .build();
    // service.createCompletion(completionRequest)
    //     .getChoices()
    //     .forEach(System.out::println);

    OpenAiService service = new OpenAiService(apiKey);
    System.out.println("\nCreating completion...");
    CompletionRequest completionRequest =
        CompletionRequest.builder()
            .model("ada")
            .prompt("Somebody once told me the world is gonna roll me")
            .echo(true)
            .user("testing")
            .n(3)
            .build();
    System.out.println(
        service.createCompletion(completionRequest).getChoices());
    // .forEach(System.out::println);
  }
}
