package org.aquamancer.warlordsbufficons.client;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

public class ActionBarParser {
    public boolean isCompassDisplayed = false;

    // the color of the text is a nested Style. the first one is the color of the name, second is the color of the colon (gray) third is duration(gold)
    // i will assume all red names are debuffs. the rest are buffs. known buffs: green, yellow; debuffs: red
    // Buff duration is a float for graphical purposes

    /*
    in game options for HP action bar element:
    - toggle "HP: "
    - toggle max health
    - toggle health
    TESTING:
        all on = sibling 0: "HP: ", sibling 1: "currenthealth/maxhealth"
        "HP: " off = sibling 0: "   ", sibling 1: "currenthealth/maxhealth"
        "HP: " & currentHP off = sibling0: maxhealth
        "HP: " & maxhealth off = sibling0: currenthealth
        maxhealth off = sibling0: "HP: ", sibling1 = currenthealth
        currenthealth off = sibling0: "HP: ", sibling1 = maxhealth
        ***maxhealth & currenthealth off = sibling0: "", sibling1 = BLU TEAM*** "overridden" edge case
        all off =sibling0: " ", sibling1: BLU TEAM

        Styles:
        "HP: " = color = gold, isBold = true
        currenthp = color = red/yellow,dark_green, green, isBold = true
        BLUE TEAM = color = blue, isBold = true


     */
    @Nullable
    public ActionBarData parseActionBarInfo(Text actionBarText) {
        ActionBarData data = new ActionBarData();
        List<Buff> buffList = new ArrayList<>();
        List<Style> styleList = new ArrayList<>();

        List<Text> siblings = actionBarText.getSiblings();
        boolean foundHp = false;
        boolean foundTeam = false;
        boolean reachedBuffs = false;
        this.isCompassDisplayed = false;

        for (int i = 0; i < siblings.size(); i++) {
            // check if compass is displayed
            if (siblings.get(i).getString().contains("Flag")) {
                this.isCompassDisplayed = true;
                return null;
            }
            if (!foundHp) { // if (!foundHp && !reachedBuffs) {
                if (siblings.get(i).getString().equals("HP: ")) {
                    data.setShowHpPrefix(true);
                    data.setHpPrefixStyle(siblings.get(i).getStyle());
                    chat("hpprefixstyle:" + siblings.get(i).getStyle().toString());
                    continue;
                }
                /*
                currentHealth/maxHealth
                parent sibling color(siblings.get(i).getStyle... = currentHealth
                nested tier 1 index 0 = null
                nested tier 1 index 1 siblings.get(i).getSiblings.get(1) = forward slash color
                nested tier 1 index 2 siblings.get(i).getSiblings.get(2) = maxHealth color
                 */
                if (this.isCurrentAndMaxHpFormat(siblings.get(i))) { // check if the current sibling is currenthealth/maxhealth format
                    chat("current and max hp format");

                    data.setShowCurrentHealth(true);
                    data.setShowMaxHealth(true);
                    int[] healthValues = this.parseCurrentAndMaxHpFormat(siblings.get(i));
                    data.setCurrentHealth(healthValues[0]);
                    data.setMaxHealth(healthValues[1]);

                    data.setCurrentHealthStyle(siblings.get(i).getStyle());
                    List<Style> nestedStyles = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        nestedStyles.add(siblings.get(i).getSiblings().get(j).getStyle());
                        chat("nestedstyle:" + siblings.get(i).getSiblings().get(j).getStyle().toString());
                    }
                    data.setCurrentAndMaxHealthStyle(nestedStyles);

                    foundHp = true;
                    continue;
                } else if (this.isCurrentHealthOnlyFormat(siblings.get(i))) { // when currentHealth is enabled and maxHealth is not, bold is parent, color is child?
                    data.setShowCurrentHealth(true);
                    data.setCurrentHealth(Integer.parseInt(siblings.get(i).getString()));
                    data.setCurrentHealthStyle(siblings.get(i).getStyle());

                    foundHp = true;
                    continue;
                } else if (this.isMaxHealthOnlyFormat(siblings.get(i))) { // when maxHealth is enabled and currentHealth is not,
                    chat("maxhealth only format");
                    data.setShowMaxHealth(true);
                    data.setMaxHealth(Integer.parseInt(siblings.get(i).getString()));
                    data.setMaxHealthStyle(siblings.get(i).getSiblings().get(0).getStyle().withBold(true));

                    foundHp = true;
                    continue;
                }
            }
            if (!foundTeam) {
                if (this.isTeamFormat(siblings.get(i))) {
                    data.setShowTeam(true);
                    data.setTeam(siblings.get(i).getString());
                    data.setTeamStyle(siblings.get(i).getStyle());

                    foundTeam = true;
                    continue;
                }
            }
            if (isABuffOrDebuff(siblings.get(i))) {
                reachedBuffs = true;
                String[] split = siblings.get(i).getString().split(":");

                buffList.add(new Buff(split[0], Float.parseFloat(split[1]), siblings.get(i).getSiblings().get(0).getStyle().getColor()));
            }
        }
        data.setBuffList(buffList);
        return data;
    }
    /*
    in game options:
        remove "HP: "
        remove max health
        remove health
     */
    private boolean isCurrentAndMaxHpFormat(Text sibling) {
        String query = sibling.getString();
        String[] split = query.split("/");

        return split.length == 2 && isNumericInt(split[0]) && isNumericInt(split[1]);
    }
    private int[] parseCurrentAndMaxHpFormat(Text sibling) {
        String[] split = sibling.getString().split("/");
        // this shouldn't throw because we run isNumericInt before
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }

    private boolean isCurrentHealthOnlyFormat(Text sibling) {
        Style style = sibling.getStyle();
        return isNumericInt(sibling.getString()) && style != null && style.getColor() != null && style.isBold() && (style.getColor().getName().equals("green") || style.getColor().getName().equals("dark_green") || style.getColor().getName().equals("yellow") || style.getColor().getName().equals("red"));
    }
    // max health: isBold() = false
    // color: IT IS POSSIBLE FOR MAX HEALTH TO BE YELLOW. is this reachable via pve only?
    // only matters if theyre using currenthealthonly -> hp won't be displayed
    // healthBuilder.append(Component.text(maxHealthRounded, maxHealthRounded > maxBaseHealthRounded ? NamedTextColor.YELLOW : NamedTextColor.GOLD));
    private boolean isMaxHealthOnlyFormat(Text sibling) {
        Style style = sibling.getSiblings().get(0).getStyle();

        return isNumericInt(sibling.getString()) && style != null && style.getColor() != null && !style.isBold() && style.getColor().getName().equals("gold");
    }

    private boolean isABuffOrDebuff(Text sibling) {
        String[] split = sibling.getString().split(":");

        return split.length == 2 && !isNumericInt(split[0]) && isNumericInt(split[1]);
    }

    /**
     * Checks if the Text contains "TEAM" and is bolded.<br>Does not check color, to allow for teams other than red/blue
     * @param sibling
     * @return
     */
    private boolean isTeamFormat(Text sibling) {
        Style style = sibling.getStyle();
        String text = sibling.getString();

        return style.isBold() && text.contains("TEAM");
    }
    public static boolean isNumericFloat(String str) {
        return str != null && str.matches("-?\\d*(\\.\\d+)?");
    }
    public static boolean isNumericInt(String str) {
        return str != null && str.matches("-?\\d+");
    }
}
