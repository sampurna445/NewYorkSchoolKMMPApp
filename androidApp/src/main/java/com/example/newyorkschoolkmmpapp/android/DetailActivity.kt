package com.example.newyorkschoolkmmpapp.android

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jetbrains.handson.kmm.shared.NYCSchoolsSDK
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {
    private val mainScope = MainScope()

    private val sdk = NYCSchoolsSDK(DatabaseDriverFactory(this))

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.detail_school)

        val schoolName = intent.getStringExtra("SCHOOL_NAME")!!

        // calling the action bar
        val actionBar = supportActionBar
        supportActionBar?.title = schoolName
        actionBar?.setDisplayHomeAsUpEnabled(true)






        fetchSchoolSATsData(schoolName)
    }

    private fun fetchSchoolSATsData(schoolName: String) {
        mainScope.launch {
            kotlin.runCatching {
                sdk.getSelectedSchoolSATsScoreData(schoolName)
            }.onSuccess {
                if (it != null) {
                    val schoolNameText = findViewById<TextView>(R.id.school_name_retrieved)

                    schoolNameText.text = it.schoolName
                    findViewById<TextView>(R.id.satNumbers).text =
                        "Number of SATs Takers : " + it.numOfSATTakers
                    findViewById<TextView>(R.id.readingScores).text =
                        "SATs Reading Score : " + it.satReadingAvgScore
                    findViewById<TextView>(R.id.writingScores).text =
                        "SATs Writing Score : " + it.satWritingAvgScore
                    findViewById<TextView>(R.id.mathScores).text =
                        "Maths Average Score : " + it.satMathAvgScore
                }
            }.onFailure {
                Toast.makeText(this@DetailActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

}
