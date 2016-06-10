package hu.supercluster.gameoflife.app.preset;

import android.support.annotation.StringRes;

import hu.supercluster.gameoflife.R;

public enum Type{
    STABLE(R.string.preset_type_stable),
    EXPANDING(R.string.preset_type_expanding),
    EXPLODING(R.string.preset_type_exploding),
    CHAOTIC(R.string.preset_type_chaotic)
    ;

    public final int labelResId;

    Type(@StringRes int labelResId) {
        this.labelResId = labelResId;
    }
};
