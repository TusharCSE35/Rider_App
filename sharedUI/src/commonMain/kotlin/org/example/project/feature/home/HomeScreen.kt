package org.example.project.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.components.AppCard
import org.example.project.components.LocalToast
import org.example.project.components.OrderCard
import org.example.project.components.StatCard
import org.example.project.feature.drawer.AppDrawer
import org.example.project.feature.drawer.DrawerDestination
import org.example.project.feature.drawer.DrawerDestinationScreen
import org.example.project.feature.earnings.EarningsScreen
import org.example.project.feature.order.details.OrderDetailsScreen
import org.example.project.feature.order.history.OrdersScreen
import org.example.project.feature.order.incoming.IncomingOrderSheet
import org.example.project.feature.order.cancel.CancelOrderSheet
import org.example.project.feature.order.delivery.DeliveryConfirmScreen
import org.example.project.feature.order.pickup.PickupVerificationScreen
import org.example.project.feature.order.delivery.DeliveryProofScreen
import org.example.project.feature.order.map.OrderMapScreen
import org.example.project.feature.safety.SosScreen
import org.example.project.feature.profile.ProfileScreen
import org.example.project.feature.earnings.EarningsDestination
import org.example.project.feature.earnings.EarningsDestinationScreen
import org.example.project.feature.rider.RiderDestination
import org.example.project.feature.rider.RiderDestinationScreen
import org.example.project.model.Order
import org.example.project.model.OrderStatus
import org.example.project.model.SampleData
import org.example.project.theme.*

private data class Tab(val label: String, val emoji: String)

private val tabs = listOf(
    Tab("Home", "\uD83C\uDFE0"),
    Tab("Orders", "\uD83D\uDCE6"),
    Tab("Earnings", "\uD83D\uDCB0"),
    Tab("Profile", "\uD83D\uDC64")
)

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    var selected by remember { mutableStateOf(0) }
    var drawerScreen by remember { mutableStateOf<DrawerDestination?>(null) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var online by remember { mutableStateOf(true) }
    var currentOrder by remember { mutableStateOf<Order?>(null) }
    var incomingOffer by remember { mutableStateOf<Order?>(null) }
    var showDetails by remember { mutableStateOf(false) }
    var nextOrderId by remember { mutableStateOf(1043) }
    var unreadCount by remember { mutableStateOf(3) }
    var showPickupVerify by remember { mutableStateOf(false) }
    var showDeliveryConfirm by remember { mutableStateOf(false) }
    var showCancel by remember { mutableStateOf(false) }
    var showMap by remember { mutableStateOf(false) }
    var showDeliveryProof by remember { mutableStateOf(false) }
    var showSos by remember { mutableStateOf(false) }
    var earningsScreen by remember { mutableStateOf<EarningsDestination?>(null) }
    var riderScreen by remember { mutableStateOf<RiderDestination?>(null) }
    val toast = LocalToast.current

    fun advanceOrder() {
        val o = currentOrder ?: return
        val next = o.status.next()
        if (next == null || !next.isActive) {
            currentOrder = null
            showDetails = false
            toast.success("Order ${o.id} completed")
        } else {
            currentOrder = o.copy(status = next)
            toast.info("Order ${o.id}: ${next.label}")
        }
    }

    fun requestAdvance() {
        when (currentOrder?.status) {
            OrderStatus.ACCEPTED -> showPickupVerify = true
            OrderStatus.ON_THE_WAY -> showDeliveryConfirm = true
            else -> advanceOrder()
        }
    }

    LaunchedEffect(online, currentOrder, incomingOffer) {
        if (online && currentOrder == null && incomingOffer == null) {
            delay(3000)
            incomingOffer = SampleData.incomingOrder.copy(id = "#$nextOrderId", time = "Now")
            nextOrderId++
        }
    }

    LaunchedEffect(online) {
        if (!online) incomingOffer = null
    }

    val destination = drawerScreen
    if (destination != null) {
        DrawerDestinationScreen(
            destination = destination,
            onBack = { drawerScreen = null },
            onUnreadChange = { unreadCount = it }
        )
        return
    }
    if (showSos) {
        SosScreen(onBack = { showSos = false })
        return
    }

    val earningsDest = earningsScreen
    if (earningsDest != null) {
        EarningsDestinationScreen(destination = earningsDest, onBack = { earningsScreen = null })
        return
    }

    val riderDest = riderScreen
    if (riderDest != null) {
        RiderDestinationScreen(destination = riderDest, onBack = { riderScreen = null })
        return
    }

    val active = currentOrder
    if (showMap && active != null) {
        OrderMapScreen(order = active, onBack = { showMap = false })
        return
    }
    if (showDeliveryProof && active != null) {
        DeliveryProofScreen(
            order = active,
            onBack = { showDeliveryProof = false },
            onSubmit = { showDeliveryProof = false; advanceOrder() }
        )
        return
    }
    if (showPickupVerify && active != null) {
        PickupVerificationScreen(
            order = active,
            onBack = { showPickupVerify = false },
            onVerified = { showPickupVerify = false; advanceOrder() }
        )
        return
    }
    if (showDeliveryConfirm && active != null) {
        DeliveryConfirmScreen(
            order = active,
            onBack = { showDeliveryConfirm = false },
            onConfirmed = { showDeliveryConfirm = false; showDeliveryProof = true }
        )
        return
    }
    if (showDetails && active != null) {
        OrderDetailsScreen(
            order = active,
            onBack = { showDetails = false },
            onAdvance = { requestAdvance() },
            onCancel = { showCancel = true },
            onViewMap = { showMap = true }
        )
        if (showCancel) {
            CancelOrderSheet(
                onDismiss = { showCancel = false },
                onConfirm = { reason ->
                    toast.error("Order ${active.id} cancelled: ${reason.label}")
                    currentOrder = null
                    showDetails = false
                    showCancel = false
                }
            )
        }
        return
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onSelectTab = { selected = it; scope.launch { drawerState.close() } },
                onOpen = { drawerScreen = it; scope.launch { drawerState.close() } },
                onLogout = {
                    scope.launch { drawerState.close() }
                    onLogout()
                },
                unreadNotifications = unreadCount
            )
        }
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    tabs.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selected == index,
                            onClick = { selected = index },
                            icon = { Text(tab.emoji, fontSize = 22.sp) },
                            label = { Text(tab.label) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        ) { inner ->
            Box(Modifier.padding(inner)) {
                when (selected) {
                    0 -> HomeTab(
                        online = online,
                        currentOrder = currentOrder,
                        unreadCount = unreadCount,
                        onNotificationClick = { drawerScreen = DrawerDestination.NOTIFICATIONS },
                        onOnlineChange = {
                            online = it
                            toast.info(if (it) "You are now online" else "You are now offline")
                        },
                        onAdvanceOrder = { requestAdvance() },
                        onOpenDetails = { showDetails = true },
                        onAvatarClick = { scope.launch { drawerState.open() } },
                        onSosClick = { showSos = true }
                    )
                    1 -> OrdersScreen(activeOrder = currentOrder)
                    2 -> EarningsScreen(onOpen = { earningsScreen = it })
                    else -> ProfileScreen(onOpen = { riderScreen = it })
                }
            }
        }
    }

    incomingOffer?.let { offer ->
        key(offer.id) {
            IncomingOrderSheet(
                order = offer,
                onAccept = {
                    currentOrder = offer.copy(status = OrderStatus.ACCEPTED)
                    incomingOffer = null
                    toast.success("Order ${offer.id} accepted")
                },
                onReject = {
                    incomingOffer = null
                    toast.info("Order ${offer.id} rejected")
                },
                onTimeout = {
                    incomingOffer = null
                    toast.error("Order ${offer.id} missed")
                }
            )
        }
    }
}

@Composable
private fun HomeTab(
    online: Boolean,
    currentOrder: Order?,
    unreadCount: Int,
    onNotificationClick: () -> Unit,
    onOnlineChange: (Boolean) -> Unit,
    onAdvanceOrder: () -> Unit,
    onOpenDetails: () -> Unit,
    onAvatarClick: () -> Unit,
    onSosClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable { onAvatarClick() },
                contentAlignment = Alignment.Center
            ) { Text("\uD83E\uDDD1", fontSize = 24.sp) }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Hello, Rider \uD83D\uDC4B", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Ready to deliver today?", color = GreyText, fontSize = 13.sp)
            }
            Box(modifier = Modifier.size(44.dp)) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onNotificationClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notifications",
                        tint = DarkText,
                        modifier = Modifier.size(26.dp)
                    )
                }
                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-5).dp, y = 4.dp)
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (unreadCount > 9) "9+" else unreadCount.toString(),
                            color = Color.White,
                            fontSize = if (unreadCount > 9) 8.sp else 10.sp,
                            lineHeight = if (unreadCount > 9) 8.sp else 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = if (online) Green else Color(0xFF3A3A3A)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(
                        if (online) "You are Online" else "You are Offline",
                        color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        if (online) "Waiting for new orders..." else "Go online to receive orders",
                        color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp
                    )
                }
                Switch(
                    checked = online,
                    onCheckedChange = onOnlineChange,
                    colors = SwitchDefaults.colors(checkedTrackColor = Color.White, checkedThumbColor = Green)
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        Text("Today", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Earnings", "\u09F3 1,250", "\uD83D\uDCB0", Modifier.weight(1f))
            StatCard("Orders", "8", "\uD83D\uDCE6", Modifier.weight(1f))
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Online", "5h 20m", "\u23F1\uFE0F", Modifier.weight(1f))
            StatCard("Rating", "4.8 \u2B50", "\uD83C\uDFC5", Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))
        Text("Current order", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        if (currentOrder != null) {
            OrderCard(order = currentOrder, onAction = onAdvanceOrder, onClick = onOpenDetails)
        } else {
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("\uD83C\uDF54", fontSize = 40.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("No active order", fontWeight = FontWeight.SemiBold)
                    Text(
                        if (online) "New orders will pop up here" else "Turn on Online to get orders",
                        color = GreyText, fontSize = 13.sp
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        OutlinedButton(
            onClick = onSosClick,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD93025)),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) { Text("\uD83C\uDD98  Emergency / SOS", fontWeight = FontWeight.SemiBold) }
    }
}