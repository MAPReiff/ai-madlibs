package ai.madlibs;

import java.io.IOException;
import java.io.InputStream;
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
    Properties props = new Properties();
    InputStream inputStream =
        App.class.getClassLoader().getResourceAsStream("api.properties");

    if (inputStream != null) {
      props.load(inputStream);
    }
    final String apiKey = props.getProperty("openAIkey");

    System.out.println(apiKey);
  }
}
