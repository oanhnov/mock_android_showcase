package com.igorwojda.showcase.feature.mock.presentation

import android.os.Bundle
import android.os.Handler
import android.transition.TransitionInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.igorwojda.showcase.feature.mock.R
import com.igorwojda.showcase.library.base.presentation.fragment.InjectionFragment

internal class SplashFragment : InjectionFragment(R.layout.fragment_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(R.transition.transition_change_bounds)
        sharedElementReturnTransition =
            TransitionInflater.from(activity).inflateTransition(R.transition.transition_change_bounds)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler()
        var llSplash = view.findViewById<RelativeLayout>(R.id.llSplash)
        var imgSplash = view.findViewById<ImageView>(R.id.imgSplash)
        handler.postDelayed({
            val extras = FragmentNavigatorExtras(
                llSplash to llSplash.transitionName,
                imgSplash to imgSplash.transitionName
            )
            findNavController().navigate(R.id.action_splashFragment_to_mockFragment, null, null, extras)
        }, 200)
    }
}
