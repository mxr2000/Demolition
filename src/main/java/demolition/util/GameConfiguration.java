package demolition.util;

import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The class which memorizes the configuration which is loaded from the config.json file
 */
public class GameConfiguration {
    /**
     * Init from the json object which is loaded and serialized.
     * @param jsonObject
     */
    public GameConfiguration(JSONObject jsonObject) {
        System.out.println(jsonObject);
        JSONArray levels = jsonObject.getJSONArray("levels");
        for (int i = 0; i < levels.size(); i++) {
            JSONObject setting = levels.getJSONObject(i);
            gridSettings.add(new GridSetting(setting.getString("path"), setting.getInt("time")));
        }
        lifeCount = jsonObject.getInt("lives");
    }
    private final List<GridSetting> gridSettings = new ArrayList<>();
    private int lifeCount = 0;

    public int getLifeCount() {
        return lifeCount;
    }

    public List<GridSetting> getGridSettings() {
        return gridSettings;
    }

    /**
     * The class which set the settings in one level
     */
    public static class GridSetting {
        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public GridSetting(String filename, int time) {
            this.filename = filename;
            this.time = time;
        }

        private String filename;
        private int time;
    }
}
