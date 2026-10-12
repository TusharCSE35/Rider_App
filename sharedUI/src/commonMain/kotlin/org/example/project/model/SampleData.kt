package org.example.project.model

object SampleData {
    val incomingOrder = Order(
        id = "#1042",
        restaurant = "Star Kabab & Restaurant",
        customer = "Rahim Uddin",
        pickupAddress = "Dhanmondi 27, Dhaka",
        dropAddress = "House 12, Road 5, Mohammadpur",
        items = 3,
        distanceKm = 3.4,
        earning = 85,
        time = "Now",
        status = OrderStatus.ACCEPTED,
        itemList = listOf(
            OrderItem("Chicken Seekh Kabab", 2, 180),
            OrderItem("Butter Naan", 4, 25),
            OrderItem("Mint Lemonade", 1, 60)
        ),
        paymentType = PaymentType.COD,
        collectAmount = 580,
        customerPhone = "+8801711000000",
        restaurantPhone = "+8801811000000",
        customerNote = "Please call when you reach the gate. Extra spicy.",
        pickupLat = 23.7561,
        pickupLng = 90.3733,
        dropLat = 23.7662,
        dropLng = 90.3589,
        pickupCode = "4821",
        deliveryOtp = "1234"
    )

    val orders = listOf(
        Order("#1041", "Pizza Roma", "Karim Hossain", "Gulshan 1", "Banani Block C", 2, 4.1, 110, "Today, 1:10 PM", OrderStatus.DELIVERED),
        Order("#1040", "Burger Hub", "Sadia Islam", "Uttara Sector 7", "Uttara Sector 11", 4, 2.2, 70, "Today, 12:20 PM", OrderStatus.DELIVERED),
        Order("#1039", "Biryani House", "Nasir Ahmed", "Mirpur 10", "Mirpur 12", 1, 1.8, 60, "Today, 11:05 AM", OrderStatus.CANCELLED),
        Order("#1038", "Sushi Zone", "Tania Akter", "Banani 11", "Gulshan 2", 2, 3.0, 95, "Yesterday, 8:40 PM", OrderStatus.DELIVERED),
        Order("#1037", "Chai & Snacks", "Mamun Khan", "Farmgate", "Tejgaon", 5, 1.5, 55, "Yesterday, 6:15 PM", OrderStatus.DELIVERED),
        Order("#1036", "Kacchi Bhai", "Rony Das", "Old Dhaka", "Motijheel", 2, 5.2, 130, "Yesterday, 2:30 PM", OrderStatus.CANCELLED)
    )

    val week = listOf(
        DayEarning("Sat", 980, 7), DayEarning("Sun", 1420, 10), DayEarning("Mon", 760, 5),
        DayEarning("Tue", 1180, 8), DayEarning("Wed", 1530, 11), DayEarning("Thu", 1250, 8),
        DayEarning("Fri", 1890, 13)
    )

    // This month = 23 earlier days + current week (last 7 days), so week totals stay consistent.
    val month = listOf(
        DayEarning("Sep 13", 1120, 8), DayEarning("Sep 14", 1350, 9), DayEarning("Sep 15", 890, 6),
        DayEarning("Sep 16", 1640, 12), DayEarning("Sep 17", 1020, 7), DayEarning("Sep 18", 1490, 10),
        DayEarning("Sep 19", 870, 6), DayEarning("Sep 20", 1310, 9), DayEarning("Sep 21", 990, 7),
        DayEarning("Sep 22", 1580, 11), DayEarning("Sep 23", 1090, 8), DayEarning("Sep 24", 1420, 10),
        DayEarning("Sep 25", 940, 6), DayEarning("Sep 26", 1710, 12), DayEarning("Sep 27", 1180, 8),
        DayEarning("Sep 28", 1290, 9), DayEarning("Sep 29", 1050, 7), DayEarning("Sep 30", 1470, 10),
        DayEarning("Oct 1", 920, 6), DayEarning("Oct 2", 1610, 11), DayEarning("Oct 3", 1140, 8),
        DayEarning("Oct 4", 1380, 9), DayEarning("Oct 5", 990, 7)
    ) + week

    const val walletBalance = 3450

    val cashSummary = CashSummary(
        cashInHand = 1840,
        limit = 3000,
        entries = listOf(
            CashEntry("#1041", 620, "Today, 1:10 PM"),
            CashEntry("#1040", 450, "Today, 12:20 PM"),
            CashEntry("#1038", 770, "Yesterday, 8:40 PM")
        )
    )

    val transactions = listOf(
        Transaction("T9", "Order #1041", "Today, 1:10 PM", 110, TransactionType.EARNING),
        Transaction("T8", "Tip from Karim", "Today, 1:10 PM", 20, TransactionType.TIP),
        Transaction("T7", "Order #1040", "Today, 12:20 PM", 70, TransactionType.EARNING),
        Transaction("T6", "Peak hour bonus", "Yesterday", 150, TransactionType.BONUS),
        Transaction("T5", "Late delivery penalty", "Yesterday", -30, TransactionType.PENALTY),
        Transaction("T4", "Withdraw to bKash", "Thu", -1500, TransactionType.PAYOUT),
        Transaction("T3", "Tip from Tania", "Thu", 30, TransactionType.TIP),
        Transaction("T2", "Order #1037", "Thu", 55, TransactionType.EARNING),
        Transaction("T1", "Withdraw to Nagad", "Tue", -1000, TransactionType.PAYOUT)
    )

    val incentives = listOf(
        Incentive("Daily Target", "Complete 10 orders today", 8, 10, 150),
        Incentive("Weekend Rush", "Complete 15 orders on Friday and Saturday", 9, 15, 300),
        Incentive("5-Star Streak", "Receive 5-star ratings on your next 5 orders", 5, 5, 100)
    )

    val shifts = listOf(
        Shift("S1", "Morning", "7:00 AM - 11:00 AM"),
        Shift("S2", "Lunch peak", "11:30 AM - 3:00 PM", isPeak = true, bonus = 100),
        Shift("S3", "Evening", "3:00 PM - 6:30 PM"),
        Shift("S4", "Dinner peak", "7:00 PM - 11:00 PM", isPeak = true, bonus = 150),
        Shift("S5", "Late night", "11:00 PM - 2:00 AM", bonus = 80)
    )

    val hotZones = listOf(
        HotZone("Dhanmondi", 0.92f, 24),
        HotZone("Gulshan", 0.81f, 19),
        HotZone("Banani", 0.67f, 14),
        HotZone("Uttara", 0.45f, 9),
        HotZone("Mirpur", 0.30f, 6)
    )

    val reviews = listOf(
        Review("Rahim Uddin", 5, "Very fast delivery, and the food was hot.", "Today"),
        Review("Karim Hossain", 5, "Polite rider, thanks!", "Today"),
        Review("Sadia Islam", 4, "Good, but the delivery was a little late.", "Yesterday"),
        Review("Nasir Ahmed", 3, "It took some time to find the address.", "Thu"),
        Review("Tania Akter", 5, "Perfect.", "Thu")
    )
    val performance = PerformanceStats(
        acceptanceRate = 92,
        onTimeRate = 88,
        cancellationRate = 4,
        completed = 156,
        rating = 4.8
    )

    val badges = listOf(
        Badge("\uD83D\uDE80", "Speedster", "10 on-time deliveries", true),
        Badge("\u2B50", "5-Star Rider", "10 five-star reviews", true),
        Badge("\uD83D\uDD25", "7-Day Streak", "Stay online for 7 consecutive days", false),
        Badge("\uD83C\uDFC6", "100 Club", "Complete 100 orders", true),
        Badge("\uD83C\uDF19", "Night Owl", "Complete 20 nighttime orders", false),
        Badge("\uD83D\uDCAF", "Zero Cancel", "No cancellations for one month", false)
    )

    val leaderboard = listOf(
        LeaderboardEntry(1, "Sohel R.", 74),
        LeaderboardEntry(2, "Jamal H.", 68),
        LeaderboardEntry(3, "Tushar S.", 61, isMe = true),
        LeaderboardEntry(4, "Imran K.", 57),
        LeaderboardEntry(5, "Rafiq M.", 52),
        LeaderboardEntry(6, "Babul D.", 47)
    )

    val documents = listOf(
        RiderDocument("NID (front & back)", DocStatus.VERIFIED),
        RiderDocument("Driving license", DocStatus.PENDING),
        RiderDocument("Vehicle registration", DocStatus.REJECTED),
        RiderDocument("Profile photo", DocStatus.MISSING)
    )
}