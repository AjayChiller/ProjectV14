package com.technofreak.projectv14

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technofreak.projectv14.adapter.ListGalleryAdapter
import com.technofreak.projectv14.adapter.SpaceItemDecoration
import com.technofreak.projectv14.model.GalleryPicture
import com.technofreak.projectv14.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Baseclass() {

    private lateinit var adapter: ListGalleryAdapter
    private lateinit var layoutManager :LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestReadStoragePermission()
        init()
    }



   public fun init() {

        layoutManager = LinearLayoutManager(this)
        galleryViewModel = ViewModelProviders.of(this)[GalleryViewModel::class.java]
        rv.layoutManager = layoutManager
        rv.addItemDecoration(SpaceItemDecoration(8))
        pictures = ArrayList(galleryViewModel.getGallerySize(this))
        adapter = ListGalleryAdapter(pictures)
        rv.adapter = adapter
        adapter.setOnClickListener { galleryPicture ->
            showToast(galleryPicture.path)
        }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == pictures.lastIndex) {
                    loadPictures(25,adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
                }
            }
        })

        loadPictures(25,adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)


    }











}
