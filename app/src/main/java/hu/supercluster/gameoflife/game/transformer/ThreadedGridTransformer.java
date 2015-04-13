package hu.supercluster.gameoflife.game.transformer;

import android.graphics.Point;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.rule.Rule;
import hugo.weaving.DebugLog;

public class ThreadedGridTransformer<T extends Cell> implements GridTransformer<T> {
    private final int threadCount;
    private ExecutorService executorService;
    private CountDownLatch countDownLatch;
    private int[][] stateChanges;

    public ThreadedGridTransformer() {
        this(4);
    }

    public ThreadedGridTransformer(int threadCount) {
        this.threadCount = threadCount;
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public final void transform(Grid<T> grid, Rule<T> rule) {
        stateChanges = new int[grid.getSizeY()][grid.getSizeX()];
        doTransform(grid, rule);
    }

    @DebugLog
    protected void doTransform(Grid<T> grid, Rule<T> rule) {
        resetLatch();
        threadedCompute(grid, rule);
        awaitLatch();

        resetLatch();
        threadedApply(grid, rule);
        awaitLatch();
    }

    private void resetLatch() {
        countDownLatch = new CountDownLatch(threadCount);
    }

    @DebugLog
    private void threadedCompute(final Grid<T> grid, final Rule<T> rule) {
        final int slice = grid.getSizeX() / threadCount;

        for (int i = 0; i < threadCount; i++) {
            final int f = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    computeNewGrid(grid, rule, new Point(f * slice, 0), new Point((f + 1) * slice, grid.getSizeY()));
                    countDownLatch.countDown();
                }
            });
        }
    }

    @DebugLog
    protected void computeNewGrid(Grid<T> grid, Rule<T> rule, Point min, Point max) {
        for (int j = min.y; j < max.y; j++) {
            for (int i = min.x; i < max.x; i++) {
                stateChanges[j][i] = rule.apply(grid, i, j);
            }
        }
    }

    @DebugLog
    private void awaitLatch() {
        try {
            countDownLatch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @DebugLog
    private void threadedApply(final Grid<T> grid, final Rule<T> rule) {
        final int slice = grid.getSizeX() / threadCount;

        for (int i = 0; i < threadCount; i++) {
            final int f = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    applyNewGrid(grid, new Point(f * slice, 0), new Point((f + 1) * slice, grid.getSizeY()));
                    countDownLatch.countDown();
                }
            });
        }
    }

    @DebugLog
    protected void applyNewGrid(Grid<T> grid, Point min, Point max) {
        for (int j = min.y; j < max.y; j++) {
            for (int i = min.x; i < max.x; i++) {
                grid.getCell(i, j).setState(stateChanges[j][i]);
            }
        }
    }
}
