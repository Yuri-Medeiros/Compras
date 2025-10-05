package com.example.compras.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.compras.controll.ItemControll
import com.example.compras.controll.ListControll
import com.example.compras.databinding.ActivityNewItemBinding

class
NewItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityNewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = binding.etName
        val categoria = binding.etCat
        val qnt = binding.etQnt
        val uni = binding.etUn

        if (intent.getStringExtra("nomeItem") != null) {
            name.setText(intent.getStringExtra("nomeItem"))
        } else if (intent.getStringExtra("catItem") != null) {
            categoria.setText(intent.getStringExtra("catItem"))
        } else if (intent.getStringExtra("qntItem") != null) {
            qnt.setText(intent.getStringExtra("qntItem"))
        } else if (intent.getStringExtra("unItem") != null) {
            uni.setText(intent.getStringExtra("unItem"))
        }

        binding.btAdd.setOnClickListener {

            if (name.text.toString().isEmpty()){
                name.error = "O nome do item é obrigatório"
                return@setOnClickListener
            }

            if (categoria.text.toString().isEmpty()){
                categoria.error = "A categoria do item é obrigatório"
                return@setOnClickListener
            }

            if (qnt.text.toString().isEmpty() || qnt.text.toString().toInt() < 1){
                qnt.error = "Informe uma quantidade válida"
                return@setOnClickListener
            }

            if (uni.text.toString().isEmpty()){
                uni.error = "A unidade da qnt. do item é obrigatório"
                return@setOnClickListener
            }

            ItemControll.adicionarItem(
                name.text.toString(),
                categoria.text.toString(),
                qnt.text.toString().toInt(),
                uni.text.toString()
            )

            Log.d("SERA QUE ADD", ListControll.getCurrentList()?.items.toString())
            finish()
        }
    }
}