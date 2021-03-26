package tech.houssemnasri.api.toolbox;

import tech.houssemnasri.api.mvp.Presenter;

public interface IToolboxPresenter extends Presenter<IToolboxView> {
    void setToolbox(IToolbox toolbox);
}
