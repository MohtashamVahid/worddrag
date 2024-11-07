package com.vahidmohtasham.worddrag

import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstanceIDService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }
}
