package com.dicoding.picodiploma.mohammadeko

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.mohammadeko.adapter.ViewPagerDetailAdapter
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.AVATAR
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.COMPANY
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.FAVORITE
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.FOLLOWERS
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.FOLLOWING
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.LOCATION
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.NAME
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.REPOSITORY
import com.dicoding.picodiploma.mohammadeko.db.DatabaseMohEko.FavColumns.Companion.USERNAME
import com.dicoding.picodiploma.mohammadeko.db.FavoriteHelper
import com.dicoding.picodiploma.mohammadeko.model.DataUsers
import com.dicoding.picodiploma.mohammadeko.model.Favorite
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.username
import kotlinx.android.synthetic.main.app_bar_favorite.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAVORITE = "extra_data"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }

    private var isFavorite = false
    private lateinit var gitHelper: FavoriteHelper
    private var favorites: Favorite? = null
    private lateinit var imageAvatar: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        gitHelper = FavoriteHelper.getInstance(applicationContext)
        gitHelper.open()

        favorites = intent.getParcelableExtra(EXTRA_NOTE)
        if (favorites != null) {
            setDataObject()
            isFavorite = true
            val checked: Int = R.drawable.ic_favorite_white_24dp
            btn_fav.setImageResource(checked)
        } else {
            setData()
        }

        viewPagerConfig()
        btn_fav.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        menu.removeItem(R.id.favoriteMenu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingMenu -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun viewPagerConfig() {
        val viewPagerDetailAdapter = ViewPagerDetailAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerDetailAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        val dataUser = intent!!.getParcelableExtra<DataUsers>(EXTRA_DATA) as DataUsers
        setActionBarTitle("Detail Users")
        name.text = dataUser.name.toString()
        username.text = dataUser.username.toString()
        company.text = dataUser.company.toString()
        location.text = dataUser.location.toString()
        repo.text = dataUser.repository.toString()
        followerss.text = dataUser.followers.toString()
        followings.text = dataUser.following.toString()
        Glide.with(this)
            .load(dataUser.avatar.toString())
            .into(avatars)
        imageAvatar = dataUser.avatar.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setDataObject() {
        val favUser = intent!!.getParcelableExtra<Favorite>(EXTRA_NOTE) as Favorite
        setActionBarTitle("Detail Users")
        name.text = favUser.name.toString()
        username.text = favUser.username.toString()
        company.text = favUser.company.toString()
        location.text = favUser.location.toString()
        repo.text = favUser.repository.toString()
        followerss.text = favUser.followers.toString()
        followings.text = favUser.following.toString()
        Glide.with(this)
            .load(favUser.avatar.toString())
            .into(avatars)
        imageAvatar = favUser.avatar.toString()
    }

    override fun onClick(view: View) {
        val checked: Int = R.drawable.ic_favorite_white_24dp
        val unChecked: Int = R.drawable.ic_favorite_border_white_24dp
        if (view.id == R.id.btn_fav) {
            if (isFavorite) {
                gitHelper.deleteById(favorites?.username.toString())
                Toast.makeText(this, "Deleted from favorite list", Toast.LENGTH_SHORT).show()
                btn_fav.setImageResource(unChecked)
                isFavorite = false
            } else {
                val dataUsername = username.text.toString()
                val dataName = name.text.toString()
                val dataAvatar = imageAvatar
                val datacompany = company.text.toString()
                val dataLocation = location.text.toString()
                val dataRepository = repo.text.toString()
                val dataFollowers = followerss.text.toString()
                val dataFollowing = followings.text.toString()
                val dataFavourite = "1"

                val values = ContentValues()
                values.put(USERNAME, dataUsername)
                values.put(NAME, dataName)
                values.put(AVATAR, dataAvatar)
                values.put(COMPANY, datacompany)
                values.put(LOCATION, dataLocation)
                values.put(REPOSITORY, dataRepository)
                values.put(FOLLOWERS, dataFollowers)
                values.put(FOLLOWING, dataFollowing)
                values.put(FAVORITE, dataFavourite)

                isFavorite = true
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, "Added to favorite list", Toast.LENGTH_SHORT).show()
                btn_fav.setImageResource(checked)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gitHelper.close()
    }
}
