package com.example.compras.controll

import android.util.Log
import com.example.compras.model.User

object UserControll {

    private val listUsers = mutableListOf<User>()
    var currentUser: User? = null

    fun login(mail: String, pss: String) : Boolean{

        val user = listUsers.find { it.mail == mail && it.pss == pss }
        Log.d("Login", user.toString())

        return if (user != null) {
            currentUser = user
            true
        } else {
            false
        }
    }

    fun register(name: String, mail: String, pss: String): Boolean {

        if (listUsers.any {it.mail == mail}){
            return false
        }

        val user = User(name, mail, pss)
        listUsers.add(user)
        Log.d("Teste de cadastro", listUsers.toString())

        return true
    }

    fun isLogged(): Boolean {
        return currentUser != null
    }

    fun logoff() {
        currentUser = null
    }
}