package tech.houssemnasri.api.grid;

public interface IGridMagnifier {
    /** Zoom in, and increase {@code GridView} scale. */
    void zoomIn();
    /** Zoom out, and decrease {@code GridView} scale. */
    void zoomOut();

    void setGridView(IGridView gridView);
    /** Returns the minimum zooming level we can reach. */
    double getMinScale();
    /** Returns the maximum zooming level we can reach. */
    double getMaxScale();
    /** Returns the amount of which the scale increase or decrease each zoomIn or zoomOut */
    double getDelta();
    /** Returns whether the zooming should animate or not. */
    boolean isAnimate();
    /**
     * Set animate to {@code animate}.
     *
     * @param animate if True zooming will animate
     */
    void setAnimate(boolean animate);
}
