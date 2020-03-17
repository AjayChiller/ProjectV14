package com.technofreak.projectv14

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technofreak.projectv14.adapter.GalleryAdapter
import com.technofreak.projectv14.adapter.SpaceItemDecoration
import com.technofreak.projectv14.model.GalleryPicture
import com.technofreak.projectv14.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class GridActivity : Baseclass() {

    private lateinit var adapter: GalleryAdapter

    private lateinit var layoutManager :GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {
        showToast("Grid")
        showToast("Grid")
        layoutManager = GridLayoutManager(this,3)
        galleryViewModel = ViewModelProviders.of(this)[GalleryViewModel::class.java]
        rv.layoutManager = layoutManager
        rv.addItemDecoration(SpaceItemDecoration(8))
        pictures = ArrayList(galleryViewModel.getGallerySize(this))
        adapter = GalleryAdapter(pictures)
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




