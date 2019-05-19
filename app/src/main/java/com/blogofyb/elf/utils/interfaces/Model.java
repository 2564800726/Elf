package com.blogofyb.elf.utils.interfaces;

import java.util.List;

public interface Model<T> {

    void get(Callback<T> callback);

}
