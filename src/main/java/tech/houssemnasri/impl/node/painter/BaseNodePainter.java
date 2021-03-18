package tech.houssemnasri.impl.node.painter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.impl.node.PNodeView;

public abstract class BaseNodePainter {
    private final ObjectProperty<ITheme> themeProperty = new SimpleObjectProperty<>();
    private PNodeView nodeView;

    public BaseNodePainter(PNodeView nodeView, ITheme theme) {
        setNodeView(nodeView);
        switchTheme(theme);
    }

    private void setNodeView(PNodeView nodeView) {
        this.nodeView = nodeView;
    }

    public abstract void paint(INode.Type nodeType);

    public void switchTheme(ITheme newTheme) {
        themeProperty.set(newTheme);
    }

    public ITheme getTheme() {
        return themeProperty.get();
    }

    public ObjectProperty<ITheme> themeProperty() {
        return themeProperty;
    }

    public PNodeView getNodeView() {
        return nodeView;
    }
}
