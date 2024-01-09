package com.example.gafitouser.notification

import com.example.gafitouser.notification.Contants.Companion.CONTENT_TYPE
import com.example.gafitouser.notification.Contants.Companion.SERVER_KEY
import com.google.android.gms.common.api.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
interface NotificationAPI {

    @Headers ("Authorization: key = $SERVER_KEY", "Content-Type: $CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): retrofit2.Response<ResponseBody>

}