package com.sebasorozcob.www.foodtil.data.network

import com.sebasorozcob.www.foodtil.data.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {

    val remote = remoteDataSource
}