package kz.home.filemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.home.filemanager.common.base.BaseViewModel
import kz.home.filemanager.common.utils.launchSafe
import kz.home.filemanager.data.GetCacheUseCase
import kz.home.filemanager.model.Messenger
import timber.log.Timber
import java.io.File

class FileViewModel(
    private val getTelegramCacheUseCase: GetCacheUseCase
) : BaseViewModel() {

    private val _files = MutableLiveData<List<File>>()
    val files: LiveData<List<File>> = _files

    private val _actions = MutableLiveData<Actions>()
    val actions: LiveData<Actions> = _actions

    fun load() {
        launchSafe(
            start = {
                _actions.postValue(Actions.Loading(true))
            },
            body = {
                _files.postValue(getTelegramCacheUseCase(GetCacheUseCase.Params(Messenger.TELEGRAM)))
                _actions.postValue(Actions.Success)
                _actions.postValue(Actions.Loading(false))
            },
            handleError = {
                Timber.e(it, "Error to load")
                _actions.postValue(Actions.Error)
                _actions.postValue(Actions.Loading(false))
            }
        )
    }

    sealed class Actions {
        data class Loading(val isActive: Boolean) : Actions()
        object Success : Actions()
        object Error : Actions()
    }
}