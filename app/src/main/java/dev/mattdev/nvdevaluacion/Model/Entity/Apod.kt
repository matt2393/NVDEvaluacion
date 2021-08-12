package dev.mattdev.nvdevaluacion.Model.Entity


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity
data class Apod(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var date: String = "",
    var explanation: String = "",
    var hdurl: String = "",
    var media_type: String = "",
    var service_version: String = "",
    var title: String = "",
    var url: String = ""
) : Parcelable