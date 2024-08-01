package example;

import org.w3c.dom.events.Event;

import arc.*;
import arc.func.Boolf;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.scene.ui.Dialog;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.entities.Units;
import mindustry.Vars;
import mindustry.game.Team;


public class ExampleJavaMod extends Mod{

    private Unit focusedUnit = null;

    public ExampleJavaMod(){
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("example-java-mod-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });


        Events.run(EventType.Trigger.update, () -> {

            focusedUnit = Units.closest(Vars.player.team(), Vars.player.x, Vars.player.y, 100f, u -> u.team() == Team.sharded);
            if (focusedUnit == null) {
                drawHPBar(Vars.player.x, Vars.player.y, 10, 100);
                return;
            }


            float x = focusedUnit.x;
            float y = focusedUnit.y;

            // Ensure HP bar is drawn with updated player position
            drawHPBar(x, y, 59, 100);
        });


        Events.run(EventType.Trigger.draw, () -> {
            Draw.z(Layer.overlayUI);
            if(focusedUnit == null) return;
            drawHPBar(focusedUnit.x, focusedUnit.y, 10, 100);

            //Draw.rect(Core.atlas.find("example-java-mod-frog"), Vars.player.x, Vars.player.y);
            Draw.reset();
        });
    }
    
    public void drawHPBar(float x, float y, float health, float maxHealth)
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

        float healthPercent = 0.5f; // health / maxHealth;
        // Draw the current health
        Draw.color(Color.scarlet);
        Fill.crect(barX, barY, barWidth * healthPercent, barHeight);

        // Draw the border
        Draw.color(Color.black);
        Lines.stroke(2f);

        Draw.reset();
    }

}
