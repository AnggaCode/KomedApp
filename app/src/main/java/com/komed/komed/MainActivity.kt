package com.komed.komed


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class MainActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 9001 // Kode permintaan sign in Google
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Konfigurasi sign in Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Inisialisasi Shared Preferences
        sharedPreferences = getSharedPreferences("KOMED", Context.MODE_PRIVATE)

        val login: ImageButton = findViewById(R.id.masuk)
        // Periksa status login pengguna
        val isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false)

        if (isLoggedIn) {
            // Jika pengguna sudah login sebelumnya, langsung navigasikan ke halaman baru
            navigateToNewActivity()
        } else {
            // Jika pengguna belum login, tampilkan tombol login
            login.setOnClickListener { signIn() }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Sign in Google berhasil
                val account = task.getResult(ApiException::class.java)

                // Simpan status login pengguna ke dalam Shared Preferences
                val editor = sharedPreferences.edit()
                editor.putBoolean("IS_LOGGED_IN", true)
                editor.apply()

                // Navigasikan ke halaman baru
                navigateToNewActivity()

            } catch (e: ApiException) {
                // Sign in Google gagal
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            }
        }
    }

    private fun navigateToNewActivity() {
        val intent = Intent(this, CreateUserActivity::class.java)
        startActivity(intent)
        // Selesaikan aktivitas saat ini agar pengguna tidak dapat kembali ke halaman login dengan menekan tombol "back"
        finish()
    }
}
