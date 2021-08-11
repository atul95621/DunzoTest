package com.example.dunzotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dunzotest.databinding.ActivityMainBinding
import com.example.shipsytest.adapter.MainAdapter
import com.example.shipsytest.model.PhotoItem
import com.example.shipsytest.model.PostModel
import com.example.shipsytest.util.ProgressBarClass
import com.example.shipsytest.vm.PostsVM
import com.todayq.official.util.ConnectivityManager
import com.todayq.official.util.Status
import androidx.core.widget.NestedScrollView


class MainActivity : AppCompatActivity() {

    lateinit var connectionDetector: ConnectivityManager
    private var adapter: MainAdapter? = null
    lateinit var arrayList: ArrayList<PhotoItem>
    lateinit var postsVM: PostsVM

    private lateinit var binding: ActivityMainBinding

    companion object {
        var page = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        listners()

    }

    private fun listners() {


        binding.imgSearch.setOnClickListener()
        {
            if (binding.edtSearch.text.trim().isNullOrBlank() == false) {
                if (connectionDetector.isConnectingToInternet) {
                    clearList()
                    userHistoryApi(page, binding.edtSearch.text.trim().toString())
                    getHistoryDataObserver()

                    scrollListner(binding.edtSearch.text.trim().toString())

                } else {
                    showToast(getString(R.string.check_internet))
                }
            } else {
                showToast("Please write the text first.")
            }
        }
    }

    fun clearList()
    {
        arrayList.clear()
        adapter?.notifyDataSetChanged()
    }

    fun showToast(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    fun scrollListner(text: String) {
        // adding on scroll change listener method for our nested scroll view.
        binding.nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                page++
                binding.idPBLoading.visibility = View.VISIBLE
                userHistoryApi(page, binding.edtSearch.text.trim().toString())
            }
        })
    }


    private fun init() {
        connectionDetector = ConnectivityManager(this)
        arrayList = ArrayList<PhotoItem>()
        postsVM = ViewModelProvider(this).get(PostsVM::class.java)
        binding.recyclerView?.setHasFixedSize(true)
        binding.recyclerView?.setNestedScrollingEnabled(false)
        binding.recyclerView?.setLayoutManager(LinearLayoutManager(this))

        adapter = MainAdapter(arrayList, this)
        binding.recyclerView?.adapter = adapter

        binding.tvAlert.visibility = View.VISIBLE


    }

    private fun userHistoryApi(page: Int, text: String) {
        postsVM.getPostData(text, page.toString())
    }

    fun getHistoryDataObserver() {
        postsVM.postLivedata.observe(this, Observer {
            it?.let { resource ->
                ProgressBarClass.dialog?.dismiss()

                when (resource.status) {
                    Status.SUCCESS -> {

                        ProgressBarClass.dialog?.dismiss()
                        var modelObj = resource.data as PostModel
                        var arrayListCame = ArrayList<PhotoItem>()
                        arrayListCame = modelObj.photos.photo
                        if (arrayListCame.isNullOrEmpty() == false) {
                            Log.e("item22:  ", arrayListCame.size.toString())
                            addNewItem(arrayListCame)

                        } else {
                            showToast("List is empty")
                        }
                    }
                    Status.ERROR -> {
                        try {
                            ProgressBarClass.dialog?.dismiss()
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Log.e("excpesignalhis", e?.message.toString())

                        }

                    }
                    Status.LOADING -> {
                        if (page == 1)
                            ProgressBarClass.progressBarCalling(this)
                    }
                }
            }
        })
    }

    private fun addNewItem(arrayListCame: ArrayList<PhotoItem>) {
        arrayList.addAll(arrayListCame)
        adapter?.notifyDataSetChanged()
        if (arrayList.size > 0) {
            binding.tvAlert.visibility = View.GONE
        }
    }
}