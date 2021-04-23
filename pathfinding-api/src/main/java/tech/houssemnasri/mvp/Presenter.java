package tech.houssemnasri.mvp;

public interface Presenter<V extends PresentableView<?, ?>> {
    void setView(V view);
}
