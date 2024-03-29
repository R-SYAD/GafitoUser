package com.example.gafitouser

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.gafitouser.data.Event
import com.example.gafitouser.data.LaporanData
import com.example.gafitouser.data.ParkirData
import com.example.gafitouser.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.UUID
import javax.inject.Inject

const val USERS = "users"
const val LAPORAN = "laporan"
const val PARKIR = "parkir"
const val TOPIC = "/topics/myTopic"

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

    val parkirProgress = mutableStateOf(false)
    val parkirs = mutableStateOf<List<ParkirData>>(listOf())
    val userParkir = mutableStateOf<ParkirData?>(null)
    val parkirUser = ParkirData()

    init {
//        auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let { uid ->
            getUserData(uid)
//            getUserParkir(uid)

        }
    }

    fun onSignup(
        noPolisi: String,
        email: String,
        pass: String,
        name: String,
        jenisMotor: String,
        noHP: String
    ) {
        if (noPolisi.isEmpty() or email.isEmpty() or pass.isEmpty() or jenisMotor.isEmpty() or name.isEmpty() or noHP.isEmpty()) {
            handleException(customMessage = "Please fill in all fields")
            return
        }
        inProgress.value = true

        db.collection(USERS).whereEqualTo("noPolisi", noPolisi).get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    handleException(customMessage = "No Polisi already exists")
                    inProgress.value = false
                } else {
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                signedIn.value = true
                                createOrUpdateProfile(
                                    noPolisi = noPolisi,
                                    jenisMotor = jenisMotor,
                                    noHP = noHP,
                                    name = name
                                )
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
                    auth.currentUser?.uid?.let { uid ->
                        inProgress.value = false
                        getUserData(uid)
                        // Cek role setelah login berhasil
                        db.collection(USERS).document(uid).get()
                            .addOnSuccessListener {
                                inProgress.value = true
                                val user = it.toObject<UserData>()
                                if (user?.role == "user") {
                                    signedIn.value = true
                                    inProgress.value = false
                                    // Role valid, lanjutkan navigasi
                                    handleException(customMessage = "Login Success")
                                } else {
                                    inProgress.value = false
                                    // Role tidak valid, logout pengguna
                                    handleException(customMessage = "Akun anda tidak ditemukan")
                                    auth.signOut()
                                }
                            }
                            .addOnFailureListener { exc ->
                                handleException(exc, "Cannot retrieve user data")
                                inProgress.value = false
                            }
                    }
//                    userParkir
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
        imageUrl: String? = null,
        noPolisi: String? = null,
        jenisMotor: String? = null,
        noHP: String? = null
    ) {
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
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

    fun getUserParkir(uid: String) {
        inProgress.value = true
        db.collection(PARKIR).whereEqualTo("userId", uid).get()
            .addOnSuccessListener { querySnapshot ->
                val parkir = querySnapshot.documents[0].toObject(ParkirData::class.java)
                Log.d("GetDataPark", "isi data $parkir")
                parkirUser.userId = parkir?.userId
                parkirUser.latitude = parkir?.latitude
                parkirUser.longitude = parkir?.longitude
                parkirUser.isLocationMarked = parkir?.isLocationMarked
                parkirUser.locationName = parkir?.locationName
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

    fun handleSuccess(customMessage: String = "") {
        val message =  "$customMessage"
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
            .addOnFailureListener { exc ->
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
                    sendFCMNotification("Laporan Baru", "Ada laporan baru yang perlu diperiksa.")
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Gagal mengambil laporan")
                    refreshLaporanProgress.value = false
                }
        } else {
            handleException(customMessage = "Error! Gagal memuat laporan")
        }
    }

    private fun convertLaporan(
        documents: QuerySnapshot,
        outState: MutableState<List<LaporanData>>
    ) {
        val newLaporans = mutableListOf<LaporanData>()
        documents.forEach { doc ->
            val laporan = doc.toObject<LaporanData>()
            newLaporans.add(laporan)
        }
        val sortedLaporans = newLaporans.sortedByDescending { it.time }
        outState.value = sortedLaporans
    }

    private fun sendFCMNotification(title: String, message: String) {
        // Ambil instance FirebaseMessaging
        val firebaseMessaging = FirebaseMessaging.getInstance()

        // Buat data untuk pesan FCM
        val data = mapOf(
            "title" to title,
            "message" to message
        )

        // Buat instance RemoteMessage
        val remoteMessage = RemoteMessage.Builder("574694477115")
            .setData(data)
            .build()

        // Kirim pesan FCM
        firebaseMessaging.send(remoteMessage)
    }


    fun refreshParkir() {
        val currentUid = auth.currentUser?.uid

        if (currentUid != null) {
            parkirProgress.value = true
            db.collection(PARKIR).get()
                .addOnSuccessListener { documents ->
                    convertParkir(documents, parkirs)
                    parkirProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Gagal mengambil data parkir")
                    parkirProgress.value = false
                }
        } else {
            handleException(customMessage = "Error! Gagal memuat data parkir")
        }
    }

    private fun convertParkir(documents: QuerySnapshot, outState: MutableState<List<ParkirData>>) {
        val newParkir = mutableListOf<ParkirData>()
        documents.forEach { doc ->
            val parkir = doc.toObject<ParkirData>()
            newParkir.add(parkir)
        }
        val sortedLaporans = newParkir.sortedByDescending { it.time }
        outState.value = sortedLaporans
    }

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

    fun markLocationInParkir(
        latitude: String,
        longitude: String,
        locationName: String,
        isLocationMarked: Boolean,
        onMarkSuccess: () -> Unit
    ) {
        val currentUid = auth.currentUser?.uid
        val currentNoPolisi = userData.value?.noPolisi
        inProgress.value = true

        if (currentUid != null && currentNoPolisi != null) {
            // Pemeriksaan keberadaan parkir dengan noPolisi yang sesuai
            val parkirRef = db.collection(PARKIR)
                .whereEqualTo("noPolisi", currentNoPolisi)

            parkirRef.get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.size() > 0) {
                        // Jika noPolisi sesuai, simpan data parkir
                        val parkirData = querySnapshot.documents[0].toObject<ParkirData>()
                        if (parkirData != null && parkirData.noPolisi == currentNoPolisi) {
                            val data = mapOf(
                                "latitude" to latitude,
                                "longitude" to longitude,
                                "locationName" to locationName,
                                "isLocationMarked" to isLocationMarked
                            )

                            db.collection(PARKIR)
                                .document(querySnapshot.documents[0].id)
                                .update(data)
                                .addOnSuccessListener {
                                    Log.d(
                                        "MarkLocation",
                                        "Location marked and updated in parkir collection!"
                                    )
                                    handleSuccess(customMessage = "Lokasi Berhasil Ditandai")
//                                    isLocationMarked.value = true
                                    inProgress.value = false
                                    refreshParkir()
                                    onMarkSuccess.invoke()
                                    val currentUser = auth.currentUser
                                    currentUser?.uid?.let { uid ->
                                        getUserData(uid)
                                        getUserParkir(uid)
                                    }
                                }
                                .addOnFailureListener { exc ->
                                    handleException(
                                        exc,
                                        "Failed to mark location and update parkir collection"
                                    )
                                    inProgress.value = false
                                }
                        } else {
                            handleException(customMessage = "User authentication error or noPolisi mismatch")
                            inProgress.value = false
                        }
                    } else {
                        handleException(customMessage = "No matching parkir document found for noPolisi: $currentNoPolisi")
                        inProgress.value = false
                    }
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to query parkir collection")
                    inProgress.value = false
                }
        } else {
            handleException(customMessage = "User authentication error or noPolisi is null")
            inProgress.value = false
        }
    }

    fun deleteMarkedLocationFromParkir(
        latitude: String,
        longitude: String,
        locationName: String,
        isLocationMarked: Boolean = false,
        onMarkSuccess: () -> Unit
    ) {
        val currentUid = auth.currentUser?.uid
        val currentNoPolisi = userData.value?.noPolisi
        inProgress.value = true

        if (currentUid != null && currentNoPolisi != null) {
            // Pemeriksaan keberadaan parkir dengan noPolisi yang sesuai
            val parkirRef = db.collection(PARKIR)
                .whereEqualTo("noPolisi", currentNoPolisi)

            parkirRef.get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.size() > 0) {
                        // Jika noPolisi sesuai, hapus data parkir yang sesuai dengan noPolisi dan UID
                        val parkirData = querySnapshot.documents[0].toObject<ParkirData>()
                        if (parkirData != null && parkirData.noPolisi == currentNoPolisi) {
                            val data = mapOf(
                                "latitude" to null,
                                "longitude" to null,
                                "locationName" to null,
                                "isLocationMarked" to null
                            )

                            db.collection(PARKIR)
                                .document(querySnapshot.documents[0].id)
                                .update(data)
                                .addOnSuccessListener {
                                    Log.d(
                                        "MarkLocation",
                                        "Location marked and updated in parkir collection!"
                                    )
//                                    isLocationMarked.value = false
                                    handleSuccess(customMessage = "Tanda Lokasi Berhasil Dihapus")
                                    inProgress.value = false
                                    refreshParkir()
                                    onMarkSuccess.invoke()
                                    val currentUser = auth.currentUser
                                    currentUser?.uid?.let { uid ->
                                        getUserData(uid)
                                        getUserParkir(uid)
                                    }
                                }
                                .addOnFailureListener { exc ->
                                    handleException(
                                        exc,
                                        "Failed to mark location and update parkir collection"
                                    )
                                    inProgress.value = false
                                }
                        } else {
                            handleException(customMessage = "User authentication error or noPolisi mismatch")
                            inProgress.value = false
                        }
                    } else {
                        handleException(customMessage = "No matching parkir document found for noPolisi: $currentNoPolisi")
                        inProgress.value = false
                    }
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to query parkir collection")
                    inProgress.value = false
                }
        } else {
            handleException(customMessage = "User authentication error or noPolisi is null")
            inProgress.value = false
        }
    }

    fun onLogout() {
        auth.signOut()
        signedIn.value = false
        userData.value = null
        userParkir.value = null
        popupNotification.value = Event("Logged Out")
    }

}