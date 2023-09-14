import SwiftUI
import shared
      struct ContentView: View {
        @ObservedObject private(set) var viewModel: ViewModel

          var body: some View {
              NavigationView {
                  listView()
                  .navigationBarTitle("School Details")
                  .navigationBarItems(trailing:
                      Button("Reload") {
                          self.viewModel.loadSchools(forceReload: true)
                  })
              }
          }


 private func listView() -> AnyView {
        switch viewModel.schools {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let schools):
            return AnyView(List(schools) { school in
                SchoolRow(schoolData: school)
            })
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }

}

extension ContentView {
    enum LoadableSchools {
        case loading
        case result([SchoolsListData])
        case error(String)
    }

    
    @MainActor
       class ViewModel: ObservableObject {
           let sdk: NYCSchoolsSDK
            @Published var schools = LoadableSchools.loading

           init(sdk: NYCSchoolsSDK) {
               self.sdk = sdk
               self.loadSchoolData(forceReload: false)
           }

          
           func loadSchools(forceReload: Bool) {
               Task {
                   do {
                       self.schools = .loading
                       let schools = try await sdk.getAllSchoolNames(forceReload: forceReload)
                       self.schools = .result(schools)
                   } catch {
                       self.schools = .error(error.localizedDescription)
                   }
               }
           }
       }
 }

extension SchoolsListData: Identifiable { }





 
