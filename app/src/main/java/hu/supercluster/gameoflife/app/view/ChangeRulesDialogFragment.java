package hu.supercluster.gameoflife.app.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;

import java.util.Set;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.app.preset.Preset;
import hu.supercluster.gameoflife.app.preset.PresetAdapter;
import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;
import hu.supercluster.gameoflife.util.EventBus;

@EFragment
public class ChangeRulesDialogFragment extends DialogFragment {
    @InstanceState
    NeighborCountBasedRule rule;

    @Bean
    ChangeRulesDialogUiHelper uiHelper;

    Spinner presets;
    TableRow survivalCheckBoxes;
    TableRow creationCheckBoxes;
    View infoWrapperTitle;
    View infoWrapper;
    TextView type;
    TextView info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper.setFragment(this);
    }

    public void setRule(NeighborCountBasedRule rule) {
        this.rule = new NeighborCountBasedRule(rule);
        onRuleSet();
    }

    public NeighborCountBasedRule getRule() {
        return rule;
    }

    private void onRuleSet() {
        if (getDialog() != null) {
            initCheckboxes();
            uiHelper.selectMatchingPresetOrShowCustom();
        }
    }

    private void onCheckboxClicked() {
        NeighborCountBasedRule customRule = uiHelper.createRuleFromCheckboxStates();
        setRule(customRule);
    }

    private void onPresetSelected(Preset preset) {
        NeighborCountBasedRule presetRule = preset.getRule();

        if (presetRule != null && !rule.equals(presetRule)) {
            setRule(presetRule);
            uiHelper.showTypeInfo(preset);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = initViews();
        initInfo();
        initPresets();
        initCheckboxes();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setView(view)
            .setTitle(R.string.dialog_change_rules_title)
            .setPositiveButton(android.R.string.ok, getOkListener())
            .setNegativeButton(android.R.string.cancel, getCancelListener())
        ;

        return builder.create();
    }

    @NonNull
    protected View initViews() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_rules, null);
        presets = (Spinner) view.findViewById(R.id.presets);
        survivalCheckBoxes = (TableRow) view.findViewById(R.id.survivalCheckBoxes);
        creationCheckBoxes = (TableRow) view.findViewById(R.id.creationCheckBoxes);
        infoWrapperTitle = view.findViewById(R.id.infoWrapperTitle);
        infoWrapper = view.findViewById(R.id.infoWrapper);
        type = (TextView) view.findViewById(R.id.type);
        info = (TextView) view.findViewById(R.id.info);

        return view;
    }

    protected void initInfo() {
        info.setClickable(true);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiHelper.showInfoDialog();
            }
        });
    }

    protected void initPresets() {
        presets.setAdapter(new PresetAdapter(getActivity()));
        presets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                onPresetSelected((Preset) adapter.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    protected void initCheckboxes() {
        for (int i = 0; i < 9; i++) {
            initCheckBox(i, survivalCheckBoxes, rule.getSurvivalNbCounts());
            initCheckBox(i, creationCheckBoxes, rule.getCreationNbCounts());
        }
    }

    protected void initCheckBox(final int i, TableRow checkBoxes, Set<Integer> nbCounts) {
        final CheckBox checkBox = (CheckBox) checkBoxes.getChildAt(i);
        checkBox.setChecked(rule != null && nbCounts.contains(i));
        setOnClickListener(checkBox);
    }

    private void setOnClickListener(CheckBox checkBox) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked();
            }
        });
    }

    @NonNull
    protected DialogInterface.OnClickListener getOkListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EventBus.getInstance().post(rule);
                getDialog().dismiss();
            }
        };
    }

    @NonNull
    protected DialogInterface.OnClickListener getCancelListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getDialog().cancel();
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();

        uiHelper.setWidth();
        uiHelper.selectMatchingPresetOrShowCustom();
    }
}
