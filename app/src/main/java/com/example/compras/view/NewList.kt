package com.example.compras.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.compras.R
import com.example.compras.controll.ListControll
import com.example.compras.databinding.ActivityNewListBinding

class NewList : AppCompatActivity() {

    private lateinit var imgView: ImageView
    private var imgURI: Uri? = null

    private val pickImageGalery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        uri: Uri? ->
        uri?.let {
            imgURI = it
            imgView.setImageURI(it)
        }
    }

    private val getPermissionGalery = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean? ->
        if (isGranted == true) {
            pickImageGalery.launch(".image/*")
        } else {

            Toast.makeText(this, "O acesso à galeria foi negada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgView = binding.ivImg

        binding.btImg.setOnClickListener {

            Toast.makeText(this, Manifest.permission.READ_EXTERNAL_STORAGE.toString(), Toast.LENGTH_SHORT).show()
            checkAndOpenGalery()
        }

        binding.btAdd.setOnClickListener {

            val title = binding.etTil

            if (title.text.toString().isEmpty()){
                title.error = "Titulo é obrigatório"
                return@setOnClickListener
            }

            ListControll.adicionarList(title.text.toString(), imgURI)
            Log.d("SALVOU A LISTA?", ListControll.getListFiltered().toString())
            finish()
        }
    }

    fun checkAndOpenGalery(){

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED -> {

                    pickImageGalery.launch(".image/*")
            }

            else -> {
                getPermissionGalery.launch(permission)
            }
        }
    }
}