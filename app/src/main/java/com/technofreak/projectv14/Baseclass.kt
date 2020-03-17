package com.technofreak.projectv14

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.technofreak.projectv14.model.GalleryPicture
import com.technofreak.projectv14.viewmodel.GalleryViewModel

open class Baseclass : AppCompatActivity()
{

    lateinit var galleryViewModel: GalleryViewModel
    lateinit var pictures: ArrayList<GalleryPicture>
    companion object {
        var flag: Int = 0

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Toast.makeText(this,"TITLEEE"+ item!!.title,Toast.LENGTH_LONG).show()
        if(flag==0)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            item.title = "list"
            flag=1

                // adapter?.notifyItemRangeChanged(0, adapter?.itemCount ?: 0)
            }
        else
        {
            val intent = Intent(this, GridActivity::class.java)
            startActivity(intent)
            item.title = "grid"
            flag=0
        }
        return super.onOptionsItemSelected(item)
    }


     fun loadPictures(pageSize: Int,adapter: RecyclerView.Adapter<ViewHolder>) {
        galleryViewModel.getImagesFromGallery(this, pageSize) {
            if (it.isNotEmpty()) {
                pictures.addAll(it)
                adapter.notifyItemRangeInserted(pictures.size, it.size)
            }
            Log.i("GalleryListSize", "${pictures.size}")

        }

    }//End of load pictures

    fun showToast(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()


    fun requestReadStoragePermission() {
        val readStorage = Manifest.permission.READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                this,
                readStorage
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(readStorage), 3)
        } //else init()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        //init()
        else {
            showToast("Permission Required to Fetch Gallery.")

            finish()
            System.exit(0)
        }
    }


}