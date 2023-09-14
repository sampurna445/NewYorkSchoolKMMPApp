
//
//  SchoolDataRow.swift
//  iosApp
//
//  Created by itc on 12/09/2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import SwiftUI
import shared

struct SchoolDataRow: View {
    var schoolData: SchoolsListData
    @State private var isDetailsActive = false // To control the navigation

    var body: some View {
        @State var db_input = schoolData.dbn

        NavigationLink(destination: SchoolDetailsView(input: $db_input), isActive: $isDetailsActive) {
            EmptyView()
        }
        .opacity(0) // Hide the NavigationLink

        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("DBN: \(schoolData.dbn)")
                Text("School Name: \(schoolData.school_name)")
                    .onTapGesture {
                        // When the school name is tapped, activate the navigation
                        isDetailsActive = true
                    }
            }
            Spacer()
        }
    }
}
