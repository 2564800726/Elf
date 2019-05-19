package com.blogofyb.elf.presenters;

import com.blogofyb.elf.utils.interfaces.Callback;
import com.blogofyb.elf.utils.interfaces.Model;
import com.blogofyb.elf.utils.interfaces.Presenter;
import com.blogofyb.elf.utils.interfaces.View;

public abstract class BasedPresenter<T> implements Presenter<T> {
    protected View<T> mView;
    protected Model<T> mModel;

    @Override
    public void bind(View<T> view) {
        mView = view;
    }

    @Override
    public void unBind(View<T> view) {
        if (mView == view) {
            mView = null;
        }
    }

    @Override
    public void bind(Model<T> model) {
        mModel = model;
    }

    @Override
    public void unBind(Model<T> model) {
        if (mModel == model) {
            mModel = null;
        }
    }
}
