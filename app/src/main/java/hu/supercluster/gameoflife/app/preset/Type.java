package hu.supercluster.gameoflife.app.preset;

import android.support.annotation.StringRes;

import hu.supercluster.gameoflife.R;

public enum Type{
    STABLE(R.string.preset_type_stable, R.string.preset_type_stable_description),
    EXPANDING(R.string.preset_type_expanding, R.string.preset_type_expanding_description),
    EXPLODING(R.string.preset_type_exploding, R.string.preset_type_exploding_description),
    CHAOTIC(R.string.preset_type_chaotic, R.string.preset_type_chaotic_description)
    ;

    public final int labelResId;
    public final int descriptionResId;

    Type(@StringRes int labelResId, @StringRes int descriptionResId) {
        this.labelResId = labelResId;
        this.descriptionResId = descriptionResId;
    }
};
