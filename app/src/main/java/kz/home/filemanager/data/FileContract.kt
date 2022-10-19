package kz.home.filemanager.data

import kz.home.filemanager.model.Messenger
import java.io.File

interface FileContract {
    suspend fun getFilesInCache(messenger: Messenger): List<File>
}