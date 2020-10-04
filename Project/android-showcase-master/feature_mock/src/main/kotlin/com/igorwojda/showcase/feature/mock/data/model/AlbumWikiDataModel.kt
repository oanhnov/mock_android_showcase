package com.igorwojda.showcase.feature.mock.data.model

import com.igorwojda.showcase.feature.mock.domain.model.AlbumWikiDomainModel

internal data class AlbumWikiDataModel(
    val published: String,
    val summary: String
)

internal fun AlbumWikiDataModel.toDomainModel() = AlbumWikiDomainModel(
    published = this.published,
    summary = this.summary
)