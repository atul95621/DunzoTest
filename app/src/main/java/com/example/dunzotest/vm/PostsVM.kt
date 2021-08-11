package com.example.shipsytest.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shipsytest.model.PostModel

import com.todayq.official.service.RetrofitBuilder
import com.todayq.official.util.Constants
import com.todayq.official.util.Resource
import kotlinx.coroutines.launch

class PostsVM() : ViewModel() {

    var postLivedata: MutableLiveData<Resource<PostModel>> = MutableLiveData()

    fun getPostData(
        tags: String, page: String
    ) {
        viewModelScope.launch {
            postLivedata.value = Resource.loading(data = null)
            try {
                var api = RetrofitBuilder.apiService
                postLivedata.value = Resource.success(
                    data = api.getPosts(
                        Constants.method,
                        Constants.api_key,
                        tags,
                        Constants.format,
                        Constants.nojsoncallback,
                        Constants.per_page,
                        page
                    )
                )


            } catch (exception: Exception) {
                postLivedata.value =
                    Resource.error(data = null, message = exception.message ?: "Error Occurred!")
            }

        }
    }


}