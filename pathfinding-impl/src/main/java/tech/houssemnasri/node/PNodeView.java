package tech.houssemnasri.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.BooleanConverter;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import tech.houssemnasri.node.INode;
import tech.houssemnasri.node.INodeView;
import tech.houssemnasri.node.skins.SimpleNodeSkin;

import static tech.houssemnasri.node.INode.Type;

@SuppressWarnings("DanglingJavadoc")
public class PNodeView extends Control implements INodeView {
  private final INode nodeModel;
  private final ReadOnlyObjectWrapper<Type> type = new ReadOnlyObjectWrapper<>(Type.BASIC);
  private final BooleanProperty animated =
      new StyleableBooleanProperty() {
        @Override
        public CssMetaData<? extends Styleable, Boolean> getCssMetaData() {
          return StyleableProperties.ANIMATED;
        }

        @Override
        public Object getBean() {
          return PNodeView.this;
        }

        @Override
        public String getName() {
          return "animated";
        }
      };

  public PNodeView(INode nodeModel, boolean isAnimated) {
    getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    setAnimated(isAnimated);
    this.nodeModel = nodeModel;
    type.bind(nodeModel.nodeTypeProperty());
  }

  public PNodeView(INode nodeModel) {
    this(nodeModel, false);
  }

  public PNodeView(INodeView copyNodeView) {
    this(new PNode(copyNodeView.getNodeModel()));
  }

  @Override
  public INode getNodeModel() {
    return nodeModel;
  }

  @Override
  public void setAnimated(boolean value) {
    animated.set(value);
  }

  @Override
  public boolean isAnimated() {
    return animated.get();
  }

  @Override
  public BooleanProperty animatedProperty() {
    return animated;
  }

  @Override
  public Control getRoot() {
    return this;
  }

  @Override
  protected Skin<?> createDefaultSkin() {
    return new SimpleNodeSkin(this);
  }

  @Override
  public void refresh() {
    applyStyle();
  }

  public ReadOnlyObjectProperty<Type> typeProperty() {
    return type.getReadOnlyProperty();
  }

  public Type getType() {
    return type.get();
  }

  public void applyStyle(Type nodeType) {
    pseudoClassStateChanged(PSEUDO_CLASS_OPEN, nodeType == Type.OPEN);
    pseudoClassStateChanged(PSEUDO_CLASS_CLOSED, nodeType == Type.CLOSED);
    pseudoClassStateChanged(PSEUDO_CLASS_PATH, nodeType == Type.PATH);
    pseudoClassStateChanged(PSEUDO_CLASS_WALL, nodeType == Type.WALL);
    pseudoClassStateChanged(PSEUDO_CLASS_SOURCE, nodeType == Type.SOURCE);
    pseudoClassStateChanged(PSEUDO_CLASS_DESTINATION, nodeType == Type.DESTINATION);
  }

  public void applyStyle() {
    applyStyle(getType());
  }

  /***************************************************************************
   *                                                                         *
   * Stylesheet Handling                                                     *
   *                                                                         *
   **************************************************************************/

  /**
   * Initialize the style class to 'PNodeView'.
   *
   * <p>This is the selector class from which CSS can be used to style this control.
   */
  private static final String DEFAULT_STYLE_CLASS = "node-view";

  private static final PseudoClass PSEUDO_CLASS_OPEN = PseudoClass.getPseudoClass("open");
  private static final PseudoClass PSEUDO_CLASS_CLOSED = PseudoClass.getPseudoClass("closed");
  private static final PseudoClass PSEUDO_CLASS_PATH = PseudoClass.getPseudoClass("path");
  private static final PseudoClass PSEUDO_CLASS_WALL = PseudoClass.getPseudoClass("wall");
  private static final PseudoClass PSEUDO_CLASS_SOURCE = PseudoClass.getPseudoClass("source");
  private static final PseudoClass PSEUDO_CLASS_DESTINATION =
      PseudoClass.getPseudoClass("destination");

  private static class StyleableProperties {
    private static final CssMetaData<PNodeView, Boolean> ANIMATED =
        new CssMetaData<>("-node-animated", BooleanConverter.getInstance(), Boolean.FALSE) {

          @Override
          public boolean isSettable(PNodeView n) {
            return n.animated == null || !n.animated.isBound();
          }

          @Override
          public StyleableProperty<Boolean> getStyleableProperty(PNodeView n) {
            return (StyleableProperty<Boolean>) n.animatedProperty();
          }
        };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static {
      final List<CssMetaData<? extends Styleable, ?>> styleables =
          new ArrayList<>(Control.getClassCssMetaData());
      styleables.add(ANIMATED);
      STYLEABLES = Collections.unmodifiableList(styleables);
    }
  }

  public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
    return StyleableProperties.STYLEABLES;
  }

  @Override
  public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
    return getClassCssMetaData();
  }

  @Override
  public String getUserAgentStylesheet() {
    return getClass().getResource("/nodeview.css").toExternalForm();
  }
}
