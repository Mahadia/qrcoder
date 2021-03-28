package com.example.qrcoder


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest
private const val CAMERA_REQUEST_CODE = 101
class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private  val settingsFragment = settingsFragment()
    
    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       replaceFragment(homeFragment)
        bottom1.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.ic_home->replaceFragment(homeFragment)
                R.id.ic_setting->replaceFragment(settingsFragment)
            }

        }
        setupPermissions()
        codeScanner()

        buttonPanel1.setOnClickListener {
            val monIntent : Intent =  Intent(this,MainActivity2::class.java)
            val information = tv_textView.text.toString()
            monIntent.putExtra("information", information)
            Ondata()
            startActivity(monIntent)

        }

    }
 private fun replaceFragment(fragment: Fragment){

    val transaction = supportFragmentManager.beginTransaction()
  //transaction.replace()
    transaction.commit()
}


  private fun codeScanner(){
      val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
      this.codeScanner = CodeScanner(this, scannerView)


      codeScanner.apply {
          camera = CodeScanner.CAMERA_BACK
          formats = CodeScanner.ALL_FORMATS

          autoFocusMode = AutoFocusMode.SAFE
          scanMode = ScanMode.SINGLE
          isAutoFocusEnabled = true
          isFlashEnabled = false


          decodeCallback = DecodeCallback {
              runOnUiThread {
                  val text = findViewById<TextView>(R.id.tv_textView)
                  text.text = it.text



              }
          }
          errorCallback = ErrorCallback {
              runOnUiThread {
                  Log.e("MAIN","ERREUR D'INITIALISATION DE LA CAMERA:${it.message}")

              }
          }
      }

      scannerView.setOnClickListener {
          codeScanner.startPreview()
      }


  }

private fun Ondata()

  {
      val inser = tv_textView.text.toString()
      tv_textView.text = inser
      val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
      val editor = sharedPreferences.edit()
      editor.apply{
          putString("STRING_KEY",inser)

      }.apply()
    //Toast.makeText(this, Toast.LENGTH_SHORT).show()
  }

  override fun onResume() {
      super.onResume()
      codeScanner.startPreview()
  }

  override fun onPause() {
      codeScanner.releaseResources()
      super.onPause()
  }

  private fun setupPermissions(){
      val permission  = ContextCompat.checkSelfPermission(this,
          android.Manifest.permission.CAMERA)
      if(permission != PackageManager.PERMISSION_GRANTED){
          makeRequest()
      }
  }

  private fun makeRequest() {
      ActivityCompat.requestPermissions(this,
          arrayOf(android.Manifest.permission.CAMERA),
          CAMERA_REQUEST_CODE)
  }

  fun OnRequestPermissionsResult(requestCode :Int,grantResult: IntArray ){
      when(requestCode){
          CAMERA_REQUEST_CODE ->{
              if(grantResult.isEmpty() || grantResult[0] != PackageManager.PERMISSION_GRANTED){
                  Toast.makeText(this,"Nous avons besoin de votre camera pour lancer cette application", Toast.LENGTH_SHORT).show()
              }else{
                  //success
              }
          }
      }

  }
}