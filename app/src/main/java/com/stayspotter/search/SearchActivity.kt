package com.stayspotter.search

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.stayspotter.Constant

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewSearch()
        }
    }



}

@Composable
@Preview
fun PreviewSearch() {
    Column {
        Text(text = "Hello World!", color = Constant.LIGHT_EDGE_BLUE)
    }
}