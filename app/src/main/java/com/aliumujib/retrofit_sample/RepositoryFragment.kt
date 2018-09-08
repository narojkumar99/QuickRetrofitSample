package com.aliumujib.retrofit_sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aliumujib.retrofit_sample.model.Repository
import com.aliumujib.retrofit_sample.retrofit.ApiClient
import com.aliumujib.retrofit_sample.retrofit.SearchResponse
import kotlinx.android.synthetic.main.fragment_repository_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RepositoryFragment : Fragment() {

    lateinit var recyclerViewAdapter: MyRepositoryRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repository_list, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = MyRepositoryRecyclerViewAdapter(mutableListOf())
        // Set the adapter
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = recyclerViewAdapter

        var hashMap = HashMap<String, Any>()
        hashMap["q"] = "android+language:java+language:kotlin"
        hashMap["sort"] = "stars"
        hashMap["order"] = "desc"

        val apiClient = ApiClient()
        val apiService = apiClient.service

        progress_bar.visibility = View.VISIBLE
        apiService.searchRepositories(hashMap).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>?) {
                progress_bar.visibility = View.GONE
                recyclerViewAdapter.addItems(response?.body()?.items!!)
            }

            override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {
                progress_bar.visibility = View.GONE
                Toast.makeText(context!!, t!!.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    companion object {

        @JvmStatic
        fun newInstance() =
                RepositoryFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
