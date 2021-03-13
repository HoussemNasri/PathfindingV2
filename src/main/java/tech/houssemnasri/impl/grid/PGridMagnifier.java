package tech.houssemnasri.impl.grid;

import tech.houssemnasri.Clamp;
import tech.houssemnasri.api.grid.IGridMagnifier;
import tech.houssemnasri.api.grid.IGridView;

public class PGridMagnifier implements IGridMagnifier {
    private IGridView gridView;
    private final double minScale;
    private final double maxScale;
    private final double scaleDelta;
    private boolean animate;

    public PGridMagnifier(
            IGridView gridView,
            double minScale,
            double maxScale,
            double scaleDelta,
            boolean animate) {
        this.gridView = gridView;
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.scaleDelta = scaleDelta;
        this.animate = animate;
    }

    public PGridMagnifier(IGridView gridView, double minScale, double maxScale, double scaleDelta) {
        this(gridView, minScale, maxScale, scaleDelta, false);
    }

    public PGridMagnifier(IGridView gridView, double minScale, double maxScale) {
        this(gridView, minScale, maxScale, 1.2d);
    }

    public PGridMagnifier(IGridView gridView) {
        this(gridView, 1.0d, 10.0d);
    }

    @Override
    public void zoomIn() {
        double scale = gridView.getScale() * scaleDelta;
        gridView.setScale(new Clamp(scale).apply(minScale, maxScale), animate);
    }

    @Override
    public void zoomOut() {
        double scale = gridView.getScale() / scaleDelta;
        gridView.setScale(new Clamp(scale).apply(minScale, maxScale), animate);
    }

    @Override
    public void setGridView(IGridView gridView) {
        this.gridView = gridView;
    }

    @Override
    public double getMinScale() {
        return minScale;
    }

    @Override
    public double getMaxScale() {
        return maxScale;
    }

    @Override
    public double getDelta() {
        return scaleDelta;
    }

    @Override
    public boolean isAnimate() {
        return animate;
    }

    @Override
    public void setAnimate(boolean animate) {
        this.animate = animate;
    }
}
