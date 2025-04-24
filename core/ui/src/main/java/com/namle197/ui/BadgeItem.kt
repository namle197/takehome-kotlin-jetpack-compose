package com.namle197.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.namle197.designsystem.theme.MobileTakeHomeTheme


@Composable
fun BadgeItem(modifier: Modifier = Modifier, icon: ImageVector, count: String, label: String) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            // Avatar
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.05f),
                        shape = CircleShape
                    )
                    .padding(8.dp),
                tint = Color.Black.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = count,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = label,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun FollowerItemPreview() {
    MobileTakeHomeTheme {
        BadgeItem(
            icon = Icons.Default.Group,
            count = "100+",
            label = "Followers"
        )
    }
}

@Preview
@Composable
fun FollowingItemPreview() {
    MobileTakeHomeTheme {
        BadgeItem(
            icon = Icons.Default.BookmarkAdded,
            count = "10+",
            label = "Following"
        )
    }
}