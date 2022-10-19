package kz.home.filemanager.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kz.home.filemanager.common.base.UseCase
import kz.home.filemanager.data.FileContract
import kz.home.filemanager.model.Messenger
import java.io.File

class GetCacheUseCase(
    private val fileContract: FileContract
) : UseCase<GetCacheUseCase.Params, List<File>>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Params) =
        fileContract.getFilesInCache(parameters.messenger)

    data class Params(val messenger: Messenger)
}
