package com.igorwojda.showcase.feature.mock.domain.repository

import com.igorwojda.showcase.feature.mock.domain.model.AlbumDomainModel

internal interface AlbumRepository {

    suspend fun getAlbumInfo(artistName: String, albumName: String, mbId: String?): AlbumDomainModel?

    suspend fun searchAlbum(phrase: String): List<AlbumDomainModel>
}