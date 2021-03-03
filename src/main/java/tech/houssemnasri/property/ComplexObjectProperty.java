package tech.houssemnasri.property;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * The {@code ComplexObjectProperty} is a special case of {@code SimpleObjectProperty} where
 * listeners will get notified with the current value as soon as they are attached rather than
 * waiting for an update
 */
public class ComplexObjectProperty<T> extends SimpleObjectProperty<T> {
    // We only want to notify the listener when there is a initial value.
    private boolean initialized;

    public ComplexObjectProperty() {
        super();
    }

    public ComplexObjectProperty(T initialValue) {
        super(initialValue);
        // Client used the constructor with initialValue, meaning we have a initial value.
        initialized = true;
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        super.addListener(listener);
        if (getValue() != null || initialized) {
            listener.changed(this, getValue(), getValue());
            initialized = true;
        }
    }
}
