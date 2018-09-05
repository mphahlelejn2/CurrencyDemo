package com.jeffreymphahlele.jeffdemo.photosFolder;

import com.jeffreymphahlele.jeffdemo.repo.IRepository;
import com.jeffreymphahlele.jeffdemo.rxjava.BaseSchedulerProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class FolderModule {

    @Binds
    abstract com.jeffreymphahlele.jeffdemo.photosFolder.IFolder.View getView(FolderFragment folderFragment );

    @Provides
    static com.jeffreymphahlele.jeffdemo.photosFolder.IFolder.Presenter getPhotosPresente(com.jeffreymphahlele.jeffdemo.photosFolder.IFolder.View view, IRepository repository, BaseSchedulerProvider provider){
        return new com.jeffreymphahlele.jeffdemo.photosFolder.FolderPresenterImpl(view,repository,provider);
    }

}