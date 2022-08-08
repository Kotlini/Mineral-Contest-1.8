package fr.ylanouh.mineralcontest.game.teams;

import fr.ylanouh.mineralcontest.game.classement.ClassementEnum;

public enum Teams {
    DARK_GREEN("§2Verte", "§f[§2Verte§f]"),
    BLUE("§9Bleu", "§f[§9Bleu§f]"),
    RED("§cRouge", "§f[§cRouge§f]"),
    YELLOW("§eJaune", "§f[§eJaune§f]"),
    ARENA("§3Arène", "§f[§3Arène§f]");

    private String name;

    private String prefix;

    private ClassementEnum classementEnum;

    Teams(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public ClassementEnum getClassementEnum() {
        return classementEnum;
    }

    public void setClassementEnum(ClassementEnum classementEnum) {
        this.classementEnum = classementEnum;
    }
}