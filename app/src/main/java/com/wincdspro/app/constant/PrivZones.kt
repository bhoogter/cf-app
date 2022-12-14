package com.wincdspro.app.constant

enum class PrivZones(val text: String, val parent: PrivZones? = null, val privId: Int) {
    PROGRAM_ADMINISTRATION("Program Administration", null, 1),
    PROGRAM_ADMINISTRATION_STORE_SETUP("  Store Setup", PROGRAM_ADMINISTRATION, 2),
    PROGRAM_ADMINISTRATION_BACKUP__RESTORE("  Backup/Restore", PROGRAM_ADMINISTRATION, 3),
    PROGRAM_ADMINISTRATION_ANNUAL_MAINTENANCE("  Annual Maintenance", PROGRAM_ADMINISTRATION, 4),
    PROGRAM_ADMINISTRATION_LOG_IN_TO_OTHER_STORES("  Log In To Other Stores", PROGRAM_ADMINISTRATION, 33),
    CUSTOMER_MANAGEMENT("Customer Management", null, 5),
    CUSTOMER_MANAGEMENT_CREATE_SALES("  Create Sales", CUSTOMER_MANAGEMENT, 6),
    CUSTOMER_MANAGEMENT_VIEW_SALES("  View Sales", CUSTOMER_MANAGEMENT, 7),
    CUSTOMER_MANAGEMENT_ADJUST_SALES("  Adjust Sales", CUSTOMER_MANAGEMENT, 8),
    CUSTOMER_MANAGEMENT_ORDER_STATUS("  Order Status", CUSTOMER_MANAGEMENT, 37),
    CUSTOMER_MANAGEMENT_GIVE_DISCOUNTS("  Give Discounts", CUSTOMER_MANAGEMENT, 30),
    CUSTOMER_MANAGEMENT_PREVENT_PRICE_ADJUST("  Prevent Price Adjust", CUSTOMER_MANAGEMENT, 36),
    CUSTOMER_MANAGEMENT_DELIVER_SALES("  Deliver Sales", CUSTOMER_MANAGEMENT, 9),
    CUSTOMER_MANAGEMENT_VOID_SALES("  Void Sales", CUSTOMER_MANAGEMENT, 10),
    CUSTOMER_MANAGEMENT_SERVICE_ORDERS("  Service Orders", CUSTOMER_MANAGEMENT, 25),
    CUSTOMER_MANAGEMENT_SALES_REPORTS("  Sales Reports", CUSTOMER_MANAGEMENT, 11),
    INVENTORY_MANAGEMENT("Inventory Management", null, 12),
    INVENTORY_MANAGEMENT_CREATE_AND_EDIT_ITEMS("  Create and Edit Items", INVENTORY_MANAGEMENT, 13),
    INVENTORY_MANAGEMENT_CHANGE_ITEM_PRICES("  Change Item Prices", INVENTORY_MANAGEMENT, 14),
    INVENTORY_MANAGEMENT_FACTORY_SHIPMENTS("  Factory Shipments", INVENTORY_MANAGEMENT, 31),
    INVENTORY_MANAGEMENT_STORE_TRANSFERS("  Store Transfers", INVENTORY_MANAGEMENT, 32),
    INVENTORY_MANAGEMENT_CHANGE_STOCK_QUANTITIES("  Change Stock Quantities", INVENTORY_MANAGEMENT, 15),
    INVENTORY_MANAGEMENT_VIEW_STOCK_QUANTITIES("  View Stock Quantities", INVENTORY_MANAGEMENT, 16),
    INVENTORY_MANAGEMENT_VIEW_COST_AND_GROSS_MARGIN("  View Cost and Gross Margin", INVENTORY_MANAGEMENT, 17),
    INVENTORY_MANAGEMENT_VIEW_LANDED_COST("  View Landed Cost", INVENTORY_MANAGEMENT, 38),
    INVENTORY_MANAGEMENT_MANAGE_PURCHASE_ORDERS("  Manage Purchase Orders", INVENTORY_MANAGEMENT, 18),
    INVENTORY_MANAGEMENT_SCHEDULE_DELIVERIES("  Schedule Deliveries", INVENTORY_MANAGEMENT, 24),
    INVENTORY_MANAGEMENT_VIEW_INVENTORY_REPORTS("  View Inventory Reports", INVENTORY_MANAGEMENT, 19),
    FINANCIAL_MANAGEMENT("Financial Management", null, 20),
    FINANCIAL_MANAGEMENT_ACCEPT_PAYMENTS("  Accept Payments", FINANCIAL_MANAGEMENT, 21),
    FINANCIAL_MANAGEMENT_CASH_DRAWER("  Cash Drawer", FINANCIAL_MANAGEMENT, 28),
    FINANCIAL_MANAGEMENT_CHANGE_PAYMENT_DATES("  Change Payment Dates", FINANCIAL_MANAGEMENT, 26),
    FINANCIAL_MANAGEMENT_CHANGE_SALE_DATE("  Change Sale Date", FINANCIAL_MANAGEMENT, 34),
    FINANCIAL_MANAGEMENT_FORFEIT_DEPOSITS("  Forfeit Deposits", FINANCIAL_MANAGEMENT, 27),
    FINANCIAL_MANAGEMENT_CREDIT_ADMINISTRATION("  Credit Administration", FINANCIAL_MANAGEMENT, 22),
    FINANCIAL_MANAGEMENT_STORE_FINANCES("  Store Finances", FINANCIAL_MANAGEMENT, 23),
    FINANCIAL_MANAGEMENT_COMMISSIONS("  Commissions", FINANCIAL_MANAGEMENT, 35),
    FINANCIAL_MANAGEMENT_DAILY_AUDIT_REPORT("  Daily Audit Report", FINANCIAL_MANAGEMENT, 29),
}
