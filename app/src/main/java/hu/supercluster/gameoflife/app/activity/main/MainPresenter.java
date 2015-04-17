package hu.supercluster.gameoflife.app.activity.main;

import android.graphics.Point;
import android.view.Display;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cellularautomaton.GameOfLifeFactory;
import hu.supercluster.gameoflife.game.cellularautomaton.Fill;
import hu.supercluster.gameoflife.game.creator.GameCreator;
import hu.supercluster.gameoflife.game.creator.GameParams;
import hu.supercluster.gameoflife.game.creator.GameParamsBuilder;

@EBean
public class MainPresenter {
    @RootContext
    MainActivity activity;

    protected void createGame() {
        GameParams gameParams = GameParamsBuilder.create()
                .setDisplaySize(getDisplaySize())
                .setCellSizeInPixels(6)
                .setFill(new Fill(0.10f, Cell.STATE_ALIVE))
                .setFps(15)
                .build()
        ;

        GameCreator.create(
                activity,
                activity.layout,
                new GameOfLifeFactory(),
                gameParams
        );
    }

    private Point getDisplaySize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }
}
