package com.blogofyb.elf.utils.interfaces;

public interface Presenter<T> {

    void bind(View<T> view);

    void unBind(View<T> view);

    void bind(Model<T> model);

    void unBind(Model<T> model);

    void showData();

}
