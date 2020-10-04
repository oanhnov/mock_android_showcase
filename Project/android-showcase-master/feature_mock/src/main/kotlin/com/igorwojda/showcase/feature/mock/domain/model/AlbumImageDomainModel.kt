package com.igorwojda.showcase.feature.mock.domain.model

import com.igorwojda.showcase.feature.mock.domain.enum.AlbumDomainImageSize

internal data class AlbumImageDomainModel(
    val url: String,
    val size: AlbumDomainImageSize
)