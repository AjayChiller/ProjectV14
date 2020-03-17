package com.technofreak.projectv14

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technofreak.projectv14.adapter.GalleryAdapter
import com.technofreak.projectv14.adapter.SpaceItemDecoration
import com.technofreak.projectv14.adapter.test
import com.technofreak.projectv14.model.GalleryPicture
import com.technofreak.projectv14.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: test
    private lateinit var adapter2: test
    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var pictures: ArrayList<GalleryPicture>
    private lateinit var layoutManager :LinearLayoutManager
    private lateinit var layoutManager2 :GridLayoutManager





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestReadStoragePermission()

        Log.e("DEBUGme","0111")
    }

    private fun requestReadStoragePermission() {
        val readStorage = Manifest.permission.READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                this,
                readStorage
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(readStorage), 3)
        } else init()
    }

    private fun init() {

        layoutManager = LinearLayoutManager(this)

        galleryViewModel = ViewModelProviders.of(this)[GalleryViewModel::class.java]
        rv.layoutManager = layoutManager
        rv.addItemDecoration(SpaceItemDecoration(8))
        pictures = ArrayList(galleryViewModel.getGallerySize(this))
        adapter = test(pictures)
        rv.adapter = adapter
        adapter.setOnClickListener { galleryPicture ->
            showToast(galleryPicture.path)
        }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == pictures.lastIndex) {
                    loadPictures(25)
                }
            }
        })

        loadPictures(25)

    }




    private fun loadPictures(pageSize: Int) {
        galleryViewModel.getImagesFromGallery(this, pageSize) {
            if (it.isNotEmpty()) {
                pictures.addAll(it)
                adapter.notifyItemRangeInserted(pictures.size, it.size)
            }
            Log.i("GalleryListSize", "${pictures.size}")

        }

    }//End of load pictures



    private fun showToast(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            init()
        else {
            showToast("Permission Required to Fetch Gallery.")
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.change_layout -> {
                if ( item.title == "grid") {

                    item.title = "list"
                } else {

                    item.title = "grid"
                }

               // adapter?.notifyItemRangeChanged(0, adapter?.itemCount ?: 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
