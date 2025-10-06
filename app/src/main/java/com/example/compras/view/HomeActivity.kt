package com.example.compras.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.compras.adapter.ListAdapter
import com.example.compras.controll.ListControll
import com.example.compras.controll.UserControll
import com.example.compras.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val adapter = ListAdapter(ListControll.getListFiltered(), { shopList ->

        ListControll.setCurrentList(shopList)

        val intent = Intent(this, ListDetail::class.java)
        intent.putExtra("titleList", shopList.title)
        startActivity(intent)
    })

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvLists.adapter = adapter
        binding.rvLists.layoutManager = LinearLayoutManager(this)

        binding.svSearch.setOnClickListener {

            val search = binding.etSearch

            if (search.text.toString().isEmpty()){
                ListControll.updateList()
                return@setOnClickListener
            }

            ListControll.filter(search.text.toString())
            adapter.notifyDataSetChanged()
        }

        binding.addList.setOnClickListener {

            val intent = Intent(this, NewList::class.java)
            startActivity(intent)
        }

        binding.ibLogout.setOnClickListener {

            UserControll.logoff()
            finish()
        }
    }

    override fun onResume(){
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}