package com.aliosmanarslan.foodproject.viewmodel

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.aliosmanarslan.foodproject.R
import com.aliosmanarslan.foodproject.view.activities.MainActivity

class UserSignUpFragmentViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    val wait = MutableLiveData<Boolean>()

    fun createUser(activity: FragmentActivity, context: Context, view: View, userEmail : String, userPassword : String, userPasswordAgain : String){
        if (userEmail.isBlank() || userPassword.isBlank() || userPasswordAgain.isBlank()){
            Snackbar.make(view, context.resources.getString(R.string.please_dont_empty_field), Snackbar.LENGTH_LONG).show()
        }else{
            if (userPassword != userPasswordAgain){
                Snackbar.make(view, context.resources.getString(R.string.do_not_match_password), Snackbar.LENGTH_LONG).show()
            }else{
                wait.value = true
                auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    activity.finish()
                }.addOnFailureListener {
                    wait.value = false
                    when(it.localizedMessage){
                        context.resources.getString(R.string.firebase_badly_email_format) -> Snackbar.make(view, context.resources.getString(R.string.snack_bar_email), Snackbar.LENGTH_LONG).show()
                        context.resources.getString(R.string.firebase_invalid_password) -> Snackbar.make(view, context.resources.getString(R.string.snack_bar_password), Snackbar.LENGTH_LONG).show()
                        else -> Snackbar.make(view, context.resources.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    fun moveSignInFragment(v : View){
        Navigation.findNavController(v).navigate(R.id.action_userSignUpFragment_to_userSignInFragment)
    }
}