package com.igorwojda.showcase.feature.mock.presentation

import androidx.fragment.app.Fragment
import com.igorwojda.showcase.feature.mock.MODULE_NAME
import com.igorwojda.showcase.feature.mock.presentation.albumlist.MockViewModel
import com.igorwojda.showcase.feature.mock.presentation.albumlist.recyclerview.ExploreActivitiesAdapter
import com.igorwojda.showcase.feature.mock.presentation.albumlist.recyclerview.FindNearbyAdapter
import com.igorwojda.showcase.feature.mock.presentation.albumlist.recyclerview.InspireYourselfAdapter
import com.igorwojda.showcase.library.base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

internal val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule") {

    // AlbumList
    bind<MockViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { MockViewModel(instance(), instance()) }
    }

    bind() from singleton { ExploreActivitiesAdapter() }
    bind() from singleton { InspireYourselfAdapter() }
    bind() from singleton { FindNearbyAdapter() }
	
}
