package com.dicoding.picodiploma.mohammadeko.widget

import android.content.Context
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.dicoding.picodiploma.mohammadeko.R
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.mohammadeko.model.DataUsers

internal class UserWidgetRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var list: List<DataUsers> = listOf()
    private var cursor: Cursor? = null

    override fun onCreate() {}

    override fun onDataSetChanged() {

        cursor?.close()

        val identityToken = Binder.clearCallingIdentity()

        cursor = mContext.contentResolver?.query(CONTENT_URI, null, null, null, null)
        cursor?.let {
            list = listOf(DataUsers())
        }

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {
        cursor?.close()
        list = listOf()
    }

    override fun getCount(): Int = list.size

    override fun getViewAt(position: Int): RemoteViews? {

        val views = RemoteViews(mContext.packageName, R.layout.user_widget_item)

        if (!list.isNullOrEmpty()) {

            views.apply {
                list[position].apply {

                    setTextViewText(
                        R.id.userWidgetItem_tv_name, name ?: name
                    )
                    setTextViewText(
                        R.id.userWidgetItem_tv_username, if (!name.isNullOrEmpty()) name else null
                    )
                }
            }
        }

        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = true
}

