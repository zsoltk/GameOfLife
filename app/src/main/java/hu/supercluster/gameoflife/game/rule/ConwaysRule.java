package hu.supercluster.gameoflife.game.rule;

import java.util.Arrays;
import java.util.HashSet;

public class ConwaysRule extends NeighborCountBasedRule {
    public ConwaysRule() {
        super(
                new HashSet<>(Arrays.asList(2, 3)),
                new HashSet<>(Arrays.asList(3))
        );
    }
}
