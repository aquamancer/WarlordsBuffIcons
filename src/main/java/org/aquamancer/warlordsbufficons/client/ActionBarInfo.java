package org.aquamancer.warlordsbufficons.client;

import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

public class ActionBarInfo {
    private int currentHealth = -1;
    private int maxHealth = -1;
    private Text team;
    private List<Buff> buffList;
    private int indexOfFirstBuff = -1;

    public void updateActionBarInfo(Text actionBarText) {
        this.buffList = parseBuffList(actionBarText);
        for (int i = 0; i < buffList.size(); i++) {
            chat(buffList.get(i).toString());
        }
    }
    // the color of the text is a nested Style. the first one is the color of the name, second is the color of the colon (gray) third is duration(gold)
    // i will assume all red names are debuffs. the rest are buffs. known buffs: green, yellow; debuffs: red
    // Buff duration is a float for graphical purposes
    private List<Buff> parseBuffList(Text actionBarText) {
        List<Buff> buffList = new ArrayList<Buff>();
        List<Text> siblings = actionBarText.getSiblings();

        for (int i = 0; i < siblings.size(); i++) {
            if (isABuffOrDebuff(siblings.get(i))) {
                if (indexOfFirstBuff == -1)
                    indexOfFirstBuff = i;
                String[] split = siblings.get(i).getString().split(":");

                buffList.add(new Buff(split[0], Float.parseFloat(split[1]), siblings.get(i).getSiblings().get(0).getStyle().getColor()));
            }
        }
        return buffList;
    }
    private boolean isHealthCounter(Text sibling) {
        String query = sibling.getString();

        try {
            String[] split = query.split("/");

            if (split.length != 2)
                return false;

            Integer.parseInt(split[0]);
            Integer.parseInt(split[1]);

            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    private boolean isABuffOrDebuff(Text sibling) {
        String[] split = sibling.getString().split(":");

        if (split.length != 2)
            return false;

        try {
            Integer.parseInt(split[0]); // split[0] should be a string so if it parses ok then return false
            return false;
        } catch (NumberFormatException ex) {
            try {
                Integer.parseInt(split[1]); // split[1] should be an int so if it parses ok then return true
                return true;
            } catch (NumberFormatException ex2) {
                return false;
            }
        }
    }
    public List<Buff> getBuffList() {
        return this.buffList;
    }
    public int getIndexOfFirstBuff() {
        return this.indexOfFirstBuff;
    }
}
