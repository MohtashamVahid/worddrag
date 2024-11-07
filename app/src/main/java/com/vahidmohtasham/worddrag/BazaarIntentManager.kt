package com.vahidmohtasham.worddrag

import android.content.Context
import android.content.Intent
import android.net.Uri

class BazaarIntentManager(private val context: Context) {

    private val BAZAAR_PACKAGE_NAME = "com.farsitel.bazaar"

    /**
     * ارجاع به برنامه مشخص
     * @param packageName نام بسته برنامه مورد نظر
     */
    fun openAppDetails(packageName: String) {

        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("bazaar://details?id=$packageName")
                setPackage(BAZAAR_PACKAGE_NAME)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://cafebazaar.ir/app/$packageName"))
            context.startActivity(webIntent)
        }
    }

    /**
     * ارجاع به صفحه نظرات برنامه مشخص
     * @param packageName نام بسته برنامه مورد نظر
     */
    fun openAppReviews(packageName: String) {
        try {
            val intent = Intent(Intent.ACTION_EDIT).apply {
                data = Uri.parse("bazaar://details?id=$packageName")
                setPackage(BAZAAR_PACKAGE_NAME)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://cafebazaar.ir/developer/850698502335"))
            context.startActivity(webIntent)
        }
    }

    /**
     * ارجاع به صفحه تمامی برنامه‌های توسعه‌دهنده
     * @param developerId شناسه توسعه‌دهنده
     */
    fun openDeveloperPage(developerId: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("bazaar://collection?slug=by_author&aid=$developerId")
                setPackage(BAZAAR_PACKAGE_NAME)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://cafebazaar.ir/developer/850698502335"))
            context.startActivity(webIntent)
        }
    }

    /**
     * ارجاع به صفحه ورود کاربر
     */
    fun openLoginPage() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("bazaar://login")
            setPackage(BAZAAR_PACKAGE_NAME)
        }
        context.startActivity(intent)
    }

    /**
     * استارت کردن اینتنت
     * @param intent اینتنت مورد نظر برای شروع
     */
    private fun startActivity(intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

//    fun showInstallBazaarOrUpdate(){
//        if (!BazaarClientProxy.isBazaarInstalledOnDevice(context)) {
//            BazaarClientProxy.showInstallBazaarView(context)
//
//        } else if (BazaarClientProxy.isNeededToUpdateBazaar(context).needToUpdateForAuth) {
//            BazaarClientProxy.showUpdateBazaarView(context)
//        } else {
//            BazaarClientProxy.showInstallBazaarView(context)
//        }
//    }
}
