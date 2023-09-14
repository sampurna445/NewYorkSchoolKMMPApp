//
//  SchoolDetailsView.swift
//  iosApp
//
//  Created by itc on 13/09/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI


struct SchoolDetailsView: View {
    @Binding var input: String
    let url = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json"

    @State private var firstInput: String = "01M650"
    @State private var dbn: String = ""
    @State private var schoolName: String = ""
    @State private var satNumber: String = ""
    @State private var readingScore: String = ""
    @State private var writingScore: String = ""
    @State private var mathScore: String = ""


    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)

            Text("DBN: \(dbn)")
            Text("School Name: \(schoolName)").bold()
            Text("")
                           .frame(height: 16)

            HStack {
                            Text("Number of SAT Candidates: \(satNumber)")
                            Spacer()
                        }
                        .padding(.leading, 20)

                        HStack {
                            Text("Reading Scores: \(readingScore)")
                            Spacer()
                        }
                        .padding(.leading, 20)

                        HStack {
                            Text("Writing Scores: \(writingScore)")
                            Spacer()
                        }
                        .padding(.leading, 20)

                        HStack {
                            Text("Math Scores: \(mathScore)")
                            Spacer()
                        }
                        .padding(.leading, 20)
            Spacer()


        }
        .navigationTitle("SchoolDetailsView")

        .onAppear {
            getData(from: url, withDBN: input)
        }
        .padding()
    }

    func getData(from url: String, withDBN dbn: String) {
        let task = URLSession.shared.dataTask(with: URL(string: url)!, completionHandler: { data, response, error in
            guard let data = data, error == nil else {
                print("Something's wrong")
                return
            }

            do {
                let results = try JSONDecoder().decode([Response].self, from: data)
                if let matchingResult = results.first(where: { $0.dbn == dbn }) {
                    self.dbn = matchingResult.dbn
                    self.schoolName = matchingResult.school_name
                    self.satNumber = matchingResult.num_of_sat_test_takers
                    self.readingScore = matchingResult.sat_critical_reading_avg_score
                    self.writingScore = matchingResult.sat_writing_avg_score
                    self.mathScore = matchingResult.sat_math_avg_score

                }
            } catch {
                print("Failed to convert")
            }
        })
        task.resume()
    }
}


struct Response: Codable {
    let dbn: String
    let school_name: String
    let num_of_sat_test_takers: String
    let sat_critical_reading_avg_score: String
    let sat_math_avg_score: String
    let sat_writing_avg_score: String
}

