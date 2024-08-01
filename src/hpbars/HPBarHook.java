package hpbars;
import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.util.*;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.Menus;
import mindustry.entities.Units;
import mindustry.Vars;
import mindustry.game.Team;


public class HPBarHook extends Mod{

    private Unit focusedEnemyUnit = null;
    private Unit focusedTeamUnit = null;

    public HPBarHook(){
        Log.info("Loaded HPBars constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                //show dialog upon startup
                Vars.ui.announce("HP Bars mod: Nearest enemy and ally at mouse position will be shown.", 4f);
            });
  
        });


        Events.run(EventType.Trigger.update, () -> {

            float range = 200f;
            focusedTeamUnit = Units.closest(Vars.player.team(), Vars.player.mouseX(), Vars.player.mouseY(), range, u -> u.team() == Team.sharded);
            focusedEnemyUnit = Units.closestEnemy(Vars.player.team(), Vars.player.mouseX(), Vars.player.mouseY(), range, u -> u.team() != Team.sharded);
        });


        Events.run(EventType.Trigger.draw, () -> {
            Draw.z(Layer.overlayUI);
            if(focusedTeamUnit != null) 
            {
                drawHPBar(focusedTeamUnit.x, focusedTeamUnit.y, focusedTeamUnit.health(), focusedTeamUnit.maxHealth(), Color.green);
            }
            
            if(focusedEnemyUnit != null) 
            {
                drawHPBar(focusedEnemyUnit.x, focusedEnemyUnit.y, focusedEnemyUnit.health(), focusedEnemyUnit.maxHealth(), Color.red);
            }

            Draw.reset();
        });
    }
    
    public void drawHPBar(float x, float y, float health, float maxHealth, Color color)
    {
        // calculate the x and y position of the HP bar
        float barX = x - 20;
        float barY = y + 20;
        float barWidth = 40;
        float barHeight = 4;
        
        Draw.z(Layer.max);
        // Draw the background of the HP bar
        Draw.color(Color.darkGray);
        Fill.crect(barX, barY, barWidth, barHeight);

        float healthPercent = health / maxHealth;
        // Draw the current health
        Draw.color(color);
        Fill.crect(barX, barY, barWidth * healthPercent, barHeight);

        // Draw the border
        Draw.color(Color.black);
        Lines.stroke(2f);

        Draw.reset();
    }

}
