package com.payal.moengage_fetch_articles.viewmodel

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.payal.moengage_fetch_articles.dto.NotificationBody
import com.payal.moengage_fetch_articles.dto.SendMessageDto
import com.payal.moengage_fetch_articles.model.ChatState
import com.payal.moengage_fetch_articles.service.FCMApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class ChatViewModel @Inject constructor(val fcmService: FCMApiService) : ViewModel() {

    var state by mutableStateOf(ChatState())
        private set

    init {
        viewModelScope.launch {
            FirebaseMessaging.getInstance().subscribeToTopic("articles").await()
        }
    }

    private fun onRemoteTokenChange(token: String) {
        state = state.copy(
            remoteToken = token
        )
    }

    //get user device token to send notification on particular device
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            onRemoteTokenChange(task.result)
        })
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun sendMessage(isBroadcast: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            getToken()
            val message = SendMessageDto(
                //Broadcast will check send notification to particular user or all users
                to = if (isBroadcast) null else state.remoteToken,
                notificationBody = NotificationBody(
                    title = "New Message", // we can pass any message
                    body = "Hey, you got something new !" // Add any content
                )
            )

            try {
                if (isBroadcast) {
                    fcmService.broadcast(message)
                } else {
                    fcmService.sendMessage(message)
                }
            } catch (e: Exception) {
                Log.d(TAG,"Something went wrong : ${e.message}")
            }
        }
    }
}