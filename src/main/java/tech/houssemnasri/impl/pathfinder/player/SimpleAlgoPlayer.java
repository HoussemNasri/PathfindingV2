package tech.houssemnasri.impl.pathfinder.player;

import javafx.animation.AnimationTimer;

import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.BaseAlgorithmPlayer;

public class SimpleAlgoPlayer extends BaseAlgorithmPlayer {
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
                        if (start == -1 || now - start >= 15000000) {
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
