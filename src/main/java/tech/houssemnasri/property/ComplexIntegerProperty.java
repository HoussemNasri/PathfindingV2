package tech.houssemnasri.property;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

/**
 * The {@code ComplexIntegerProperty} is a special case of {@code SimpleIntegerProperty} where
 * listeners will get notified with the current value as soon as it's attached rather than waiting *
 * for a change.
 */
public class ComplexIntegerProperty extends SimpleIntegerProperty {
    // We only want to notify the listener when there is a initial value.
    private boolean initialized;

    public ComplexIntegerProperty() {
        super();
    }

    public ComplexIntegerProperty(int initialValue) {
        super(initialValue);
        // Client used the constructor with initialValue, meaning we have a initial value.
        initialized = true;
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        super.addListener(listener);
        // Notifying the listener as soon as it's attached, and we have an initial Value.
        if (getValue() != 0 || initialized) {
            listener.changed(this, getValue(), getValue());
            initialized = true;
        }
    }
}
