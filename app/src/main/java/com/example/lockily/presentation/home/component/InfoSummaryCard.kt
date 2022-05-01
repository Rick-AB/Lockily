package com.example.lockily.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lockily.presentation.home.CredentialItem

@Composable
fun InfoSummaryCard(
    title: String,
    description: String,
    credentialList: List<CredentialItem>,
    onItemClick: (String) -> Unit
) {
    Column(
        Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 20.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = description, style = MaterialTheme.typography.caption.copy(fontSize = 16.sp))
        credentialList.forEach {
            val iconResource = it.iconResource
            val name = it.name
            val count = it.count
            CredentialItem(
                iconResource = iconResource,
                name = name,
                count = count,
                modifier = Modifier.padding(vertical = 24.dp),
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun CredentialItem(
    iconResource: Int,
    name: String,
    count: Int,
    modifier: Modifier,
    onItemClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(name)
            }) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = "",
            tint = Color.Blue,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "$name ($count)",
            style = MaterialTheme.typography.body1.copy(color = Color.Black, fontSize = 16.sp)
        )
    }
}