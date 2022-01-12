package com.wincdspro.app.constant

class OperationNames {
    companion object {
        const val MAIN_MENU_START = "Start"
        const val MAIN_MENU_ORDER = "Order"
        const val MAIN_MENU_INVENT = "Inventory"
        const val MAIN_MENU_TRACKG = "Tracking"
        const val MAIN_MENU_ABOUTP = "About"
        const val MAIN_MENU_PERMCAMERA = "Permission"
        const val MAIN_MENU_DEBUG = "Debug"
        const val MAIN_MENU_HELP = "App Help"

        const val ORDER_OPERATION_VIEWSALE = "View Sale"
        const val ORDER_OPERATION_NEWSALE = "New Sale"

        const val ORDER_FRAGMENT_LOOKUP = "Find Sale"
        const val ORDER_FRAGMENT_LOOKUP_CUSTNO = "findsaleallownew"
        const val ORDER_FRAGMENT_VIEWSALE = "View Sale"
        const val ORDER_FRAGMENT_CUSTINFO = "Customer Info"
        const val ORDER_FRAGMENT_NEWSALEITEMS = "Enter New Sale Item"
        const val ORDER_FRAGMENT_ITEMSELECT = "Select Item"
        const val ORDER_FRAGMENT_NEWSALEFINISH = "Finish Sale"

        const val INV_OPERATION_VIEWITEM = "View Item"
        const val INV_OPERATION_VIEWITEMDETAIL = "View Item Detail"
        const val INV_OPERATION_PURCHASE_ORDERS = "POs"
        const val INV_OPERATION_WAREHOUSE = "Warehouse Trk"
        const val INV_OPERATION_PHYSICAL_INV = "Phys Invent"

        const val INV_FRAGMENT_LOOKUP = "Find Style"
        const val INV_FRAGMENT_VIEWITEM = "View Item"
        const val INV_FRAGMENT_VIEWITEMDETAIL = "View Item Detail"
        const val INV_FRAGMENT_PHYSICALINV = "Phys Invent"
        const val INV_FRAGMENT_PURCHASE_ORDERS = "POs"
        const val INV_FRAGMENT_PURCHASE_ORDERS_LOOKUP = "Find PO"

        const val TRK_OPERATION_PULLORDER = "Pull Orders"
        const val TRK_OPERATION_RECPO = "Rec POs"
        const val TRK_OPERATION_STKLOC = "Stk Loc"

        const val TRK_FRAGMENT_PULLORDER = "Pull Orders"
        const val TRK_FRAGMENT_RECPO = "Rec POs"
        const val TRK_FRAGMENT_STKLOC = "Stk Loc"

        const val RPT_OPERATION_DAILYAUDIT = "Daily Audit"
        const val RPT_OPERATION_RESULTS = "results"
        const val RPT_OPERATION_VIEW = "view"

        const val RPT_FRAGMENT_DAILYAUDIT = "Daily Audit"
        const val RPT_FRAGMENT_RESULTS = "results"
        const val RPT_FRAGMENT_VIEW = "view"
    }
}
