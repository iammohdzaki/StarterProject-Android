package com.starter.permissions

/**
Created by Mohammad Zaki
on Oct,03 2021
 **/
interface BasePermission {
    /**
     * Method to check that activity has required permissions or not
     *
     * @param perms the array of permissions to check
     * @return true if activity has permissions
     */
    fun hasPermissions(perms: Array<String>): Boolean

    /**
     * Method to request the permission . it first check permisson is granted or not , if not then ask user for permission.
     *
     * @param requestCode     the permission request code
     * @param rationalMessage message to show the user why we required permission
     * @param perms           the array of permissions
     */
    fun requestPermission(requestCode: Int,
                          rationalMessage: String,perms: Array<String>)

}