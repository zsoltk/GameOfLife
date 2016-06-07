package hu.supercluster.gameoflife.app.rate.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import org.androidannotations.annotations.EFragment;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.app.rate.event.SuggestDialogNoEvent;
import hu.supercluster.gameoflife.app.rate.event.SuggestDialogYesEvent;
import hu.supercluster.gameoflife.util.EventBus;

@EFragment
public class SuggestDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setCancelable(false)
                .setTitle(R.string.dialog_suggest_title)
                .setMessage(R.string.dialog_suggest_body)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getInstance().post(new SuggestDialogYesEvent());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getInstance().post(new SuggestDialogNoEvent());
                        dialog.cancel();
                    }
                })
        ;

        return builder.create();
    }
}
