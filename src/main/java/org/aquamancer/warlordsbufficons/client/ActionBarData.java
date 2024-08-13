package org.aquamancer.warlordsbufficons.client;

import net.minecraft.text.Style;

import java.util.ArrayList;
import java.util.List;

public class ActionBarData {
    private int currentHealth;
    private int maxHealth;
    private boolean showHpPrefix = false;
    private boolean showCurrentHealth = false;
    private boolean showMaxHealth = false;
    private String team;
    private boolean showTeam;
    private List<Buff> buffList;
    private Style hpPrefixStyle, currentHealthStyle, maxHealthStyle;
    private List<Style> currentAndMaxHealthStyle;
    private List<List<Style>> buffStyles;
    private Style teamStyle;

    public ActionBarData() {
        this.currentHealth = -1;
        this.maxHealth = -1;
        this.showHpPrefix = false;
        this.showCurrentHealth = false;
        this.showMaxHealth = false;
        this.team = null;
        this.showTeam = false;
        this.buffList = null;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public boolean isShowHpPrefix() {
        return showHpPrefix;
    }

    public void setShowHpPrefix(boolean showHpPrefix) {
        this.showHpPrefix = showHpPrefix;
    }

    public boolean isShowCurrentHealth() {
        return showCurrentHealth;
    }

    public void setShowCurrentHealth(boolean showCurrentHealth) {
        this.showCurrentHealth = showCurrentHealth;
    }

    public boolean isShowMaxHealth() {
        return showMaxHealth;
    }

    public void setShowMaxHealth(boolean showMaxHealth) {
        this.showMaxHealth = showMaxHealth;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isShowTeam() {
        return showTeam;
    }

    public void setShowTeam(boolean showTeam) {
        this.showTeam = showTeam;
    }

    public List<Buff> getBuffList() {
        return buffList;
    }

    public void setBuffList(List<Buff> buffList) {
        this.buffList = buffList;
    }

    public Style getHpPrefixStyle() {
        return hpPrefixStyle;
    }

    public void setHpPrefixStyle(Style hpPrefixStyle) {
        this.hpPrefixStyle = hpPrefixStyle;
    }

    public Style getCurrentHealthStyle() {
        return currentHealthStyle;
    }

    public void setCurrentHealthStyle(Style currentHealthStyle) {
        this.currentHealthStyle = currentHealthStyle;
    }

    public Style getMaxHealthStyle() {
        return maxHealthStyle;
    }

    public void setMaxHealthStyle(Style maxHealthStyle) {
        this.maxHealthStyle = maxHealthStyle;
    }

    public List<Style> getCurrentAndMaxHealthStyle() {
        return currentAndMaxHealthStyle;
    }

    public void setCurrentAndMaxHealthStyle(List<Style> currentAndMaxHealthStyle) {
        this.currentAndMaxHealthStyle = currentAndMaxHealthStyle;
    }

    public List<List<Style>> getBuffStyles() {
        return buffStyles;
    }

    public void setBuffStyles(List<List<Style>> buffStyles) {
        this.buffStyles = buffStyles;
    }

    public Style getTeamStyle() {
        return teamStyle;
    }

    public void setTeamStyle(Style teamStyle) {
        this.teamStyle = teamStyle;
    }
}
