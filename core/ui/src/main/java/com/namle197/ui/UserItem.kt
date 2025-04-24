package com.namle197.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.namle197.designsystem.theme.MobileTakeHomeTheme

@Composable
fun UserItem(name: String, profileUrl: String?, location: String?, avatarUrl: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val context = LocalContext.current
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
                onClick()
            }
        ,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar
            Box(modifier = Modifier
                .size(129.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center,
                ) {
                Image(
                    painter = rememberAsyncImagePainter(avatarUrl),
                    contentDescription = "Profile picture of $name",
                    modifier = Modifier
                        .size(115.dp)
                        .padding(2.dp)
                        .clip(CircleShape), // Make it round
                    contentScale = ContentScale.Crop
                )
            }

            Box(modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
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
                        fontSize = 18.sp,
                        color = Color.Black.copy(alpha = 0.9f)
                    )

                    Divider(
                        color = Color.Black.copy(alpha = 0.1f),
                        modifier = Modifier,
                    )

                    profileUrl?.let { nonNullProfileUrl ->
                        Text(
                            text = nonNullProfileUrl,
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

                    location?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Icon(
                                Icons.Outlined.Place,
                                contentDescription = "Location Icon",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Black.copy(alpha = 0.2f)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = location,
                                fontWeight = W400,
                                fontSize = 16.sp,
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                        }
                    }
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
            location = null,
            avatarUrl = "https://avatars.githubusercontent.com/u/101?v=4",
            modifier = Modifier,
            onClick = {}
        )
    }
}