package hu.supercluster.gameoflife.app.preset;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

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
        presets.add(new Preset(resources.getString(R.string.preset_life), "23/3"));
        presets.add(new Preset(resources.getString(R.string.preset_high_life), "23/36"));
        presets.add(new Preset(resources.getString(R.string.preset_sponge_cells), "01234567/456"));
        presets.add(new Preset(resources.getString(R.string.preset_slowly_growing_fizz), "45/345"));
        presets.add(new Preset(resources.getString(R.string.preset_drills), "245678/3"));
        presets.add(new Preset(resources.getString(R.string.preset_condensation), "125678/367"));
        presets.add(new Preset(resources.getString(R.string.preset_seeds), "/2"));
        presets.add(new Preset(resources.getString(R.string.preset_spread), "012345678/3"));
        presets.add(new Preset(resources.getString(R.string.preset_maze), "12345/3"));
        presets.add(new Preset(resources.getString(R.string.preset_amoeba), "1358/357"));
        presets.add(new Preset(resources.getString(R.string.preset_diamoeba), "5678/35678"));
        presets.add(new Preset(resources.getString(R.string.preset_memory), "1357/1357"));

        return presets;
    }

    private static NeighborCountBasedRule createRule(String rule) {
        return new NeighborCountBasedRule(rule);
    }
}
