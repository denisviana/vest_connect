package thedantas.vestconnect.data.data_source
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

class NetworkInfoDataSource(context: Context) {

    private val connections = mutableListOf<Int>()

    init {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()

        cm.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                connections.add(network.hashCode())
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                connections.remove(network.hashCode())
            }
        })
    }

    fun hasInternetConnection(): Boolean {
        return connections.isNotEmpty()
    }
}
