package com.example.gafitouser

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.gafitouser.data.Event
import com.example.gafitouser.data.LaporanData
import com.example.gafitouser.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception
import java.util.UUID
import javax.inject.Inject

const val USERS = "users"
const val LAPORAN = "laporan"
const val PARKIR = "parkir"

@HiltViewModel
class GafitoViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
) : ViewModel() {

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val popupNotification = mutableStateOf<Event<String>?>(null)

    var isCondition = mutableStateOf(false)

    val refreshLaporanProgress = mutableStateOf(false)
    val laporans = mutableStateOf<List<LaporanData>>(listOf())


    init {
//        auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let { uid ->
            getUserData(uid)
        }
    }

    fun onSignup(username: String, email: String, pass: String) {
        if (username.isEmpty() or email.isEmpty() or pass.isEmpty()) {
            handleException(customMessage = "Please fill in all fields")
            return
        }
        inProgress.value = true

        db.collection(USERS).whereEqualTo("username", username).get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    handleException(customMessage = "Username already exists")
                    inProgress.value = false
                } else {
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                signedIn.value = true
                                createOrUpdateProfile(username = username)
                            } else {
                                handleException(task.exception, "Sign Up Failed")
                            }
                            inProgress.value = false
                        }
                }
            }
            .addOnFailureListener { }
    }

    fun onLogin(email: String, pass: String) {
        if (email.isEmpty() or pass.isEmpty()) {
            handleException(customMessage = "Please fill in all fields")
            return
        }
        inProgress.value = true
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signedIn.value = true
                    inProgress.value = false
                    auth.currentUser?.uid?.let { uid ->
                        handleException(customMessage = "Login Success")
                        getUserData(uid)
                    }
                } else {
                    handleException(task.exception, "Login Failed")
                    inProgress.value = false
                }
            }
            .addOnFailureListener { exc ->
                handleException(exc, "Login Failed")
                inProgress.value = false
            }
    }

    private fun createOrUpdateProfile(
        name: String? = null,
        username: String? = null,
        imageUrl: String? = null,
        noPolisi: String? = null,
        jenisMotor: String? = null,
        noHP: String? = null
    ) {
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            username = username ?: userData.value?.username,
            imageUrl = imageUrl ?: userData.value?.imageUrl,
            noPolisi = noPolisi ?: userData.value?.noPolisi,
            jenisMotor = jenisMotor ?: userData.value?.jenisMotor,
            noHP = noHP ?: userData.value?.noHP
        )

        uid?.let { uid ->
            inProgress.value = true
            db.collection(USERS).document(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    it.reference.update(userData.toMap())
                        .addOnSuccessListener {
                            this.userData.value = userData
                            inProgress.value = false
                        }
                        .addOnFailureListener {
                            handleException(it, "Cannot update user")
                            inProgress.value = false
                        }
                } else {
                    db.collection(USERS).document(uid).set(userData)
                    getUserData(uid)
                    inProgress.value = false
                }
            }
                .addOnFailureListener { exc ->
                    handleException(exc, "Cannot create User")
                    inProgress.value = false
                }
        }
    }

    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(USERS).document(uid).get()
            .addOnSuccessListener {
                val user = it.toObject<UserData>()
                userData.value = user
                inProgress.value = false
                refreshLaporan()
//                getCondition()
                statusParkir()
            }
            .addOnFailureListener { exc ->
                handleException(exc, "Cannot retrieve user data")
                inProgress.value = false
            }

    }

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)
    }

    fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        inProgress.value = true

        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("images/$uuid")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener(onSuccess)
        }
            .addOnFailureListener{exc ->
                handleException(exc)
                inProgress.value = false
            }
    }

    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri) {
            createOrUpdateProfile(imageUrl = it.toString())
        }
    }

    fun refreshLaporan() {
        val currentUid = auth.currentUser?.uid

        if (currentUid != null) {
            refreshLaporanProgress.value = true
            db.collection(LAPORAN).get()
                .addOnSuccessListener { documents ->
                    convertLaporan(documents, laporans)
                    refreshLaporanProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Gagal mengambil laporan")
                    refreshLaporanProgress.value = false
                }
        } else {
            handleException(customMessage = "Error! Gagal memuat laporan")
        }
    }

    private fun convertLaporan(documents: QuerySnapshot, outState: MutableState<List<LaporanData>>) {
        val newLaporans = mutableListOf<LaporanData>()
        documents.forEach { doc ->
            val laporan = doc.toObject<LaporanData>()
            newLaporans.add(laporan)
        }
        val sortedLaporans = newLaporans.sortedByDescending { it.time }
        outState.value = sortedLaporans
    }

//    fun statusParkir(noPolisi: String) {
//        val currentUid = auth.currentUser?.uid
//        val currentNoPolisi = userData.value?.noPolisi
//
//        if (currentUid != null) {
//            val userRef = db.collection(PARKIR).whereEqualTo("noPolisi", currentNoPolisi)
//            userRef.get().addOnSuccessListener { querySnapshot ->
//                // Jika data user ditemukan, mengubah kondisi menjadi true
//                if (querySnapshot.size() > 0) {
//                    isCondition.value = true
//                }
//            }
//        }
//    }

    fun statusParkir() {
        val currentUid = auth.currentUser?.uid
        val currentNoPolisi = userData.value?.noPolisi

        inProgress.value = true

        if (currentUid != null) {
            val userRef = db.collection(PARKIR).whereEqualTo("noPolisi", currentNoPolisi)
            userRef.get().addOnSuccessListener { querySnapshot ->
                // Jika data user ditemukan, mengubah kondisi menjadi true
                if (querySnapshot.size() > 0) {
                    isCondition.value = true
                }
            }
            inProgress.value = false
        } else {
            inProgress.value = false
            handleException(customMessage = "There is Something Error")
        }
    }

}