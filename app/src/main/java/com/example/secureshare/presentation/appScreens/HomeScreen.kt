package com.example.secureshare.presentation.appScreens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.FolderShared
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.secureshare.presentation.Dashboard
import com.example.secureshare.presentation.appScreens.AppScreenViewModel
import com.example.secureshare.presentation.appScreens.receivedFiles.RecievedFiles
import com.example.secureshare.presentation.appScreens.settings.Settings
import com.example.secureshare.presentation.appScreens.sharedFiles.SharedFiles
import com.example.secureshare.presentation.appScreens.wallet.Wallet
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, appScreenViewModel: AppScreenViewModel) {
    var currentSelected = appScreenViewModel.currentSelected
    val coroutineScope = rememberCoroutineScope()
    val pages = listOf(
        "Dashboard" to Icons.Outlined.Dashboard,
        "Shared Files" to Icons.Outlined.FolderShared,
        "Wallet" to Icons.Outlined.Wallet,
        "Received Files" to Icons.Outlined.Inbox,
        "Settings" to Icons.Outlined.Settings
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { pages.size },
    )
    LaunchedEffect(pagerState){
        snapshotFlow{pagerState.currentPage}.collectLatest {
            currentSelected.value = pages[pagerState.currentPage].first
        }
        currentSelected.value = pages[pagerState.currentPage].first
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(currentSelected.value) }, colors = TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.tertiary,
                scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
                actionIconContentColor = MaterialTheme.colorScheme.tertiary
            )
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add new file",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }, bottomBar = {
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth().background(MaterialTheme.colorScheme.background)
                .clip(RoundedCornerShape(32.dp)),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                pages.forEachIndexed { index, (title,icon) ->
                    BottomBarIcons(imageVector = icon, contentDescription = title,currentSelected = currentSelected.value, curSeleted = {
                        appScreenViewModel.currentSelected.value = it
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    })
                }
            }
        }
    }) { innerPadding ->
        HorizontalPager(state = pagerState, modifier = Modifier.padding(innerPadding)) {
            when (it) {
                0 -> {
                    Dashboard()
                }
                1 -> {
                    SharedFiles()
                }
                2 -> {
                    Wallet()
                }
                3 -> {
                    RecievedFiles()
                }
                4 -> {
                    Settings()
                }
            }
        }
    }
}

@Composable
fun BottomBarIcons(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    currentSelected: String,
    curSeleted: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .scale(if (currentSelected == contentDescription) 1.5f else 1f)
                .clickable(onClick = {
                    curSeleted(contentDescription)
                }),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = if (currentSelected == contentDescription) MaterialTheme.colorScheme.tertiary else LocalContentColor.current
        )
//        Text(text = contentDescription, color = if(currentSelected==contentDescription) MaterialTheme.colorScheme.tertiary else LocalContentColor.current)
    }
}