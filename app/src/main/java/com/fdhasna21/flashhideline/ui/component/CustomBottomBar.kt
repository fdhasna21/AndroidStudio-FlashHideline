package com.fdhasna21.flashhideline.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fdhasna21.flashhideline.core.theme.AccentElectricAmber
import com.fdhasna21.flashhideline.core.theme.AccentElectricAmberContainer
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.ui.navigation.BottomNavItem

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun CustomBottomBar(
    items: List<BottomNavItem>,
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = modifier.height(64.dp),
        windowInsets = WindowInsets(0.dp)
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            val cornerShape = RoundedCornerShape(8.dp)

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item) },
                interactionSource = remember { MutableInteractionSource() },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Surface(
                        color = if (isSelected) AccentElectricAmberContainer else Color.Transparent,
                        shape = cornerShape,
                        modifier = Modifier
                            .clip(cornerShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = AccentElectricAmber),
                                onClick = { onItemClick(item) }
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (isSelected) AccentElectricAmber else MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            AnimatedVisibility(
                                visible = isSelected,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically()
                            ) {
                                Row {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = AccentElectricAmber
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

@ThemePreviews
@Composable
fun CustomBottomBarStaticPreview() {
    FlashHidelineTheme {
        CustomBottomBar(
            items = listOf(
                BottomNavItem.News,
                BottomNavItem.Sources,
                BottomNavItem.Settings
            ),
            currentRoute = BottomNavItem.News.route,
            onItemClick = {}
        )
    }
}