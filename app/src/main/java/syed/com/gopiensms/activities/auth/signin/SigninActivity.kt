package syed.com.gopiensms.activities.auth.signin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import syed.com.gopiensms.R

class SignInActivity: AppCompatActivity() {
	
	private lateinit var auth: FirebaseAuth
	
	
	override fun onStart() {
		super.onStart()
		val currentUser = auth.currentUser
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_signin)
		
//		FirebaseApp.initializeApp(applicationContext)
		auth = FirebaseAuth.getInstance()
		
		val email: EditText = findViewById(R.id.fieldEmail)
		val password: EditText = findViewById(R.id.fieldPass)
		val btnSignin: Button = findViewById(R.id.btnsignin)
		btnSignin.setOnClickListener(View.OnClickListener {
			signIn(email.text.toString(), password.text.toString())
		})
	}
	
	private fun signIn(email: String, password: String) {
		auth.signInWithEmailAndPassword(email, password)
			.addOnCompleteListener(this) { task ->
				if (task.isSuccessful) {
					val user = auth.currentUser
					user?.let {
						// Name, email address, and profile photo Url
//						val name = user.displayName
						val email = user.email
//						val photoUrl = user.photoUrl
						
						// Check if user's email is verified
//						val emailVerified = user.isEmailVerified
						
						// The user's ID, unique to the Firebase project. Do NOT use this value to
						// authenticate with your backend server, if you have one. Use
						// FirebaseUser.getToken() instead.
//						val uid = user.uid
						Toast.makeText(baseContext, "Authentication Successful:\n."+email, Toast.LENGTH_SHORT).show()
					}
				} else {
					Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
				}

				// [START_EXCLUDE]
				if (!task.isSuccessful) {
				//	status.setText(R.string.auth_failed)
					Toast.makeText(baseContext, "UnSuccessful.", Toast.LENGTH_SHORT).show()
				}
			}
	}
	
}
