package tech.houssemnasri.api;

import javafx.scene.layout.StackPane;

/**
 * The {@code INodeView} is a self contained view we use to display our model {@code INode}, it
 * listens for changes on the model and update itself accordingly.
 */
public interface INodeView {
    /**
     * Showing cost info on the view.
     *
     * @param show If true cost information will be visible.
     */
    void showCostInfo(boolean show);

    /** Returns the current node size. */
    NodeSize getViewSize();

    void setViewSize(NodeSize newNodeSize);

    INode getNodeModel();


    /**
     * {@code NodeSize} represent the possible sizes a node could have, each size has a double value
     * represent both the width and height of the node, with an additional label for other views to
     * efficiently detect possible sizes.
     */
    enum NodeSize {
        XXSMALL(10, "XXSmall"),
        XSMALL(20, "XSmall"),
        SMALL(30, "Small"),
        MEDIUM(40, "Medium"),
        LARGE(60, "Large"),
        XLARGE(80, "XLarge");

        private final double size;
        private final String label;

        NodeSize(double size, String label) {
            this.size = size;
            this.label = label;
        }

        NodeSize(double size) {
            this.size = size;
            this.label = name();
        }

        public String getLabel() {
            return label;
        }

        public double getSize() {
            return size;
        }
    }
}
