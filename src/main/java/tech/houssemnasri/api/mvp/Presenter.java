package tech.houssemnasri.api.mvp;

public interface Presenter<V extends PresentableView<?, ?>> {
    void setView(V view);
}
