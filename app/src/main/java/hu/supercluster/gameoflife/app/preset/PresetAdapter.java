package hu.supercluster.gameoflife.app.preset;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hu.supercluster.gameoflife.R;

public class PresetAdapter extends BaseAdapter {
    public static final int POS_PICK_ONE = 0;
    public static final int POS_CUSTOM = 1;
    private static ArrayList<Preset> presets;
    private final Context context;
    private LayoutInflater inflater;

    public PresetAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        presets = new ArrayList<>();
        Resources resources = context.getResources();
        presets.add(new Preset(resources.getString(R.string.preset_pick_one), null, null));
        presets.add(new Preset(resources.getString(R.string.preset_custom), null, null));
        presets.add(new Preset(resources.getString(R.string.preset_life), "23/3", Type.CHAOTIC));
        presets.add(new Preset(resources.getString(R.string.preset_high_life), "23/36", Type.CHAOTIC));
        presets.add(new Preset(resources.getString(R.string.preset_sponge_cells), "01234567/456", Type.STABLE));
        presets.add(new Preset(resources.getString(R.string.preset_slowly_growing_fizz), "45/345", Type.EXPANDING));
        presets.add(new Preset(resources.getString(R.string.preset_drills), "245678/3", Type.EXPANDING));
        presets.add(new Preset(resources.getString(R.string.preset_condensation), "125678/367", Type.EXPANDING));
        presets.add(new Preset(resources.getString(R.string.preset_seeds), "/2", Type.EXPLODING));
        presets.add(new Preset(resources.getString(R.string.preset_spread), "012345678/3", Type.EXPANDING));
        presets.add(new Preset(resources.getString(R.string.preset_maze), "12345/3", Type.EXPANDING));
        presets.add(new Preset(resources.getString(R.string.preset_amoeba), "1358/357", Type.CHAOTIC));
        presets.add(new Preset(resources.getString(R.string.preset_diamoeba), "5678/35678", Type.CHAOTIC));
        presets.add(new Preset(resources.getString(R.string.preset_coral), "45678/3", Type.EXPANDING));
        presets.add(new Preset(resources.getString(R.string.preset_memory), "1357/1357", Type.EXPLODING));
    }

    @Override
    public int getCount() {
        return presets.size();
    }

    @Override
    public Object getItem(int position) {
        return presets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        if (convertView == null) {
            textView = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        } else {
            textView = (TextView) convertView;
        }

        Preset preset = presets.get(position);
        textView.setText(preset.getName());

        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (position < 2) {
            return createHiddenView();
        }

        TextView listItem = getView(convertView);
        Preset preset = presets.get(position);
        listItem.setText(preset.getName());

        return listItem;
    }

    @NonNull
    protected View createHiddenView() {
        TextView hidden = new TextView(context);
        hidden.setHeight(0);
        hidden.setVisibility(View.GONE);

        return hidden;
    }

    protected TextView getView(View convertView) {
        TextView view;

        if (convertView == null || convertView.getHeight() == 0) {
            view = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        } else {
            view = (TextView) convertView;
        }

        return view;
    }
}
