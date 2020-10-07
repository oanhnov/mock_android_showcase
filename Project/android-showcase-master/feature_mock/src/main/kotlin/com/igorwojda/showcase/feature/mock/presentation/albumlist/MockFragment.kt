package com.igorwojda.showcase.feature.mock.presentation.albumlist

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.igorwojda.showcase.feature.mock.R
import com.igorwojda.showcase.feature.mock.presentation.albumlist.recyclerview.ExploreActivitiesAdapter
import com.igorwojda.showcase.feature.mock.presentation.albumlist.recyclerview.FindNearbyAdapter
import com.igorwojda.showcase.feature.mock.presentation.albumlist.recyclerview.InspireYourselfAdapter
import com.igorwojda.showcase.library.base.presentation.extension.observe
import com.igorwojda.showcase.library.base.presentation.fragment.InjectionFragment
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_mock.*
import org.kodein.di.generic.instance

class MockFragment : InjectionFragment(R.layout.fragment_mock) {

    private val viewModel: MockViewModel by instance()

    private val exploreActivitiesAdapter: ExploreActivitiesAdapter by instance()
    private val inspireYourselfAdapter: InspireYourselfAdapter by instance()
    private val findNearbyAdapter: FindNearbyAdapter by instance()

    private val stateObserver = Observer<MockViewModel.ViewState> {
        exploreActivitiesAdapter.albums = it.albums
        inspireYourselfAdapter.albums = it.albums.subList(16, 25)
        findNearbyAdapter.albums = it.albums.subList(21, 40)
        progressBar.visible = it.isLoading
        layoutAlbum.visibility = View.VISIBLE
        llToolbar.visibility = View.GONE
        llToolbarShow.visibility = View.VISIBLE
        errorAnimation.visible = it.isError
        val fadeIn: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_show_recyclerview)
        rclExplore.startAnimation(fadeIn)
        rclInspire.startAnimation(fadeIn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(activity).inflateTransition(R.transition.transition_change_bounds)
        sharedElementReturnTransition =
            TransitionInflater.from(activity).inflateTransition(R.transition.transition_change_bounds)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView(){
        layoutAlbum.visibility = View.GONE
        rclExplore.apply {
            layoutManager = GridLayoutManager(rclExplore.getContext(), 1, GridLayoutManager.HORIZONTAL, false)
            adapter = exploreActivitiesAdapter

        }
        rclInspire.apply {
            layoutManager = GridLayoutManager(rclInspire.getContext(), 1, GridLayoutManager.HORIZONTAL, false)
            adapter = inspireYourselfAdapter

        }
        rclFindNearby.apply {
            setHasFixedSize(false)
            layoutManager = GridLayoutManager(rclFindNearby.getContext(), 1, GridLayoutManager.HORIZONTAL, false)
            adapter = findNearbyAdapter
        }
        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
        var checkFindShow = 0
        scrLayoutAlbum.getViewTreeObserver()
            .addOnScrollChangedListener(OnScrollChangedListener {
                if (scrLayoutAlbum != null) {
                    val scrollBounds = Rect()
                    scrLayoutAlbum.getHitRect(scrollBounds)
                    if (rclFindNearby.getLocalVisibleRect(scrollBounds) && checkFindShow == 0) {
                        val fadeIn: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_show_recyclerfind)
                        rclFindNearby.startAnimation(fadeIn)
                        checkFindShow++
                    }

                }

            })
    }


}
