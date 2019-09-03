package com.example.dnd5echaracterassistant;


public class Proficiency {
    private boolean strSave, dexSave, conSave, intelSave, wisSave, chaSave;
    private boolean acrobatics, animalHandling, arcana, athletics, deception, history, insight, intimidation, investigation;
    private boolean medicine, nature, perception, performance, persuasion, religion, sleightOfHand, stealth, survival;
    public Proficiency(){

    }
    public Proficiency(
            boolean strSave, boolean dexSave, boolean conSave, boolean intelSave, boolean wisSave, boolean chaSave,
            boolean acrobatics, boolean animalHandling, boolean arcana, boolean athletics, boolean deception,
            boolean history, boolean insight, boolean intimidation, boolean investigation, boolean medicine,
            boolean nature, boolean perception, boolean performance, boolean persuasion, boolean religion,
            boolean sleightOfHand, boolean stealth, boolean survival)
    {
        this. strSave = strSave;
        this. dexSave = dexSave;
        this. conSave = conSave;
        this. intelSave = intelSave;
        this. wisSave = wisSave;
        this. chaSave = chaSave;
        this. acrobatics = acrobatics;
        this. animalHandling = animalHandling;
        this. arcana = arcana;
        this. athletics = athletics;
        this. deception = deception;
        this. history = history;
        this. insight = insight;
        this. intimidation = intimidation;
        this. investigation = investigation;
        this. medicine = medicine;
        this. nature = nature;
        this. perception = perception;
        this. performance = performance;
        this. persuasion = persuasion;
        this. religion = religion;
        this. sleightOfHand = sleightOfHand;
        this. stealth = stealth;
        this. survival = survival;
    }

    public boolean isStrSave() {
        return strSave;
    }

    public void setStrSave(boolean strSave) {
        this.strSave = strSave;
    }

    public boolean isDexSave() {
        return dexSave;
    }

    public void setDexSave(boolean dexSave) {
        this.dexSave = dexSave;
    }

    public boolean isConSave() {
        return conSave;
    }

    public void setConSave(boolean conSave) {
        this.conSave = conSave;
    }

    public boolean isIntelSave() {
        return intelSave;
    }

    public void setIntelSave(boolean intelSave) {
        this.intelSave = intelSave;
    }

    public boolean isWisSave() {
        return wisSave;
    }

    public void setWisSave(boolean wisSave) {
        this.wisSave = wisSave;
    }

    public boolean isChaSave() {
        return chaSave;
    }

    public void setChaSave(boolean chaSave) {
        this.chaSave = chaSave;
    }

    public boolean isAcrobatics() {
        return acrobatics;
    }

    public void setAcrobatics(boolean acrobatics) {
        this.acrobatics = acrobatics;
    }

    public boolean isAnimalHandling() {
        return animalHandling;
    }

    public void setAnimalHandling(boolean animalHandling) {
        this.animalHandling = animalHandling;
    }

    public boolean isArcana() {
        return arcana;
    }

    public void setArcana(boolean arcana) {
        this.arcana = arcana;
    }

    public boolean isAthletics() {
        return athletics;
    }

    public void setAthletics(boolean athletics) {
        this.athletics = athletics;
    }

    public boolean isDeception() {
        return deception;
    }

    public void setDeception(boolean deception) {
        this.deception = deception;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public boolean isInsight() {
        return insight;
    }

    public void setInsight(boolean insight) {
        this.insight = insight;
    }

    public boolean isIntimidation() {
        return intimidation;
    }

    public void setIntimidation(boolean intimidation) {
        this.intimidation = intimidation;
    }

    public boolean isInvestigation() {
        return investigation;
    }

    public void setInvestigation(boolean investigation) {
        this.investigation = investigation;
    }

    public boolean isMedicine() {
        return medicine;
    }

    public void setMedicine(boolean medicine) {
        this.medicine = medicine;
    }

    public boolean isNature() {
        return nature;
    }

    public void setNature(boolean nature) {
        this.nature = nature;
    }

    public boolean isPerception() {
        return perception;
    }

    public void setPerception(boolean perception) {
        this.perception = perception;
    }

    public boolean isPerformance() {
        return performance;
    }

    public void setPerformance(boolean performance) {
        this.performance = performance;
    }

    public boolean isPersuasion() {
        return persuasion;
    }

    public void setPersuasion(boolean persuasion) {
        this.persuasion = persuasion;
    }

    public boolean isReligion() {
        return religion;
    }

    public void setReligion(boolean religion) {
        this.religion = religion;
    }

    public boolean isSleightOfHand() {
        return sleightOfHand;
    }

    public void setSleightOfHand(boolean sleightOfHand) {
        this.sleightOfHand = sleightOfHand;
    }

    public boolean isStealth() {
        return stealth;
    }

    public void setStealth(boolean stealth) {
        this.stealth = stealth;
    }

    public boolean isSurvival() {
        return survival;
    }

    public void setSurvival(boolean survival) {
        this.survival = survival;
    }
}
