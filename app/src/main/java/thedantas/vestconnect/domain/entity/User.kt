package thedantas.vestconnect.domain.entity

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate

@Parcelize
@IgnoreExtraProperties
data class User(
  val holder: String,
  val email: String,
  val birthday: LocalDate,
  val password: String,
  val latitude : Float ? = null,
  val longitude : Float ? = null
) : Parcelable