package com.example.habittracker.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.habittracker.firebase.UserDataset
import java.lang.Math.round

@Composable
fun RecyclerItem(index: Int, data: UserDataset?){
    Row(
        modifier = Modifier
            .background(colorResource(R.color.rc_item_background))
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ){
        Text(
            text = (index + 1).toString(),
            color = Color.Black,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            text = data!!.email.toString(),
            color = Color.Black,
            fontWeight = FontWeight.Black
        )
        val roundedNumber = data.roundedNumber?.toInt().toString() + " %"
        Text(
            text = roundedNumber,
            color = Color.Black,
        )
    }
}