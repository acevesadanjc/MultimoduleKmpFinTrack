import com.kmp.core.navigation.deeplink.DeepLinkCoordinator

object DeepLinkBridge {
    fun handle(url: String): Boolean = DeepLinkCoordinator.saveDeepLinkRoute(url)
}