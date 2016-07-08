package hu.supercluster.gameoflife.app.rate.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import org.androidannotations.annotations.EFragment;

import hu.supercluster.gameoflife.R;
import hu.supercluster.gameoflife.app.rate.event.RatingDialogLaterEvent;
import hu.supercluster.gameoflife.app.rate.event.RatingDialogNoEvent;
import hu.supercluster.gameoflife.app.rate.event.RatingDialogYesEvent;
import hu.supercluster.gameoflife.util.EventBus;

@EFragment
public class RatingDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setCancelable(false)
                .setTitle(R.string.dialog_rate_title)
                .setMessage(R.string.dialog_rate_body)
                .setPositiveButton(R.string.dialog_rate_rate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getInstance().post(new RatingDialogYesEvent());
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(R.string.dialog_rate_later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getInstance().post(new RatingDialogLaterEvent());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_rate_no_thanks,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getInstance().post(new RatingDialogNoEvent());
                        dialog.cancel();
                    }
                })
        ;

        return builder.create();
    }
}
