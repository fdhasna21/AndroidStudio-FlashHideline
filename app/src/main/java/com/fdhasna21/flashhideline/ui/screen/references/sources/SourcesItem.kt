package com.fdhasna21.flashhideline.ui.screen.references.sources

import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fdhasna21.flashhideline.core.theme.AccentElectricAmber
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.core.utils.ext.toCountryFlagEmoji
import com.fdhasna21.flashhideline.data.dummy.HeadlinesDummyProvider
import com.fdhasna21.flashhideline.data.dummy.SourcesDummyProvider
import com.fdhasna21.flashhideline.data.model.item.SourceItem

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun SourcesItem(
    source: SourceItem,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = source.country.toCountryFlagEmoji(),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = source.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@ThemePreviews
@Composable
private fun SourcesItemPreview() {
    val context = LocalContext.current
    val objectMapper = ObjectMapper().apply { registerKotlinModule() }
    val dummyProvider = SourcesDummyProvider(context, objectMapper)

    FlashHidelineTheme {
        SourcesItem(dummyProvider.getSingleData())
    }
}