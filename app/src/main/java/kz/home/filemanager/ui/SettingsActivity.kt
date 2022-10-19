package kz.home.filemanager.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kz.home.filemanager.*
import kz.home.filemanager.common.utils.*
import kz.home.filemanager.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getInfoList())
        binding.infoList.adapter = adapter

        binding.openSettingsButton.setOnClickListener {
            openPermissionSettings(this)
        }
        binding.requestPermissionButton.setOnClickListener {
            requestStoragePermission(this)
        }
    }

    private fun getInfoList(): List<String> {
        return listOf(
            getString(R.string.sdk_codename_info, Build.VERSION.CODENAME),
            getString(R.string.sdk_version_info, Build.VERSION.SDK_INT.toString()),
            getString(R.string.legacy_storage_info, getLegacyStorageStatus()),
            getString(R.string.permission_used_info, getStoragePermissionName()),
            getString(R.string.permission_granted_info, getPermissionStatus(this))
        )
    }
}