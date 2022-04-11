package com.javalon.xpensewhiz.presentation.home_screen.components
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.BottomSheetScaffoldState
//import androidx.compose.material.Divider
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.Icon
//import androidx.compose.material.IconButton
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.ExperimentalUnitApi
//import androidx.compose.ui.unit.TextUnit
//import androidx.compose.ui.unit.TextUnitType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.javalon.xpensewhiz.R
//import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
//import com.javalon.xpensewhiz.presentation.ui.theme.LightBlue3
//import com.javalon.xpensewhiz.util.spacing
//import kotlinx.coroutines.launch
//
//@ExperimentalFoundationApi
//@ExperimentalComposeUiApi
//@ExperimentalUnitApi
//@ExperimentalMaterialApi
//@Composable
//fun AddEntryComponent(bottomSheetScaffoldState: BottomSheetScaffoldState, homeViewModel: HomeViewModel = hiltViewModel()) {
//
//    val expenseAmount = remember { homeViewModel.transactionAmount }
//    val scope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier
//            .fillMaxHeight(MaterialTheme.spacing.sheetHeight)
//            .fillMaxWidth()
//            .background(MaterialTheme.colors.background)
//            .animateContentSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        InfoBanner(shown = homeViewModel.showInfoBanner.value)
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    start = MaterialTheme.spacing.large,
//                    end = MaterialTheme.spacing.small,
//                    top = MaterialTheme.spacing.small
//                ),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Add Transaction",
//                style = MaterialTheme.typography.subtitle1,
//                color = MaterialTheme.colors.onSurface
//            )
//            IconButton(
//                onClick = {
//                    scope.launch {
//                        bottomSheetScaffoldState.bottomSheetState.collapse()
//                    }
//                }
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.remove),
//                    contentDescription = "remove",
//                    tint = MaterialTheme.colors.onSurface,
//                    modifier = Modifier.align(Alignment.Top)
//                )
//            }
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            Text(
//                text = "NGN",
//                modifier = Modifier
//                    .padding(
//                        start = MaterialTheme.spacing.large,
//                        end = MaterialTheme.spacing.medium
//                    )
//                    .background(LightBlue3, shape = RoundedCornerShape(24.dp))
//                    .padding(
//                        horizontal = MaterialTheme.spacing.large,
//                        vertical = MaterialTheme.spacing.small
//                    )
//                    .align(Alignment.CenterVertically),
//                style = MaterialTheme.typography.h3.copy(fontSize = 24.sp),
//                color = MaterialTheme.colors.surface,
//            )
//
//            Text(
//                text = expenseAmount.value,
//                style = MaterialTheme.typography.h3.copy(
//                    fontSize = 36.sp,
//                    fontWeight = FontWeight.W300
//                ),
//                color = MaterialTheme.colors.onSurface,
//                modifier = Modifier.align(Alignment.CenterVertically)
//            )
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp),
//            horizontalArrangement = Arrangement.Start,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(
//                onClick = { homeViewModel.setDialogState(true) }
//            ) {
//                Icon(
//                    painter = painterResource(id = homeViewModel.expense.value.iconRes),
//                    contentDescription = "selected_expense",
//                    tint = MaterialTheme.colors.surface,
//                    modifier = Modifier
//                        .padding(
//                            start = MaterialTheme.spacing.large,
//                            top = MaterialTheme.spacing.medium,
//                            end = MaterialTheme.spacing.medium,
//                            bottom = MaterialTheme.spacing.medium
//                        )
//                        .background(LightBlue3, shape = CircleShape)
//                        .padding(12.dp)
//                )
//            }
//            Column(modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    text = homeViewModel.expense.value.title,
//                    style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp),
//                    color = MaterialTheme.colors.onSurface,
//                    letterSpacing = TextUnit(0.2f, TextUnitType.Sp)
//                )
//                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
//                Divider(modifier = Modifier.fillMaxWidth(0.9f))
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(2.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            HelperButton( modifier = Modifier.weight(1f), text = 100) {
//                homeViewModel.addToExpense(it)
//            }
//
//            HelperButton( modifier = Modifier.weight(1f), text = 250) {
//                homeViewModel.addToExpense(it)
//            }
//
//            HelperButton( modifier = Modifier.weight(1f), text = 500) {
//                homeViewModel.addToExpense(it)
//            }
//        }
//
////        KeypadComponent(homeViewModel, bottomSheetScaffoldState) {
////            homeViewModel.setTransaction(it)
////        }
//    }
//
//    Category()
//}
//
//@ExperimentalFoundationApi
//@ExperimentalComposeUiApi
//@ExperimentalUnitApi
//@ExperimentalMaterialApi
//@Preview(showBackground = true, heightDp = 580)
//@Composable
//fun PreviewBottomSheetContent() {
//}