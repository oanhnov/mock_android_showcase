package com.igorwojda.showcase.feature.mock

import com.igorwojda.showcase.app.feature.KodeinModuleProvider
import com.igorwojda.showcase.feature.mock.data.dataModule
import com.igorwojda.showcase.feature.mock.domain.domainModule
import com.igorwojda.showcase.feature.mock.presentation.presentationModule
import org.kodein.di.Kodein

internal const val MODULE_NAME = "Mock"

object FeatureKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}