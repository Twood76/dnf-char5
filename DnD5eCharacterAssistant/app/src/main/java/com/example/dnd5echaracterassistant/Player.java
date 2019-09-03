package com.example.dnd5echaracterassistant;

public class Player {
    protected String name;
    protected int lvl, strength, dexterity, constitution, intelligence, wisdom, charisma;
    protected int proficiency, strBonus,dexBonus, conBonus, intBonus, wisBonus, chaBonus;

    public Player(){

    }

    public Player(String name, int lvl, int str, int dex, int con, int intel, int wis, int cha){
        this.name = name;
        this.lvl = lvl;
        this.strength = str;
        this.dexterity = dex;
        this.constitution = con;
        this.intelligence = intel;
        this.wisdom = wis;
        this.charisma = cha;
        this.proficiency = (int)Math.ceil(lvl*0.25) + 1;
        this.strBonus = (str - 10) / 2;
        this.dexBonus =  (dex - 10) / 2;
        this.conBonus =  (con - 10) / 2;
        this.intBonus =  (intel - 10) / 2;
        this.wisBonus = (wis - 10) / 2 ;
        this.chaBonus =  (cha - 10) / 2;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int lvl) {
        this.proficiency = (int)Math.ceil(lvl*0.25) + 1;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
        setProficiency(lvl);
    }
/*
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
 */

    public int getStrength() {
        return strength;
    }

    public void setStrength(int str) {
        this.strength = str;
        setStrBonus(str);
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dex) {
        this.dexterity = dex;
        setDexBonus(dex);
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int con) {
        this.constitution = con;
        setConBonus(con);
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intel) {
        this.intelligence = intel;
        setIntBonus(intel);
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wis) {
        this.wisdom = wis;
        setWisBonus(wis);
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int cha) {
        this.charisma = cha;
        setChaBonus(cha);
    }

    public int getStrBonus() {
        return strBonus;
    }

    public void setStrBonus(int str) {
        this.strBonus = (str - 10) / 2;
    }

    public int getDexBonus() {
        return dexBonus;
    }

    public void setDexBonus(int dex) {
        this.dexBonus = (dex - 10) / 2;
    }

    public int getConBonus() {
        return conBonus;
    }

    public void setConBonus(int con) {
        this.conBonus = (con - 10) / 2;
    }

    public int getIntBonus() {
        return intBonus;
    }

    public void setIntBonus(int intel) {
        this.intBonus = (intel - 10) / 2;
    }

    public int getWisBonus() {
        return wisBonus;
    }

    public void setWisBonus(int wis) {
        this.wisBonus = (wis - 10) / 2;
    }

    public int getChaBonus() {
        return chaBonus;
    }

    public void setChaBonus(int cha) {
        this.chaBonus = (cha - 10) / 2;
    }

}



