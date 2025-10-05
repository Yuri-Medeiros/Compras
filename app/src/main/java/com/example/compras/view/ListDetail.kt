package com.example.compras.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.compras.adapter.ItemAdapter
import com.example.compras.controll.ItemControll
import com.example.compras.controll.ListControll
import com.example.compras.databinding.ActivityListDetailBinding
import com.example.compras.model.Item

class ListDetail : AppCompatActivity() {

    private val adapter = ItemAdapter(ItemControll.getItems(), {
        item: Item? -> {

            if (item != null) {

                ItemControll.setCurrentItem(item)
                val intent = Intent(this, NewItem::class.java)
                startActivity(intent)
            }
    }})

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvLists.adapter = adapter
        binding.rvLists.layoutManager = LinearLayoutManager(this)

        val list = ListControll.getCurrentList()
        if (list != null) {
            binding.tvTitle.text = list.title
        }

        binding.add.setOnClickListener {

            val intent = Intent(this, NewItem::class.java)
            startActivity(intent)
        }

        binding.btEdit.setOnClickListener {

            val intent = Intent(this, NewList::class.java)
            startActivity(intent)
            finish()
        }

        binding.btDelete.setOnClickListener {

            val list = ListControll.getCurrentList()

            if (list != null){

                ListControll.deleteList(list)
                ListControll.setCurrentList(null)
                finish()
            }
        }
    }

    override fun onResume(){
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}