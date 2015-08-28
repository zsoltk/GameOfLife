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
        this(2 * Runtime.getRuntime().availableProcessors());
    }

    public ThreadedGridTransformer(int threadCount) {
        this.threadCount = threadCount;
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public final void transform(Grid<T> grid, Rule<T> rule) {
        resetStateChanges(grid);
        doTransform(grid, rule);
    }

    protected void resetStateChanges(Grid<T> grid) {
        if (stateChanges == null) {
            stateChanges = new int[grid.getSizeY()][grid.getSizeX()];
        } else {
            doResetStateChanges(grid);
        }
    }

    private void doResetStateChanges(Grid<T> grid) {
        for (int j = 0; j < grid.getSizeY(); j++) {
            for (int i = 0; i < grid.getSizeX(); i++) {
                stateChanges[j][i] = 0;
            }
        }
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
                    computeNewGrid(grid, rule, getSubGridStartPoint(f, slice), getSubGridEndPoint(f, slice, grid));
                    countDownLatch.countDown();
                }
            });
        }
    }

    protected Point getSubGridStartPoint(int f, int slice) {
        int minX = f * slice;

        return new Point(minX, 0);
    }

    protected Point getSubGridEndPoint(int f, int slice, Grid<T> grid) {
        int maxX = f < threadCount - 1 ? (f + 1) * slice : grid.getSizeX();

        return new Point(maxX, grid.getSizeY());
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
                    applyNewGrid(grid, getSubGridStartPoint(f, slice), getSubGridEndPoint(f, slice, grid));
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
