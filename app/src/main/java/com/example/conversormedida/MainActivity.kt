package com.example.conversormedida

import android.R.layout.simple_spinner_item
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val medidas = arrayOf("Centímetros", "Metros", "Quilômetros", "Milhas")
    private lateinit var spinnerEntrada: Spinner
    private lateinit var spinnerSaida: Spinner
    private lateinit var vlEntrada: EditText
    private lateinit var vlSaida: TextView
    private lateinit var medida_selecionada: TextView
    private lateinit var medida_saida: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initialize()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun initialize() {
        spinnerEntrada = findViewById(R.id.spinner_entrada)
        spinnerSaida = findViewById(R.id.spinner_saida)
        vlEntrada = findViewById(R.id.vlEntrada)
        vlSaida = findViewById(R.id.vlSaida)
        medida_selecionada = findViewById(R.id.medida_selecionada)
        medida_saida = findViewById(R.id.medida_saida)

        val arrayAdapter = ArrayAdapter(this, simple_spinner_item, medidas)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        configureSpinnerentrada(spinnerEntrada, "Converter de ")
        configureSpinnersaida(spinnerSaida, "Converter para ")

        vlEntrada.setOnKeyListener { _, _, _ ->
            convertMeasures()
            false
        }
    }

    private fun configureSpinnerentrada(spinner: Spinner, toastPrefix: String) {
        val arrayAdapter = ArrayAdapter(this, simple_spinner_item, medidas)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                medida_selecionada.text = medidas[position]
                Toast.makeText(applicationContext, toastPrefix + medidas[position], Toast.LENGTH_SHORT).show()
                convertMeasures()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun configureSpinnersaida(spinner: Spinner, toastPrefix: String) {
        val arrayAdapter = ArrayAdapter(this, simple_spinner_item, medidas)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                medida_saida.text = medidas[position]
                Toast.makeText(applicationContext, toastPrefix + medidas[position], Toast.LENGTH_SHORT).show()
                convertMeasures()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }



    private fun convertMeasures() {
        val valorEntrada = vlEntrada.text.toString().toDoubleOrNull()
        if (valorEntrada == null) {
            vlSaida.text = ""
            return
        }

        val fromUnit = medidas[spinnerEntrada.selectedItemPosition]
        val toUnit = medidas[spinnerSaida.selectedItemPosition]

        val valorSaida = when (fromUnit to toUnit) {
            "Centímetros" to "Metros" -> valorEntrada / 100
            "Centímetros" to "Quilômetros" -> valorEntrada / 100000
            "Centímetros" to "Milhas" -> valorEntrada / 160934.4
            "Metros" to "Centímetros" -> valorEntrada * 100
            "Metros" to "Quilômetros" -> valorEntrada / 1000
            "Metros" to "Milhas" -> valorEntrada / 1609.344
            "Quilômetros" to "Centímetros" -> valorEntrada * 100000
            "Quilômetros" to "Metros" -> valorEntrada * 1000
            "Quilômetros" to "Milhas" -> valorEntrada / 1.609344
            "Milhas" to "Centímetros" -> valorEntrada * 160934.4
            "Milhas" to "Metros" -> valorEntrada * 1609.344
            "Milhas" to "Quilômetros" -> valorEntrada * 1.609344
            else -> valorEntrada
        }

        vlSaida.text = valorSaida.toString()
    }
}


