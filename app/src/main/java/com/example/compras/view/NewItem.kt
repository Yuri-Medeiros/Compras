package com.example.compras.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.compras.controll.ItemControll
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

        val itemEdit = ItemControll.getCurrentItem()

        if (itemEdit != null) {
            name.setText(itemEdit.name)
            categoria.setText(itemEdit.categoria)
            qnt.setText(itemEdit.quantidade.toString())
            uni.setText(itemEdit.unidade)
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

            if (name.text.toString() == ItemControll.getCurrentItem()?.name &&
                categoria.text.toString() == ItemControll.getCurrentItem()?.categoria &&
                qnt.text.toString().toInt() == ItemControll.getCurrentItem()?.quantidade &&
                uni.text.toString() == ItemControll.getCurrentItem()?.unidade) {
                return@setOnClickListener
            }

            try {

                if (ItemControll.getCurrentItem() == null) {

                    ItemControll.adicionarItem(
                        name.text.toString(),
                        categoria.text.toString(),
                        qnt.text.toString().toInt(),
                        uni.text.toString()
                    )

                } else {

                    ItemControll.editItem(
                        name.text.toString(),
                        categoria.text.toString(),
                        qnt.text.toString().toInt(),
                        uni.text.toString()
                    )
                    ItemControll.setCurrentItem(null)
                }
                finish()

            } catch (e: IllegalArgumentException) {

                if (e.message == "DUPLICADO") {

                    Toast.makeText(this, "${name.text.toString()} já existe", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.btDlt.setOnClickListener {

            if (ItemControll.getCurrentItem() != null) {

                ItemControll.deleteItem()
                finish()
            } else {
                Toast.makeText(this, "Item ainda não adicionado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ItemControll.setCurrentItem(null)
    }
}