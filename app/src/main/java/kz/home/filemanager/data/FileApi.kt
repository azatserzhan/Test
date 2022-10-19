package kz.home.filemanager.data

import android.os.Environment
import kz.home.filemanager.common.utils.getFilesList
import kz.home.filemanager.model.Messenger
import java.io.File


class FileApi : FileContract {

    override suspend fun getFilesInCache(messenger: Messenger): List<File> {
        val dir = messenger.dirs.map {
            File("${Environment.getExternalStorageDirectory().path}/${it}")
        }
        val files = mutableListOf<File>()
        dir.forEach {
            it.walkTopDown().forEach { file ->
                if (file.isFile) {
                    files.add(file)
                }
            }
        }
        return files
    }
}