package kz.home.filemanager

import kz.home.filemanager.data.FileApi
import kz.home.filemanager.data.FileContract
import kz.home.filemanager.viewmodel.GetCacheUseCase
import kz.home.filemanager.model.InjectionModule
import kz.home.filemanager.viewmodel.FileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule : InjectionModule {
    override fun create(): Module = module {
        single<FileContract> { FileApi() }
        viewModel { FileViewModel(get()) }
        single { GetCacheUseCase(get()) }
    }
}
