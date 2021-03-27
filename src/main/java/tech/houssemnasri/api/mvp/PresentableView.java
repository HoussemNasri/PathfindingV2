package tech.houssemnasri.api.mvp;
/**
 * This interface must be implemented by all views that have a presenter.
 *
 * @param <P> the presenter type
 */
public interface PresentableView<P extends Presenter<?>, V> extends View<V> {
  /** Enforcing the MVP architecture pattern by having a reference to the view presenter. */
  void setPresenter(P presenter);
}
