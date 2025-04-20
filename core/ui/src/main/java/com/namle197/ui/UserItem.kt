package com.namle197.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.namle197.designsystem.theme.MobileTakeHomeTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun UserItem(name: String, profileUrl: String, avatarUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar
            Box(modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center,
                ) {
                Image(
                    painter = rememberAsyncImagePainter(avatarUrl),
                    contentDescription = "Profile picture of $name",
                    modifier = Modifier
                        .size(95.dp)
                        .padding(2.dp)
                        .clip(CircleShape), // Make it round
                    contentScale = ContentScale.Crop
                )
            }

            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(0.7f),
                contentAlignment = Alignment.TopStart) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopStart),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    Divider(
                        color = Color.Black.copy(alpha = 0.1f),
                        modifier = Modifier,
                    )

                    Text(
                        text = profileUrl,
                        fontSize = 14.sp,
                        color = Color.Blue.copy(alpha = 0.5f),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl))
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                // Handle the exception if no browser app is found
                                e.printStackTrace()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UserItemPreview() {
    MobileTakeHomeTheme {
        UserItem(
            name = "Nam Le",
            profileUrl = "https://avatars.githubusercontent.com/u/101?v=4",
            avatarUrl = "https://avatars.githubusercontent.com/u/101?v=4",
            modifier = Modifier
        )
    }
}