package com.example.swapshop_mobile_version

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {

    private val PREFS_NAME = "SwapShop"
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor!!.commit()
    }
    //Fonction pour sauvegarder une valeur int dans mon fichier shared
    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.commit()
    }
    //Fonction pour sauvegarder une valeur booléenne dans mon fichier shared
    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status!!)
        editor.commit()
    }
    //Fonction pour lire une valeur string depuis mon fichier shared
    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }
    //Fonction pour lire une valeur int depuis mon fichier shared
    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }
    //Fonction pour lire une valeur booléenne depuis mon fichier shared
    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }
    //Fonction pour effacer les valeurs de mon fichier shared
    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor.clear()
        editor.commit()
    }
    //Fonction pour effacer une valeur de mon fichier shared à partir de sa clé
    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }

}