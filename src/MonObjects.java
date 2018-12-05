
public class MonObjects {

    protected String name;
    protected int amourClass;
    protected int hitP;
    protected String speed;
    protected int str;
    protected int strModifier;
    protected int dex;
    protected int dexModifier;
    protected int con;
    protected int conModifier;
    protected int intel;
    protected int intelModifier;
    protected int wis;
    protected int wisModifier;
    protected int cha;
    protected int chaModifier;
    protected int savingThrows [] = new int[6];
    protected int profiBonus;
    protected int skillUp;
    protected String damageRes[] = new String[100];
    protected String conditionImmun [] = new String[25];
    protected String senses[] = new String[20];
    protected String languages[] = new String[20];
    protected int challenge;

    /*******************************************************************************************************************
     * Basic constructors for monster. Must have at least Name, Armour Class, and speed. Each iteration will be
     * incremental. New window that creates monster must be added in future updates.
     * ****************************************************************************************************************/
    public MonObjects(String inName, int inArmorC, int inHp) {
        // Creates a basic monster Used
        this.name = inName;
        this.amourClass = inArmorC;
        this.hitP = inHp;
    }
    // Goes up to Strength Mod
    public MonObjects(String name, int amourClass, int hitP, String speed, int str, int strModifier) {
        this.name = name;
        this.amourClass = amourClass;
        this.hitP = hitP;
        this.speed = speed;
        this.str = str;
        this.strModifier = strModifier;
    }
    // Goes up to Dexterity Modifier
    public MonObjects(String name, int amourClass, int hitP, String speed, int str, int strModifier, int dex,
                      int dexModifier) {
        this.name = name;
        this.amourClass = amourClass;
        this.hitP = hitP;
        this.speed = speed;
        this.str = str;
        this.strModifier = strModifier;
        this.dex = dex;
        this.dexModifier = dexModifier;
    }
    // Goes up to Intelligence mod
    public MonObjects(String name, int amourClass, int hitP, String speed, int str, int strModifier, int dex,
                      int dexModifier, int con, int conModifier, int intel, int intelModifier) {
        this.name = name;
        this.amourClass = amourClass;
        this.hitP = hitP;
        this.speed = speed;
        this.str = str;
        this.strModifier = strModifier;
        this.dex = dex;
        this.dexModifier = dexModifier;
        this.con = con;
        this.conModifier = conModifier;
        this.intel = intel;
        this.intelModifier = intelModifier;
    }
    // Goes up to the Wisdom Modifier
    public MonObjects(String name, int amourClass, int hitP, String speed, int str, int strModifier, int dex,
                      int dexModifier, int con, int conModifier, int intel, int intelModifier, int wis,
                      int wisModifier) {
        this.name = name;
        this.amourClass = amourClass;
        this.hitP = hitP;
        this.speed = speed;
        this.str = str;
        this.strModifier = strModifier;
        this.dex = dex;
        this.dexModifier = dexModifier;
        this.con = con;
        this.conModifier = conModifier;
        this.intel = intel;
        this.intelModifier = intelModifier;
        this.wis = wis;
        this.wisModifier = wisModifier;
    }
    // Goes up to Charisma modifier
    public MonObjects(String name, int amourClass, int hitP, String speed,
                      int str, int strModifier,
                      int dex, int dexModifier,
                      int con, int conModifier,
                      int intel, int intelModifier,
                      int wis, int wisModifier,
                      int cha, int chaModifier) {
        this.name = name;
        this.amourClass = amourClass;
        this.hitP = hitP;
        this.speed = speed;
        this.str = str;
        this.strModifier = strModifier;
        this.dex = dex;
        this.dexModifier = dexModifier;
        this.con = con;
        this.conModifier = conModifier;
        this.intel = intel;
        this.intelModifier = intelModifier;
        this.wis = wis;
        this.wisModifier = wisModifier;
        this.cha = cha;
        this.chaModifier = chaModifier;
    }

    // All of the basic Information
    public MonObjects(String name, int armorClass,
                      int hitP, String speed,
                      int str, int strModifier,
                      int dex, int dexModifier,
                      int con, int conModifier,
                      int intel, int intelModifier,
                      int wis, int wisModifier,
                      int cha, int chaModifier,
                      int[] savingThrows, int profiBonus,
                      int skillUp,
                      String[] damageRes,
                      String[] conditionImmun,
                      String[] senses,
                      String[] languages,
                      int challenge) {
        this.name = name;
        this.amourClass = armorClass;
        this.hitP = hitP;
        this.speed = speed;
        this.str = str;
        this.strModifier = strModifier;
        this.dex = dex;
        this.dexModifier = dexModifier;
        this.con = con;
        this.conModifier = conModifier;
        this.intel = intel;
        this.intelModifier = intelModifier;
        this.wis = wis;
        this.wisModifier = wisModifier;
        this.cha = cha;
        this.chaModifier = chaModifier;
        this.savingThrows = savingThrows;
        this.profiBonus = profiBonus;
        this.skillUp = skillUp;
        this.damageRes = damageRes;
        this.conditionImmun = conditionImmun;
        this.senses = senses;
        this.languages = languages;
        this.challenge = challenge;
    }

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public int getAmourClass() {return amourClass;}
    public void setAmourClass(int amourClass) { this.amourClass = amourClass; }

    public int getHitP() { return hitP; }
    public void setHitP(int hitP) { this.hitP = hitP; }

    public String getSpeed() {
        if(this.speed != null) return speed;
        else return "N/A"; }
    public void setSpeed(String speed) { this.speed = speed; }

    public int getStr() { return str; }
    public void setStr(int str) { this.str = str; }
    public int getStrModifier() { return strModifier; }
    public void setStrModifier(int strModifier) { this.strModifier = strModifier; }

    public int getDex() { return dex; }
    public void setDex(int dex) { this.dex = dex; }
    public int getDexModifier() { return dexModifier; }
    public void setDexModifier(int dexModifier) { this.dexModifier = dexModifier; }

    public int getCon() { return con;}
    public void setCon(int con) { this.con = con; }
    public int getConModifier() { return conModifier; }
    public void setConModifier(int conModifier) { this.conModifier = conModifier; }

    public int getIntel() { return intel; }
    public void setIntel(int intel) { this.intel = intel; }
    public int getIntelModifier() { return intelModifier; }
    public void setIntelModifier(int intelModifier) { this.intelModifier = intelModifier; }

    public int getWis() { return wis; }
    public void setWis(int wis) { this.wis = wis; }
    public int getWisModifier() { return wisModifier; }
    public void setWisModifier(int wisModifier) { this.wisModifier = wisModifier; }

    public int getCha() { return cha; }
    public void setCha(int cha) { this.cha = cha; }
    public int getChaModifier() { return chaModifier; }
    public void setChaModifier(int chaModifier) { this.chaModifier = chaModifier; }

    public int[] getSavingThrows() { return savingThrows; }
    public void setSavingThrows(int[] savingThrows) { this.savingThrows = savingThrows; }

    public int getProfiBonus() { return profiBonus; }
    public void setProfiBonus(int profiBonus) { this.profiBonus = profiBonus; }

    public int getSkillUp() { return skillUp; }
    public void setSkillUp(int skillUp) { this.skillUp = skillUp; }

    public String[] getDamageRes() { return damageRes; }
    public void setDamageRes(String[] damageRes) { this.damageRes = damageRes; }

    public String[] getConditionImmun() { return conditionImmun; }
    public void setConditionImmun(String[] conditionImmun) { this.conditionImmun = conditionImmun; }

    public String[] getSenses() { return senses; }
    public void setSenses(String[] senses) { this.senses = senses; }

    public String[] getLanguages() { return languages; }
    public void setLanguages(String[] languages) { this.languages = languages; }

    public int getChallenge() { return challenge; }
    public void setChallenge(int challenge) { this.challenge = challenge; }
}