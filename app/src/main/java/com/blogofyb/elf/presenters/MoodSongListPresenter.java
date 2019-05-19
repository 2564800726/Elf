package com.blogofyb.elf.presenters;

import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.interfaces.Callback;

import java.util.List;

public class MoodSongListPresenter extends BasedPresenter<MusicBean>{
    @Override
    public void showData() {
        if (mModel != null) {
            mModel.get(new Callback<MusicBean>() {
                @Override
                public void success(List<MusicBean> data) {
                    if (mView != null) {
                        mView.show(data);
                    }
                }

                @Override
                public void failed(Exception e) {

                }
            });
        }
    }
}
