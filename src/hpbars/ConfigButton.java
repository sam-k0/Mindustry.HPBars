package hpbars;
import arc.graphics.Color;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.dialogs.*;
import mindustry.mod.*;


public class ConfigButton {
    public static void init(Mod mod) {
        Vars.ui.menuGroup.fill(c -> {
            c.top().right();
            c.button("HPBars Config", Icon.pencilSmall, () -> {
                Config config = new Config(mod.getConfig().path());

                // Config window
                BaseDialog dialog = new BaseDialog("HPBars Config");

                dialog.cont.table(t -> {
                    t.defaults().pad(4);

                    t.add("Show Friendly HP Bars").left();
                    t.check("", config.isShowFriendlyHPBars(), b -> {
                        config.setShowFriendlyHPBars(b);
                    }).row();

                    t.add("Show Enemy HP Bars").left();
                    t.check("", config.isShowEnemyHPBars(), b -> {
                        config.setShowEnemyHPBars(b);
                    }).row();

                    t.add("HP Bar Cursor Radius").left();
                    t.field(String.valueOf(config.getShowRadius()), s1 -> {
                        try {
                            config.setShowRadius(Float.parseFloat(s1));
                        } catch (Exception e) {
                            ShowErrorDialog("Invalid radius input: "+s1, dialog);
                        }
                    }).row();

                    t.add("Friendly Color").left();
                    t.field(config.getFriendlyColor().toString(), s1 -> {
                        try {
                            config.setFriendlyColor(Color.valueOf(s1));
                        } catch (Exception e) {
                            ShowErrorDialog("Invalid color input: "+s1, dialog);
                        }
                    }).row();

                    t.add("Enemy Color").left();
                    t.field(config.getEnemyColor().toString(), s1 -> {
                        try {
                            config.setEnemyColor(Color.valueOf(s1));
                        } catch (Exception e) {
                            ShowErrorDialog("Invalid color input: "+ s1, dialog);
                        }
                    }).row();
                });

                dialog.buttons.button("Save", () -> {
                    config.saveFile(mod.getConfig().path());
                    dialog.hide();
                }).size(250f, 50f);
                
                dialog.buttons.button("Cancel", dialog::hide).size(200f, 50f);
                dialog.show();
            }).size(250f,50f);
        });
    }

    private static void ShowErrorDialog(String message, BaseDialog parent) {
        parent.hide(); // Hide the parent dialog
        BaseDialog dialog = new BaseDialog("Error");
        dialog.cont.add(message);
        dialog.buttons.button("OK", dialog::hide);
        dialog.show();
    }
}