package com.blogofyb.elf.models;

import com.blogofyb.elf.utils.interfaces.Model;

import java.util.List;
import java.util.Vector;

public abstract class BasedModel<T> implements Model<T> {
    protected List<T> mData;
}
