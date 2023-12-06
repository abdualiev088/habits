package com.example.habittracker.ui.compose

import android.content.res.Resources
import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.habittracker.recyclerViewAdapter.HabitData

@Composable
fun RecyclerItem(data: HabitData){
    Row(
        modifier = Modifier
            .background(colorResource(R.color.rc_item_background))
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ){
        Text(
            text = data.title,
            color = Color.Black,
            fontWeight = FontWeight.Black
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            text = data.status.toString(),
            color = Color.Black,
        )
        Text(
            text = data.time_created,
            color = Color.Black,
            fontWeight = FontWeight.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    RecyclerItem(HabitData("Title", false, "102302"))
}