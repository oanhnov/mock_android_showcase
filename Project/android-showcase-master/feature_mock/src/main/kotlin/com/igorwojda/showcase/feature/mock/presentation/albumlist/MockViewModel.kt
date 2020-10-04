package com.igorwojda.showcase.feature.mock.presentation.albumlist

import androidx.lifecycle.viewModelScope
import com.igorwojda.showcase.feature.mock.domain.model.AlbumDomainModel
import com.igorwojda.showcase.feature.mock.domain.usecase.GetAlbumListUseCase
import com.igorwojda.showcase.library.base.presentation.navigation.NavManager
import com.igorwojda.showcase.library.base.presentation.viewmodel.BaseAction
import com.igorwojda.showcase.library.base.presentation.viewmodel.BaseViewModel
import com.igorwojda.showcase.library.base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.launch

internal class MockViewModel(
    private val navManager: NavManager,
    private val getAlbumListUseCase: GetAlbumListUseCase
) : BaseViewModel<MockViewModel.ViewState, MockViewModel.Action>(ViewState()) {

    override fun onLoadData() {
        getAlbumList()
    }
	
    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.MockLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            albums = viewAction.albums
        )
        is Action.MockLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            albums = listOf()
        )
    }

    private fun getAlbumList() {
        viewModelScope.launch {
            getAlbumListUseCase.execute().also { result ->
                val action = when (result) {
                    is GetAlbumListUseCase.Result.Success ->
                        if (result.data.isEmpty()) {
                            Action.MockLoadingFailure
                        } else {
                            Action.MockLoadingSuccess(result.data)
                        }

                    is GetAlbumListUseCase.Result.Error ->
                        Action.MockLoadingFailure
                }
                sendAction(action)
            }
        }
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val albums: List<AlbumDomainModel> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class MockLoadingSuccess(val albums: List<AlbumDomainModel>) : Action()
        object MockLoadingFailure : Action()
    }
}
