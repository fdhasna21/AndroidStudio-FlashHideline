package com.fdhasna21.flashhideline.core.utils.component

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.fdhasna21.flashhideline.core.theme.DARK_BACKGROUND_HEX
import com.fdhasna21.flashhideline.core.theme.LIGHT_BACKGROUND_HEX

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Preview(
    name = "Light Mode",
    showBackground = true,
    backgroundColor = LIGHT_BACKGROUND_HEX
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = DARK_BACKGROUND_HEX
)
annotation class ThemePreviews