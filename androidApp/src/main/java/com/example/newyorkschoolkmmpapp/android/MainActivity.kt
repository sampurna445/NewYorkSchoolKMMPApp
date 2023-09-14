package com.example.newyorkschoolkmmpapp.android

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jetbrains.handson.kmm.shared.NYCSchoolsSDK
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var schoolsRecyclerView: RecyclerView
    private lateinit var progressBarView: FrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    // private val schoolsRvAdapter = NYCSchoolsRVAdapter(listOf())


    private val sdk = NYCSchoolsSDK(DatabaseDriverFactory(this))
    private val mainScope = MainScope()


    private val navigateFn: (String) -> Unit = { schoolName ->
        val intent = Intent(getApplicationContext(), DetailActivity::class.java);
        intent.putExtra("SCHOOL_NAME", schoolName)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "NYC Schools"
        setContentView(R.layout.activity_main)

        schoolsRecyclerView = findViewById(R.id.schoolsListRv)
        progressBarView = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeContainer)




        schoolsRecyclerView.layoutManager = LinearLayoutManager(this)


        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            displaySchools(true)
        }

        displaySchools(false)
    }


    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun displaySchools(needReload: Boolean) {
        progressBarView.isVisible = true
        mainScope.launch {
            kotlin.runCatching {
                sdk.getAllSchoolNames(needReload)
            }.onSuccess {
                val schoolsRvAdapter = NYCSchoolsRVAdapter(it, navigateFn)
                schoolsRvAdapter.notifyDataSetChanged()
                schoolsRecyclerView.adapter = schoolsRvAdapter
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            progressBarView.isVisible = false
        }
    }
}
