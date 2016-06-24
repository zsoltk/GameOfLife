package hu.supercluster.gameoflife.app.view;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import org.androidannotations.annotations.EBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.app.preset.Preset;
import hu.supercluster.gameoflife.app.preset.PresetAdapter;
import hu.supercluster.gameoflife.app.preset.Type;
import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;

@EBean
public class ChangeRulesDialogUiHelper {
    private ChangeRulesDialogFragment fragment;

    void setFragment(ChangeRulesDialogFragment fragment) {
        this.fragment = fragment;
    }

    void setWidth() {
        Resources resources = fragment.getActivity().getResources();
        int width = (int) resources.getDimension(R.dimen.dialog_change_rules_width);

        Window window = fragment.getDialog().getWindow();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(window.getAttributes());
        lp.width = width;
        window.setAttributes(lp);
    }

    void setCheckboxStates() {
        Set<Integer> creationNbCounts = fragment.rule.getCreationNbCounts();
        Set<Integer> survivalNbCounts = fragment.rule.getSurvivalNbCounts();

        for (int i = 0; i < 9; i++) {
            CheckBox creation = (CheckBox) fragment.creationCheckBoxes.getChildAt(i);
            CheckBox survival = (CheckBox) fragment.survivalCheckBoxes.getChildAt(i);
            creation.setChecked(creationNbCounts.contains(i));
            survival.setChecked(survivalNbCounts.contains(i));
        }
    }

    void selectMatchingPresetOrShowCustom() {
        if (fragment.getDialog() == null) {
            return;
        }

        if (!selectPresetIfPossible()) {
            showCustom();
        }
    }

    private boolean selectPresetIfPossible() {
        PresetAdapter adapter = (PresetAdapter) fragment.presets.getAdapter();
        List<Preset> items = adapter.getPresets();

        for (int i = PresetAdapter.POS_FIRST; i < items.size(); i++) {
            Preset preset = items.get(i);
            NeighborCountBasedRule presetRule = preset.getRule();

            if (presetRule != null && presetRule.equals(fragment.rule)) {
                fragment.presets.setSelection(i);

                return true;
            }
        }

        return false;
    }

    private void showCustom() {
        fragment.presets.setSelection(PresetAdapter.POS_CUSTOM);
        fragment.infoWrapperTitle.setVisibility(View.GONE);
        fragment.infoWrapper.setVisibility(View.GONE);
    }

    NeighborCountBasedRule createRuleFromCheckboxStates() {
        Set<Integer> survivalNbCounts = new HashSet<>();
        Set<Integer> creationNbCounts = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            CheckBox survivalCheckbox = (CheckBox) fragment.survivalCheckBoxes.getChildAt(i);
            if (survivalCheckbox.isChecked()) {
                survivalNbCounts.add(i);
            }

            CheckBox creationCheckbox = (CheckBox) fragment.creationCheckBoxes.getChildAt(i);
            if (creationCheckbox.isChecked()) {
                creationNbCounts.add(i);
            }
        }

        return new NeighborCountBasedRule(survivalNbCounts, creationNbCounts);
    }

    void showInfoDialog() {
        Preset preset = (Preset) fragment.presets.getSelectedItem();
        Type type = preset.getType();

        if (type != null) {
            AlertDialog dialog = new AlertDialog.Builder(fragment.getActivity())
                    .setTitle(type.labelResId)
                    .setMessage(type.descriptionResId)
                    .create()
                    ;

            dialog.show();
        }
    }

    public void showTypeInfo(Preset preset) {
        fragment.type.setText(preset.getType().labelResId);
        fragment.infoWrapperTitle.setVisibility(View.VISIBLE);
        fragment.infoWrapper.setVisibility(View.VISIBLE);
    }
}
