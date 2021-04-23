package tech.houssemnasri.node.skins;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import tech.houssemnasri.node.INode;
import tech.houssemnasri.node.PNodeView;
import tech.houssemnasri.pathfinder.PathCost;

/** This skin will display cost information on the node. */
public class WithCostNodeSkin extends BaseNodeSkin implements PathCost.Listener {
  private Text t1, t2, t3;

  public WithCostNodeSkin(PNodeView control) {
    super(control);
    initGraphics();
    registerListeners();
  }

  private void initGraphics() {
    t1 = new Text("");
    t1.getStyleClass().setAll("primary-text");
    t2 = new Text("");
    t2.getStyleClass().setAll("secondary-text");
    t3 = new Text("");
    t3.getStyleClass().setAll("secondary-text");

    StackPane.setAlignment(t1, Pos.BOTTOM_CENTER);
    StackPane.setMargin(t1, new Insets(0, 0, 3, 0));
    StackPane.setAlignment(t2, Pos.TOP_LEFT);
    StackPane.setAlignment(t3, Pos.TOP_RIGHT);

    handleTypeChanged(null, null, getSkinnable().getType());
  }

  private void registerListeners() {
    getSkinnable()
        .getNodeModel()
        .getPathCost()
        .getCostArguments()
        .addListener(this::handleCostChanged);
    getSkinnable().typeProperty().addListener(this::handleTypeChanged);
    getSkinnable().getNodeModel().getPathCost().registerListener(this);
  }

  private void handleCostChanged(ListChangeListener.Change<? extends Integer> change) {
    while (change.next()) {
      if (change.getAddedSize() == 1) {
        String newValue = String.valueOf(change.getAddedSubList().get(0));
        switch (change.getFrom()) {
          case 0:
            t1.setText(newValue);
            break;
          case 1:
            t2.setText(newValue);
            break;
          case 2:
            t3.setText(newValue);
            break;
        }
      }
    }
  }

  private void handleTypeChanged(ObservableValue<?> obs, INode.Type old, INode.Type type) {
    switch (type) {
      case OPEN:
      case CLOSED:
      case PATH:
        setShowCost(true);
        addCostViews();
        break;
      default:
        setShowCost(false);
        removeCostViews();
    }
  }

  private void setShowCost(boolean value) {
    t1.setVisible(value);
    t2.setVisible(value);
    t3.setVisible(value);
  }

  private void removeCostViews() {
    getContainer().getChildren().removeAll(t1, t2, t3);
  }

  private void addCostViews() {
    if (!getContainer().getChildren().contains(t1)) getContainer().getChildren().addAll(t1, t2, t3);
  }

  private void clearCostViews() {
    t1.setText("");
    t2.setText("");
    t3.setText("");
  }

  @Override
  public BaseNodeSkin newInstance(PNodeView control) {
    return new WithCostNodeSkin(control);
  }

  @Override
  public void onCostCleared() {
    clearCostViews();
  }
}
