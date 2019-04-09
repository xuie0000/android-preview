package com.xuie.android.ui.coordinator

import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.xuie.android.R

/**
 * @author xuie
 */
class CoordinatorLayoutActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_coordinator_layout)

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    val fab = findViewById<FloatingActionButton>(R.id.fab)
    setSupportActionBar(toolbar)
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fab.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
