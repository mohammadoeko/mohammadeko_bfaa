package com.dicoding.picodiploma.mohammadeko.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViewsService
import android.widget.RemoteViewsService.RemoteViewsFactory

class UserWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        UserWidgetRemoteViewsFactory(this.applicationContext)
}