package com.example.compras.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.compras.controll.ListControll
import com.example.compras.databinding.ActivityNewListBinding

class NewList : AppCompatActivity() {

    private lateinit var imgView: ImageView
    private var imgURI: Uri? = null

    private val pickImageGalery = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) {
        uri: Uri? ->
        uri?.let {

            saveUriPermission(it)

            imgURI = it
            imgView.setImageURI(it)
        }
    }

    private val getPermissionGalery = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean? ->
        if (isGranted == true) {
            pickImageGalery.launch(arrayOf("image/*"))
        } else {

            Toast.makeText(this, "O acesso à galeria foi negada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgView = binding.ivImg

        val currentShopList = ListControll.getCurrentList()

        if (currentShopList != null) {
            binding.etTil.setText(currentShopList.title)
            imgView.setImageURI(currentShopList.img)
            imgURI = currentShopList.img
        }

        binding.btImg.setOnClickListener {

            checkAndOpenGalery()
        }

        binding.btAdd.setOnClickListener {

            val title = binding.etTil

            //Se o titulo foi informado
            if (title.text.toString().isEmpty()){
                title.error = "Titulo é obrigatório"
                return@setOnClickListener
            }

            try {

                if (currentShopList != null) {
                    //Editando ja existente

                    if (currentShopList.title != title.text.toString() ||
                        currentShopList.img != imgURI
                    ) {
                        //Realmente está editando algo

                        ListControll.editList(currentShopList, title.text.toString(), imgURI)
                        ListControll.setCurrentList(null)
                        finish()
                    }

                } else {
                    //Adicionando novo

                    ListControll.adicionarList(title.text.toString(), imgURI)
                    finish()
                }

            } catch (e: IllegalArgumentException) {

                if (e.message == "DUPLICADO") {
                    //Se duplicado

                    Toast.makeText(
                        this,
                        "${title.text.toString()} já existe",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener

                }
            }
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

                    pickImageGalery.launch(arrayOf("image/*"))
            }

            else -> {
                getPermissionGalery.launch(permission)
            }
        }
    }

    private fun saveUriPermission(uri: Uri) {
        val contentResolver = applicationContext.contentResolver

        val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        try {
            contentResolver.takePersistableUriPermission(uri, takeFlags)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}