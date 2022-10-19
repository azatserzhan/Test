package kz.home.filemanager.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kz.home.filemanager.*
import kz.home.filemanager.common.utils.*
import kz.home.filemanager.databinding.ActivityFileExplorerBinding
import kz.home.filemanager.viewmodel.FileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

const val MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1
const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 2

class FileExplorerActivity : AppCompatActivity() {
    private var hasPermission = false
    private lateinit var binding: ActivityFileExplorerBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val viewModel: FileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFileExplorerBinding.inflate(layoutInflater)
        binding.toolbar.inflateMenu(R.menu.file_manager_menu)
        setContentView(binding.root)

        setupUi()
        viewModel.files.observeNotNull(this) { files ->
            adapter.clear()
            adapter.addAll(files.map {
                renderItem(this, it)
            })

            adapter.notifyDataSetChanged()
        }
        viewModel.actions.observeNotNull(this) {
            when (it) {
                is FileViewModel.Actions.Error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT)
                    .show()
                is FileViewModel.Actions.Loading -> {
                    binding.contentLoadingProgressBar.isVisible = it.isActive
                }
                is FileViewModel.Actions.Success -> {
                    Toast.makeText(
                        this,
                        "Cache files: ${viewModel.files.value?.size}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        hasPermission = checkStoragePermission(this)
        if (hasPermission) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                if (!Environment.isExternalStorageLegacy()) {
                    binding.rationaleView.visibility = View.GONE
                    binding.legacyStorageView.visibility = View.VISIBLE
                    return
                }
            }

            binding.rationaleView.visibility = View.GONE
            binding.filesTreeView.visibility = View.VISIBLE

            viewModel.load()
        } else {
            binding.rationaleView.visibility = View.VISIBLE
            binding.filesTreeView.visibility = View.GONE
        }
    }

    private fun setupUi() {
        binding.toolbar.setOnMenuItemClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }

        binding.permissionButton.setOnClickListener {
            requestStoragePermission(this)
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<String>())
        binding.filesTreeView.adapter = adapter
        binding.filesTreeView.setOnItemClickListener { _, _, position, _ ->
            viewModel.files.value?.let {
                if (it.size > position) {
                    openFile(this, it[position])
                }
            }
        }
    }

    private fun getTelegramCache() {
        val p = this.packageManager.getApplicationInfo(
            "org.telegram.messenger",
            PackageManager.GET_META_DATA
        )
        val cache = File("${p.dataDir}/cache")
        cache.listFiles()

        val sd = getExternalStorageDirectory().absolutePath
        val cacheDir = File(String.format("%s/Android/data/%s/cache", sd, "org.telegram.messenger"))
        cacheDir.listFiles()
    }
}
