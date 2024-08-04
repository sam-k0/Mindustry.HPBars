package hpbars;

import arc.files.Fi;
import arc.util.serialization.JsonValue;
import arc.util.serialization.JsonReader;
import mindustry.Vars;
import arc.graphics.Color;

public class Config {
    private boolean showFriendlyHPBars;
    private boolean showEnemyHPBars;
    private Color friendlyColor;
    private Color enemyColor;
    private float showRadius;

    //private final String filePath;


    public Config(String path) {
        //this.filePath = path;
        if (!loadFromFile(path)) {
            // If loading fails, initialize with default values
            init();
        }
    }

    public void init() {
        showFriendlyHPBars = true;
        showEnemyHPBars = true;
        friendlyColor = Color.green;
        enemyColor = Color.red;
        showRadius = 200f;
    }

    private boolean loadFromFile(String filePath) {
        Fi file = Fi.get(filePath);
        if (file.exists()) {
            try {
                String jsonString = file.readString();
                JsonReader reader = new JsonReader();
                JsonValue jsonData = reader.parse(jsonString);
                // Copy the loaded values
                this.showFriendlyHPBars = jsonData.getBoolean("showFriendlyHPBars", true);
                this.showEnemyHPBars = jsonData.getBoolean("showEnemyHPBars", true);
                this.friendlyColor = jsonData.has("friendlyColor") ? Color.valueOf(jsonData.getString("friendlyColor")) : Color.green;
                this.enemyColor =  jsonData.has("enemyColor") ? Color.valueOf(jsonData.getString("enemyColor")) : Color.red;
                this.showRadius = jsonData.getFloat("showRadius", 200f);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Vars.ui.showException(e);
            }
        }
        return false;
    }

    public void saveFile(String filePath) {
        Fi file = Fi.get(filePath);
        try {
            JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
            jsonObject.addChild("showFriendlyHPBars", new JsonValue(showFriendlyHPBars));
            jsonObject.addChild("showEnemyHPBars", new JsonValue(showEnemyHPBars));
            jsonObject.addChild("showRadius", new JsonValue(showRadius));
            
            jsonObject.addChild("friendlyColor", new JsonValue(friendlyColor.toString()) );
            jsonObject.addChild("enemyColor",new JsonValue(enemyColor.toString()) );

            file.writeString(jsonObject.toString(), false);
        } catch (Exception e) {
            e.printStackTrace();
            Vars.ui.showException(e);
        }
    }

    // Getters and Setters for the fields if needed
    public boolean isShowFriendlyHPBars() {
        return showFriendlyHPBars;
    }

    public void setShowFriendlyHPBars(boolean showFriendlyHPBars) {
        this.showFriendlyHPBars = showFriendlyHPBars;
    }

    public boolean isShowEnemyHPBars() {
        return showEnemyHPBars;
    }

    public void setShowEnemyHPBars(boolean showEnemyHPBars) {
        this.showEnemyHPBars = showEnemyHPBars;
    }

    public Color getFriendlyColor() {
        return friendlyColor;
    }

    public void setFriendlyColor(Color friendlyColor) {
        this.friendlyColor = friendlyColor;
    }

    public Color getEnemyColor() {
        return enemyColor;
    }

    public void setEnemyColor(Color enemyColor) {
        this.enemyColor = enemyColor;
    }

    public float getShowRadius() {
        return showRadius;
    }

    public void setShowRadius(float showRadius) {
        this.showRadius = showRadius;
    }
}
