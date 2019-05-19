package com.blogofyb.elf.utils.interfaces;

import java.util.List;

public interface Callback<T> {

    void success(List<T> data);

    void failed(Exception e);

}
