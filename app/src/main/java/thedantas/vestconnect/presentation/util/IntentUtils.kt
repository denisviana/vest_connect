package thedantas.vestconnect.presentation.util
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import timber.log.Timber

fun openFacebook(context: Context, url: String = "https://www.facebook.com/sdabrasil/") {
    try {
        if (context.packageManager.getPackageInfo("com.facebook.katana", 0) != null) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("fb://facewebmodal/f?href=$url")
                )
            )
        }
    } catch (e: Exception) {
        Timber.e(e)
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

fun openInstagram(context: Context, url: String = "https://www.instagram.com/sdabrasil/") {
    startIntent(context, url, "com.instagram.android")
}

fun openYouTube(
    context: Context,
    url: String = "https://www.youtube.com/channel/UCKaqPdICEZ4Zb1ZnyoPS_dw"
) {
    startIntent(context, url, "com.google.android.youtube")
}

fun openFlickr(
    context: Context,
    url: String = "https://www.flickr.com/photos/semanadeavivamento/albums/"
) {
    startIntent(context, url, "com.yahoo.mobile.client.android.flickr")
}

fun openSpotify(
    context: Context,
    url: String = "https://open.spotify.com/album/0Er3VPZlYQaQKgAj7lfk8c?si=NOnWg7tBROimnm6b87uI5A"
) {
    startIntent(context, url, "com.spotify.mobile.android.ui")
}

fun openAppleMusic(
    context: Context,
    url: String = "https://music.apple.com/br/artist/sda-music/1474160339"
) {
    startIntent(context, url, "com.apple.android.music")
}

fun openDeezer(
    context: Context,
    url: String = "https://www.deezer.com/br/album/104968442"
) {
    startIntent(context, url, "deezer.android.app")
}

fun startIntent(context: Context, url: String, packageName: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    try {
        if (context.packageManager.getPackageInfo(packageName, 0) != null) {
            intent.setPackage(packageName)
        }
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.e(e)
    }
    intent.data = Uri.parse(url)
    context.startActivity(intent)
}

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    context.startActivity(intent)
}
