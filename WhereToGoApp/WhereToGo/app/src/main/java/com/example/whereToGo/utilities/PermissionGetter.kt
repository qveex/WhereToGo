package com.example.whereToGo.utilities

import android.Manifest
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog


class PermissionGetter(private val context: AppCompatActivity): EasyPermissions.PermissionCallbacks {

    val LOCATION_PERMISSION_REQUEST_CODE = 1234

    fun hasLocationPermissions() = EasyPermissions.hasPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION)
    fun requestLocationPermission() {

        EasyPermissions.requestPermissions(
            context,
            "This application can't work without Location Permission",
            LOCATION_PERMISSION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
            )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        if (EasyPermissions.somePermissionPermanentlyDenied(context, perms.first())) {
            SettingsDialog.Builder(context).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, context)
    }

}