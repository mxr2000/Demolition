package demolition;

import demolition.util.GameConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * test if the configuration form the config.json is correctly loaded
 */
public class GameConfigurationTest {
    @Test
    void shouldLoadConfiguration() {
        GameConfiguration gameConfiguration = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("build/resources/main/config.json"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();

            String content = stringBuilder.toString();
            gameConfiguration = new GameConfiguration(JSONObject.parse(content));
        } catch (Exception e) {

        }
        Assertions.assertNotNull(gameConfiguration);
        Assertions.assertEquals(4, gameConfiguration.getLifeCount());
        Assertions.assertEquals(2, gameConfiguration.getGridSettings().size());
        Assertions.assertEquals(80, gameConfiguration.getGridSettings().get(0).getTime());
        Assertions.assertEquals("level3.txt", gameConfiguration.getGridSettings().get(0).getFilename());
    }
}
