package kz.home.filemanager.model

import org.koin.core.module.Module

interface InjectionModule {
    fun create(): Module
}