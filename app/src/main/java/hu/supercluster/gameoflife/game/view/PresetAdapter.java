package hu.supercluster.gameoflife.game.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;

public class PresetAdapter extends ArrayAdapter<Preset> {
    public PresetAdapter(Context context, int resource) {
        super(context, resource, getPresets(context));
    }

    private static List<Preset> getPresets(Context context) {
        final ArrayList<Preset> presets = new ArrayList<>();
        final Resources resources = context.getResources();
        presets.add(new Preset(resources.getString(R.string.preset_pick_one), null));
        presets.add(new Preset(resources.getString(R.string.preset_life), createRule(set(2, 3), set(3))));
        presets.add(new Preset(resources.getString(R.string.preset_sponge_cells), createRule(set(0, 1, 2, 3, 4, 5, 6, 7), set(4, 5, 6))));
        presets.add(new Preset(resources.getString(R.string.preset_slowly_growing_fizz), createRule(set(4, 5), set(3, 4, 5))));
        presets.add(new Preset(resources.getString(R.string.preset_drills), createRule(set(2, 4, 5, 6, 7, 8), set(3))));
        presets.add(new Preset(resources.getString(R.string.preset_condensation), createRule(set(1, 2, 5, 6, 7, 8), set(3, 6, 7))));
        presets.add(new Preset(resources.getString(R.string.preset_seeds), createRule(set(), set(2))));
        presets.add(new Preset(resources.getString(R.string.preset_spread), createRule(set(0, 1, 2, 3, 4, 5, 6, 7, 8), set(3))));

        return presets;
    }

    @NonNull
    private static Set<Integer> set(Integer... ints) {
        return new HashSet<>(Arrays.asList(ints));
    }

    private static NeighborCountBasedRule createRule(Set<Integer> survivalNbCounts, Set<Integer> creationNbCounts) {
        return new NeighborCountBasedRule(survivalNbCounts, creationNbCounts);
    }
}
