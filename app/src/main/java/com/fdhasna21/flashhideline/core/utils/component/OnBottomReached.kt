package com.fdhasna21.flashhideline.core.utils.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.foundation.lazy.LazyListState

/**
 * Created by Fernanda Hasna on 12/08/2026.
 * **/

@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false
            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect { isReached ->
                if (isReached) {
                    onLoadMore()
                }
            }
    }
}