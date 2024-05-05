package com.example.furniture_app.viewmodels.Authentication

import androidx.lifecycle.ViewModel
import com.example.furniture_app.data.User
import com.example.furniture_app.util.Constants.USER_COLLECTION
import com.example.furniture_app.util.RegisterFieldsState
import com.example.furniture_app.util.RegisterValidation
import com.example.furniture_app.util.Resource
import com.example.furniture_app.util.validateEmail
import com.example.furniture_app.util.validateFirstName
import com.example.furniture_app.util.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private  val firebaseAuth: FirebaseAuth,
    private val db:FirebaseFirestore
):ViewModel() {
    private var _register =MutableStateFlow<Resource<User>>(Resource.Loading())
    var register: Flow<Resource<User>> = _register
    private val _validation = Channel<RegisterFieldsState> ()
    val validation = _validation.receiveAsFlow()

    fun createAccountWithEmailAndPassword(user: User,password:String){
        if(shouldregister(user, password)) {

            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it.user?.let {
//                        _register.value = Resource.Success(it)
                        saveUserInfo(it.uid,user)
                    }

                }.addOnFailureListener {
                    _register.value = Resource.Error(it.message.toString())
                }
        }
        else{
            val registerFieldsState= RegisterFieldsState(
               validateEmail(user.email),
                validatePassword(password),
                validateFirstName(user.firstName)
            )
            runBlocking {
                _validation.send(registerFieldsState)
            }
        }
    }

    private fun saveUserInfo(userUid:String,user: User){
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value=Resource.Success(user)
            }.addOnFailureListener {
                _register.value =Resource.Error(it.message.toString())
            }
    }
    private fun shouldregister(user: User, password: String):Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        val firstNameValidation = validateFirstName(user.firstName)
        return emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success && firstNameValidation is RegisterValidation.Success
    }

}