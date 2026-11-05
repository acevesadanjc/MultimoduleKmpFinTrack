import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    init() {
        guard let flavorCode = Bundle.main.object(
                    forInfoDictionaryKey: "AppFlavor"
                ) as? String, !flavorCode.isEmpty else {
                    fatalError("AppFlavor is missing from Info.plist")
                }

        KoinIOSKt.doInitKoinIOS(flavorCode: flavorCode)
    }
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
