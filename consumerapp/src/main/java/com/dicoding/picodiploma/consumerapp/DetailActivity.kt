package com.dicoding.picodiploma.consumerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.consumerapp.adapter.ViewPagerDetailAdapter
import com.dicoding.picodiploma.consumerapp.model.Favorite
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.username


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var imageAvatar: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setDataObject()
        viewPagerConfig()
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
    private fun setDataObject() {
        val favUser = intent.getParcelableExtra<Favorite>(EXTRA_NOTE) as Favorite
        setActionBarTitle("Detail " + favUser.name.toString())
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

}
