package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.navigation.Screen
import com.javalon.xpensewhiz.presentation.ui.theme.expenseGradient
import com.javalon.xpensewhiz.presentation.ui.theme.incomeGradient
import kotlinx.coroutines.launch

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun AddEntryChooser(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    navController: NavHostController
) {

    val scope = rememberCoroutineScope()
    val progress = bottomSheetScaffoldState.bottomSheetState.progress.fraction
    val expandRotation by animateFloatAsState(
        targetValue = -360f * progress,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = Spring.StiffnessLow)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {
                    scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                    navController.navigate("${Screen.TransactionScreen.route}/0")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.income),
                    contentDescription = "add entry",
                    tint = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .size(48.dp, 48.dp)
                        .rotate(expandRotation)
                        .background(
                            incomeGradient,
                            CircleShape
                        )
                        .padding(8.dp)
                )
            }

            Text(
                text = "ADD INCOME",
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colors.onSurface
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {
                    scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                    navController.navigate("${Screen.TransactionScreen.route}/1")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.expense),
                    contentDescription = "expense",
                    tint = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .size(48.dp, 48.dp)
                        .rotate(expandRotation)
                        .background(
                            expenseGradient,
                            CircleShape
                        )
                        .padding(8.dp)
                )
            }

            Text(
                text = "ADD EXPENSE",
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colors.onSurface
            )
        }

    }
}