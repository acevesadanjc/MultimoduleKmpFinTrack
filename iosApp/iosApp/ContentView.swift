import UIKit
import SwiftUI
import PulseUI
import Shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Self.Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Self.Context) {}
}

struct ContentView: View {
    
    @State private var showPulse = false
    
    var body: some View {
        ZStack {
            ComposeView().ignoresSafeArea()
            #if DEBUG
            VStack {
                Button(action: { showPulse = true }) {
                    Text("⚙️ Show logs")
                        .padding(10)
                        .background(.ultraThinMaterial)
                        .cornerRadius(8)
                }.padding(.bottom, 50)
                Spacer()
            }
            #endif
        }
        .onOpenURL { url in
            _ = DeepLinkBridge.shared.handle(url: url.absoluteString)
        }
        .sheet(isPresented: $showPulse) {
            NavigationView {
                ConsoleView()
                    .navigationTitle("Logs console")
                    .toolbar {
                        ToolbarItem(placement: .navigationBarTrailing) {
                            Button("Close") { showPulse = false }
                        }
                    }
            }
        }
    }
}
