package com.example.gafitouser

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Handle incoming FCM message here
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}" )

            // Handle data payload here
            // Jika laporan baru diterima, tampilkan notifikasi
            if (remoteMessage.data["type"] == "new_report") {
                // Panggil fungsi untuk menampilkan notifikasi
                showNotification(
                    remoteMessage.data["laporanId"],
                    remoteMessage.data["laporanTitle"]
                )
            }
        }

        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        // Handle new or refreshed FCM token
        Log.d(TAG, "Refreshed token: $token")
        // Kirimkan token ke server Anda jika diperlukan
    }

    private fun showNotification(laporanId: String?, laporanTitle: String?) {
        // Implementasi notifikasi di sini
        // Anda dapat menggunakan NotificationManagerCompat untuk menampilkan notifikasi
    }

    companion object {
        private const val TAG = "MyFirebaseMessagingService"
    }
}
