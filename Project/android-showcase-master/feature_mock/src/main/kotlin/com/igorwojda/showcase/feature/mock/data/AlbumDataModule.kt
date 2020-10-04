package com.igorwojda.showcase.feature.mock.data

import com.igorwojda.showcase.feature.mock.MODULE_NAME
import com.igorwojda.showcase.feature.mock.data.repository.AlbumRepositoryImpl
import com.igorwojda.showcase.feature.mock.data.retrofit.service.AlbumRetrofitService
import com.igorwojda.showcase.feature.mock.domain.repository.AlbumRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

internal val dataModule = Kodein.Module("${MODULE_NAME}DataModule") {

    bind<AlbumRepository>() with singleton { AlbumRepositoryImpl(instance()) }

    bind() from singleton { instance<Retrofit>().create(AlbumRetrofitService::class.java) }
}