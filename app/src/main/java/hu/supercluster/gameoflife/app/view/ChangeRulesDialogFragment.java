package hu.supercluster.gameoflife.app.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.app.preset.Preset;
import hu.supercluster.gameoflife.app.preset.PresetAdapter;
import hu.supercluster.gameoflife.app.preset.Type;
import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;
import hu.supercluster.gameoflife.util.EventBus;

public class ChangeRulesDialogFragment extends DialogFragment {
    private Set<Integer> survivalNbCounts;
    private Set<Integer> creationNbCounts;

    Spinner presets;
    TableRow survivalCheckBoxes;
    TableRow creationCheckBoxes;
    View infoWrapperTitle;
    View infoWrapper;
    TextView type;
    TextView info;

    public void setRule(NeighborCountBasedRule rule) {
        survivalNbCounts = rule.getSurvivalNbCounts();
        creationNbCounts = rule.getCreationNbCounts();
        updateUiForCurrentRule();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_change_rules, null);
        presets = (Spinner) view.findViewById(R.id.presets);
        survivalCheckBoxes = (TableRow) view.findViewById(R.id.survivalCheckBoxes);
        creationCheckBoxes = (TableRow) view.findViewById(R.id.creationCheckBoxes);
        infoWrapperTitle = view.findViewById(R.id.infoWrapperTitle);
        infoWrapper = view.findViewById(R.id.infoWrapper);
        type = (TextView) view.findViewById(R.id.type);
        info = (TextView) view.findViewById(R.id.info);
        info.setClickable(true);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInfoClicked();
            }
        });

        presets.setAdapter(new PresetAdapter(getActivity()));
        presets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                Preset preset = (Preset) adapter.getItemAtPosition(position);
                NeighborCountBasedRule rule = preset.getRule();

                if (rule != null) {
                    Set<Integer> creationNbCounts = rule.getCreationNbCounts();
                    Set<Integer> survivalNbCounts = rule.getSurvivalNbCounts();

                    for (int i = 0; i < 9; i++) {
                        CheckBox creation = (CheckBox) creationCheckBoxes.getChildAt(i);
                        CheckBox survival = (CheckBox) survivalCheckBoxes.getChildAt(i);
                        creation.setChecked(creationNbCounts.contains(i));
                        survival.setChecked(survivalNbCounts.contains(i));
                    }

                    type.setText(preset.getType().labelResId);
                    infoWrapperTitle.setVisibility(View.VISIBLE);
                    infoWrapper.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        for (int i = 0; i < 9; i++) {
            handleSurvivalCheckBox(i);
            handleCreationCheckBox(i);
        }

        builder
            .setView(view)
            .setTitle(R.string.dialog_change_rules_title)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    EventBus.getInstance().post(getRule());
                    dialog.dismiss();
                }
            })

            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ChangeRulesDialogFragment.this.getDialog().cancel();
                }
            })
        ;

        AlertDialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        setWidth();
        updateUiForCurrentRule();
    }

    protected void setWidth() {
        Resources resources = getActivity().getResources();
        int width = (int) resources.getDimension(R.dimen.dialog_change_rules_width);

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(window.getAttributes());
        lp.width = width;
        window.setAttributes(lp);
    }

    protected void handleCreationCheckBox(int i) {
        CheckBox checkBox = (CheckBox) creationCheckBoxes.findViewWithTag(String.valueOf(i));
        checkBox.setChecked(creationNbCounts != null && creationNbCounts.contains(i));
        onSelectionChangeShowCustomLabel(checkBox);
    }

    protected void handleSurvivalCheckBox(int i) {
        CheckBox checkBox = (CheckBox) survivalCheckBoxes.findViewWithTag(String.valueOf(i));
        checkBox.setChecked(survivalNbCounts != null && survivalNbCounts.contains(i));
        onSelectionChangeShowCustomLabel(checkBox);
    }

    private void onSelectionChangeShowCustomLabel(CheckBox checkBox) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUiForCurrentRule();
            }
        });
    }

    protected void updateUiForCurrentRule() {
        if (getDialog() == null) {
            return;
        }

        if (!selectPresetIfPossible()) {
            showCustom();
        }
    }

    private boolean selectPresetIfPossible() {
        PresetAdapter adapter = (PresetAdapter) presets.getAdapter();
        List<Preset> items = adapter.getPresets();
        NeighborCountBasedRule currentRule = getRule();

        for (int i = PresetAdapter.POS_FIRST; i < items.size(); i++) {
            Preset preset = items.get(i);
            NeighborCountBasedRule presetRule = preset.getRule();

            if (presetRule != null && presetRule.equals(currentRule)) {
                presets.setSelection(i);

                return true;
            }
        }

        return false;
    }

    protected void showCustom() {
        presets.setSelection(PresetAdapter.POS_CUSTOM);
        infoWrapperTitle.setVisibility(View.GONE);
        infoWrapper.setVisibility(View.GONE);
    }

    public NeighborCountBasedRule getRule() {
        Set<Integer> survivalNbCounts = new HashSet<>();
        Set<Integer> creationNbCounts = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            CheckBox survivalCheckbox = (CheckBox) survivalCheckBoxes.getChildAt(i);
            if (survivalCheckbox.isChecked()) {
                survivalNbCounts.add(i);
            }

            CheckBox creationCheckbox = (CheckBox) creationCheckBoxes.getChildAt(i);
            if (creationCheckbox.isChecked()) {
                creationNbCounts.add(i);
            }
        }

        return new NeighborCountBasedRule(survivalNbCounts, creationNbCounts);
    }

    void onInfoClicked() {
        Preset preset = (Preset) presets.getSelectedItem();
        Type type = preset.getType();

        if (type != null) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(type.labelResId)
                    .setMessage(type.descriptionResId)
                    .create()
            ;

            dialog.show();
        }
    }
}
