package hu.supercluster.gameoflife.app.preset;

import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;

public class Preset {
    private final String name;
    private final Type type;
    private final NeighborCountBasedRule rule;

    public Preset(String name, String rule, Type type) {
        this.name = name;
        this.type = type;
        this.rule = rule == null ? null : new NeighborCountBasedRule(rule);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public NeighborCountBasedRule getRule() {
        return rule;
    }

    public Type getType() {
        return type;
    }
}
