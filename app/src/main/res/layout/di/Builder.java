package com.jeffreymphahlele.jeffdemo.di;
import com.jeffreymphahlele.jeffdemo.photosFolder.FolderFragment;
import com.jeffreymphahlele.jeffdemo.photosFolder.FolderModule;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = AndroidInjectionModule.class)
public abstract class Builder {
    @com.jeffreymphahlele.jeffdemo.di.PerActivity
    @ContributesAndroidInjector(modules = FolderModule.class)
    abstract FolderFragment bindPhotosFragment();

}
