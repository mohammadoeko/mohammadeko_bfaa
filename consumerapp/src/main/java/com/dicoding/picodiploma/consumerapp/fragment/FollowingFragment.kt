package com.dicoding.picodiploma.consumerapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.consumerapp.DetailActivity
import com.dicoding.picodiploma.consumerapp.R
import com.dicoding.picodiploma.consumerapp.adapter.ListDataFollowingAdapter
import com.dicoding.picodiploma.consumerapp.adapter.followingFilterList
import com.dicoding.picodiploma.consumerapp.model.DataUsers
import com.dicoding.picodiploma.consumerapp.model.Favorite
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray
import org.json.JSONObject

class FollowingFragment : Fragment() {

    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
    }

    private var listData: ArrayList<DataUsers> = ArrayList()
    private lateinit var adapter: ListDataFollowingAdapter
    private var favorites: Favorite? = null
    private lateinit var dataUser: Favorite
    private lateinit var dataUser2: DataUsers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListDataFollowingAdapter(listData)
        listData.clear()

        favorites = activity!!.intent.getParcelableExtra(DetailActivity.EXTRA_NOTE)
        if (favorites != null) {
            dataUser =
                activity!!.intent.getParcelableExtra<Favorite>(FollowersFragment.EXTRA_NOTE) as Favorite
            getDataGit(dataUser.username.toString())
        } else {
            dataUser2 =
                activity!!.intent.getParcelableExtra<DataUsers>(FollowersFragment.EXTRA_DATA) as DataUsers
            getDataGit(dataUser2.username.toString())
        }
    }

    private fun getDataGit(id: String) {
        progressBarFollowing.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 6fe9dff2e5e43d25eb3abe9ff508a750b972f725")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBarFollowing.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDataGitDetail(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBarFollowing.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun getDataGitDetail(id: String) {
        progressBarFollowing.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 6fe9dff2e5e43d25eb3abe9ff508a750b972f725")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBarFollowing.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String? = jsonObject.getString("login").toString()
                    val name: String? = jsonObject.getString("name").toString()
                    val avatar: String? = jsonObject.getString("avatar_url").toString()
                    val company: String? = jsonObject.getString("company").toString()
                    val location: String? = jsonObject.getString("location").toString()
                    val repository: Int = jsonObject.getInt("public_repos")
                    val followers: Int = jsonObject.getInt("followers")
                    val following: Int = jsonObject.getInt("following")
                    listData.add(
                        DataUsers(
                            username,
                            name,
                            avatar,
                            company,
                            location,
                            repository,
                            followers,
                            following
                        )
                    )
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBarFollowing.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun showRecyclerList() {
        recycleViewFollowing.layoutManager = LinearLayoutManager(activity)
        val listDataAdapter =
            ListDataFollowingAdapter(followingFilterList)
        recycleViewFollowing.adapter = adapter

        listDataAdapter.setOnItemClickCallback(object :
            ListDataFollowingAdapter.OnItemClickCallback {
            override fun onItemClicked(DataUsers: DataUsers) {
                //DO NOTHING
            }
        })
    }
}
