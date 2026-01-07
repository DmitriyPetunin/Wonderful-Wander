package com.example.feature.profile.impl.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileStat(
    numberText: String,
    text:String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){

    Column(
        modifier = modifier.clickable {
            onClick()
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = numberText,
            fontWeight = FontWeight.Bold,
            fontSize =  20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)

    }

}