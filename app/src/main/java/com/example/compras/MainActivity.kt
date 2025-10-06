package com.example.compras

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.compras.databinding.ActivityMainBinding
import com.example.compras.controll.UserControll
import com.example.compras.view.CadastroActivity
import com.example.compras.view.HomeActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.login.setOnClickListener {

            val mail = binding.etMail
            val pss = binding.etPsw

            if (!isValidEmail(mail.text.toString().trim())){
                mail.error = "Informe um email válido"
                return@setOnClickListener
            }

            if (pss.text.toString().trim().isEmpty()){
                pss.error = "Informe uma senha"
                return@setOnClickListener
            }

            if (UserControll.login(mail.text.toString(), pss.text.toString())) {

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            } else {

                Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.cadastre.setOnClickListener {

            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etMail.text.clear()
        binding.etPsw.text.clear()
        Log.d("ONRESUME", "LIMPOU")
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}