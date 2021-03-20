package tech.houssemnasri.impl.pathfinder.player;

import javafx.animation.AnimationTimer;

import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.BaseAlgorithmPlayer;

public class SimpleAlgoPlayer extends BaseAlgorithmPlayer {
    private static final long SPEED_SLOW = 700000000;
    private static final long SPEED_MEDIUM = 70000000;
    private static final long SPEED_FAST = 12000000;

    public SimpleAlgoPlayer(BaseAlgorithm thisAlgorithm) {
        super(thisAlgorithm);
    }

    @Override
    public void forward() {

    }

    @Override
    public void back() {

    }

    @Override
    public void play() {
        AnimationTimer timer =
                new AnimationTimer() {
                    private long start = -1;
                    @Override
                    public void handle(long now) {
                        if (start == -1 || now - start >= SPEED_MEDIUM) {
                            start = now;
                            if (getAlgorithm().isPathFound()) {
                                this.stop();
                            } else {
                                getAlgorithm().forward();
                            }
                        }
                    }
                };
        timer.start();
    }

    @Override
    public void pause() {}

    @Override
    public void reset() {}
}
