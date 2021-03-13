package tech.houssemnasri.api.grid;

public interface IGridMagnifier {
    void zoomIn();
    void zoomOut();

    void setGridView(IGridView gridView);
    double getMinScale();
    double getMaxScale();
    double getDelta();
    boolean isAnimate();
    void setAnimate(boolean animate);
}
