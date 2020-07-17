package thedantas.vestconnect.domain.entity

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@IgnoreExtraProperties
data class User(
  val uid : String,
  val holder: String,
  val email : String,
  val birthday : Date,
  val phone : String,
  val location : String,
  val password : String
) : Parcelable