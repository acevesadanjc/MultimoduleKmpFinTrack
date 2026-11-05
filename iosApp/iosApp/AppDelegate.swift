import SwiftUI
import OSLog
import Pulse
import PulseProxy
import PulseUI
import Firebase
import FirebaseAnalytics

class AppDelegate: NSObject, UIApplicationDelegate {
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil,
    ) -> Bool {

        FirebaseApp.configure()
        NetworkLogger.enableProxy()
        
        if let options = FirebaseApp.app()?.options {
            print("🔥 Firebase Configuration")
            print("Project ID: \(options.projectID ?? "N/A")")
            print("Google App ID: \(options.googleAppID)")
            print("Bundle ID: \(options.bundleID ?? "N/A")")
        }
        
        Analytics.setAnalyticsCollectionEnabled(true)
        
        return true
    }
}
