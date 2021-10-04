package com.starter.permissions

import com.starter.ui.base.BaseActivity
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
Created by Mohammad Zaki
on Oct,03 2021
 **/
abstract class BasePermissionActivity : BaseActivity(), BasePermission,
    EasyPermissions.PermissionCallbacks {

    override fun hasPermissions(perms: Array<String>): Boolean {
        return EasyPermissions.hasPermissions(this, *perms)
    }

    override fun requestPermission(
        requestCode: Int,
        rationalMessage: String,
        perms: Array<String>
    ) {
        if (hasPermissions(perms)) {
            onPermissionGranted(requestCode, perms.asList())
        } else { // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, rationalMessage, requestCode, *perms)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this@BasePermissionActivity
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        onPermissionGranted(requestCode, perms)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
            onPermissionPermanentlyDenied(requestCode, perms)
        } else {
            onPermissionDenied(requestCode, perms)
        }
    }

    /**
     * Method called when user have granted the permission
     *
     * @param requestCode the request code for permission
     * @param perms       list of permissions granted
     */
    abstract fun onPermissionDenied(requestCode: Int, perms: List<String?>?)

    /**
     * Method called when user denied permission
     *
     * @param requestCode the request code for permission
     * @param perms       list of permissions denied
     */
    abstract fun onPermissionGranted(requestCode: Int, perms: List<String?>?)

    /**
     * Mehod called when some permissions are permanentaly denied
     *
     * @param requestCode the request code for permission
     * @param perms       list of permissions denied
     */
    abstract fun onPermissionPermanentlyDenied(requestCode: Int, perms: List<String?>)
}