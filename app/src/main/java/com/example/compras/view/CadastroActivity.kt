package com.example.compras.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.compras.MainActivity
import com.example.compras.databinding.ActivityCadastroBinding
import com.example.compras.controll.UserControll

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.cadastre.setOnClickListener {

            val name = binding.etName
            val email = binding.etMail
            val password = binding.etPsw
            val confirmPassword = binding.etConf

            if (name.text.toString().isEmpty()) {
                name.error = "Informe seu nome"
                return@setOnClickListener
            }

            if (!isValidEmail(email.text.toString())) {
                email.error = "Informe um e-mail válido"
                return@setOnClickListener
            }

            if (password.text.toString().isEmpty()) {
                password.error = "Informe uma senha"
                return@setOnClickListener
            }

            if (confirmPassword.text.toString().isEmpty()) {
                confirmPassword.error = "Confirme sua senha"
                return@setOnClickListener
            }

            if (password.text.toString() != confirmPassword.text.toString()) {
                confirmPassword.error = "As senhas não conferem"
                return@setOnClickListener
            }

            val registred = UserControll.register(
                name.text.toString(), email.text.toString(), password.text.toString()
            )

            if (!registred) {
                Toast.makeText(this, "Esta email ja está registrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}