package hpbars;
import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.util.*;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.Menus;
import mindustry.entities.Units;
import mindustry.Vars;
import java.util.Random;
import arc.files.*;

public class HPBarHook extends Mod{

    // values will be loaded from the config file
    public Color friendlyColor = Color.green;
    public Color enemyColor = Color.red;
    public boolean showFriendlyHPBars = true;
    public boolean showEnemyHPBars = true;
    public float showRadius = 200f;

    public HPBarHook(){
        Log.info("Loaded HPBars constructor.");

        Events.on(EventType.ClientLoadEvent.class, e -> {
            ConfigButton.init(this); // Initialize the config button
        });

        // Load the config file when the world is loaded
        Events.on(EventType.WorldLoadEvent.class, e -> {
            Config config = new Config(this.getConfig().path());
            friendlyColor = config.getFriendlyColor();
            enemyColor = config.getEnemyColor();
            showFriendlyHPBars = config.isShowFriendlyHPBars();
            showEnemyHPBars = config.isShowEnemyHPBars();
            showRadius = config.getShowRadius();
        });


        Events.run(EventType.Trigger.draw, () -> {
            Draw.z(Layer.overlayUI);

            if(showFriendlyHPBars)
            {
                Units.nearby(Vars.player.team(),  Vars.player.mouseX(),  Vars.player.mouseY(), showRadius, (Unit unit) -> {
                    drawHPBar(unit.x, unit.y, unit.health(), unit.maxHealth(), friendlyColor);
                });
            }


            if(showEnemyHPBars)
            {
                Units.nearbyEnemies(Vars.player.team(), Vars.player.mouseX(), Vars.player.mouseY(), showRadius, (Unit unit) -> {
                drawHPBar(unit.x, unit.y, unit.health(), unit.maxHealth(), enemyColor);
                });
            } 

            Draw.reset();
        });
    }
    

    public void drawHPBar(float x, float y, float health, float maxHealth, Color color)
    {
        // calculate the x and y position of the HP bar
        float barWidth = 25;
        float barX = x - barWidth / 2;
        float barY = y + 15;
        float barHeight = 4;
        
        Draw.z(Layer.max);
        // Draw the background of the HP bar
        Draw.color(Color.darkGray);
        // Draw the border
        if(health < 0)
        {
            Draw.color(Color.darkGray);
        }
        Fill.crect(barX, barY, barWidth, barHeight);

        float healthPercent = health / maxHealth;
        if(health < 0) healthPercent = 0;
        // Draw the current health
        Draw.color(color);
        Fill.crect(barX, barY, barWidth * healthPercent, barHeight);
        
        Draw.reset();
    }

    public String choose(String[] options) {
        if (options == null || options.length == 0) {
            throw new IllegalArgumentException("Options array must not be null or empty");
        }
        Random random = new Random();
        int index = random.nextInt(options.length);  // Randomly choose an index from 0 to options.length-1
        return options[index];
    }
}
