package com.wincdspro.app.test

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.wincds.collection.PoNoCollection
import com.wincdspro.app.model.wincds.collection.SearchItemCollection
import com.wincdspro.app.model.wincds.collection.SearchSaleCollection
import com.wincdspro.app.model.wincds.core.GrossMargin
import com.wincdspro.app.model.wincds.core.Item
import com.wincdspro.app.model.wincds.core.ItemDetail
import com.wincdspro.app.model.wincds.core.Mail
import com.wincdspro.app.model.wincds.core.Po
import com.wincdspro.app.model.wincds.core.PoItem
import com.wincdspro.app.model.wincds.core.Sale
import com.wincdspro.app.model.wincds.core.collection.ItemDetailCollection
import com.wincdspro.app.model.wincds.report.ReportGenerationResponse
import com.wincdspro.app.model.wincds.search.SearchItem
import com.wincdspro.app.model.wincds.search.SearchSale
import com.wincdspro.app.util.Format
import java.util.Date
import java.util.UUID

class WincdsClientTestResponses {
    companion object {
        private fun <T> setAndReturn(path: String, c: Class<T>, o: ToJson): T where T : ToJson {
            FakeNetworkInterceptor.responses[path] = o.toJson()
            return o as T
        }

        private fun setAndReturn(path: String, s: String): String {
            FakeNetworkInterceptor.responses[path] = s
            return s
        }

        fun defaultSetup() {
            verify()
            help()
            connectionTest()

            salesByDate()
            saleByLeaseNo10348()
            saleNoList()

            customerByMailIndex()

            salesByLeaseNoPartial()
            salesByLeaseNo10348()
            salesByLastNameCARTER()
            itemsByStyle6000_()
            itemsByStyle55()

            itemByStyle6000_066()
            itemDetailByStyle6000_066()

            poList()
            poByPoNo2020()

            reportGenerateUnknown()
        }

        // //////////////////////////////////////////////////
        // Connection API
        fun verify() = setAndReturn("/api/v1/verify", "Ok")
        fun help() = setAndReturn("/api/v1/help", "Ok")
        fun connectionTest() = setAndReturn("/api/v1/connectionTest", "Ok")

        // //////////////////////////////////////////////////
        // Sale API
        fun saleNoList() = setAndReturn(
            "/api/v1/l/1/sale", SearchSaleCollection::class.java,
            SearchSaleCollection(
                results = listOf()
            )
        )

        fun saleByLeaseNo10348() = setAndReturn(
            "/api/v1/l/1/sale/10348", Sale::class.java,
            Sale(
                leaseNo = "10348",
                index = 20,
                sale = 100.0,
                deposit = 10.0,
                items = listOf(
                    GrossMargin(1, "10348", 1.0, "6000-066", "VIRGINIA HOUSE", "DESCRIPTION", "", "0", "900", "", 10.0, 0.0, 699.00, "", "st", 1, "", null, 76),
                    GrossMargin(2, "10348", 0.0, "SUB", "TOTAL: ---", "", "", "0", "900", "", 00.0, 0.0, 699.00, "", "st", 1, "", null, 76),
                    GrossMargin(3, "10348", 0.0, "TAX", "SALES TAX", "", "", "0", "900", "", 0.0, 0.0, 45.00, "", "st", 1, "", null, 76),
                )
            )
        )

        // //////////////////////////////////////////////////
        // Customer API
        fun customerByMailIndex() = setAndReturn(
            "/api/v1/l/1/customer/20", Mail::class.java,
            Mail(
                index = 20,
                last = "CARTER",
                first = "JOHN",
            )
        )

        // //////////////////////////////////////////////////
        // Search API
        fun salesByDate() = setAndReturn("/api/v1/l/1/find/sale/date/" + Format.dateUrl(), "{\"results\":[]}")
        fun salesByLeaseNoPartial() = setAndReturn(
            "/api/v1/l/1/find/sale/saleno/1034", SearchSaleCollection::class.java,
            SearchSaleCollection(
                results = listOf(
                    SearchSale(leaseNo = "10340", last = "Sale 1", first = "-"),
                    SearchSale(leaseNo = "10341", last = "Sale 2", first = "-"),
                    SearchSale(leaseNo = "10342", last = "Sale 3", first = "-"),
                    SearchSale(leaseNo = "10348", last = "Sale 8", first = "-"),
                    SearchSale(leaseNo = "10349", last = "Sale 9", first = "-"),
                )
            )
        )

        fun salesByLeaseNo10348() = setAndReturn(
            "/api/v1/l/1/find/sale/saleno/10348", SearchSaleCollection::class.java,
            SearchSaleCollection(
                results = listOf(
                    SearchSale(leaseNo = "10348", last = "Sale 8", first = "-"),
                )
            )
        )

        fun itemsByStyle6000_() = setAndReturn(
            "/api/v1/l/1/find/item/style/6000-", SearchItemCollection::class.java,
            SearchItemCollection(
                results = listOf(
                    SearchItem(rn = 1, style = "6000-066", vendor = "VIRGINIA HOUSE", vendorNo = "990"),
                    SearchItem(rn = 2, style = "6000-085", vendor = "VIRGINIA HOUSE", vendorNo = "990"),
                    SearchItem(rn = 3, style = "6000-099", vendor = "VIRGINIA HOUSE", vendorNo = "990"),
                )
            )
        )

        fun itemsByStyle55() = setAndReturn(
            "/api/v1/l/1/find/item/style/55-", SearchItemCollection::class.java,
            SearchItemCollection(
                results = listOf(
                    SearchItem(rn = 1, style = "557", vendor = "ASHLEY", vendorNo = "980"),
                    SearchItem(rn = 2, style = "558", vendor = "ASHLEY HOUSE", vendorNo = "980"),
                )
            )
        )

        fun salesByLastNameCARTER() = setAndReturn(
            "/api/v1/l/1/find/sale/name/CARTER", SearchSaleCollection::class.java,
            SearchSaleCollection(
                results = listOf(
                    SearchSale(leaseNo = "10348", index = 20, last = "CARTER", first = "JOHN", tele = "5554443333", city = "AnywhereVille, OH", status = "O"),
                    SearchSale(leaseNo = "10345", index = 20, last = "CARTER", first = "JOHN", tele = "5554443333", city = "AnywhereVille, OH", status = "C"),
                )
            )
        )

        // //////////////////////////////////////////////////
        // Inventory API
        fun itemByStyle6000_066() = setAndReturn(
            "/api/v1/inventory/6000-066", Item::class.java,
            Item(
                rn = 1,
                style = "6000-066",
                vendorNo = "090",
                vendor = "VIRGINIA HOUSE",
                desc = "Desk",
                cost = 50.0,
                onSale = 699.00,
            )
        )

        fun itemDetailByStyle6000_066() = setAndReturn(
            "/api/v1/inventory/detail/6000-066", ItemDetailCollection::class.java,
            ItemDetailCollection(
                results = listOf(
                    ItemDetail(detailID = 1, style = "6000-066"),
                    ItemDetail(detailID = 2, style = "6000-066"),
                )
            )
        )

        // //////////////////////////////////////////////////
        // Inventory API
        fun poList() = setAndReturn(
            "/api/v1/l/1/po", PoNoCollection::class.java,
            PoNoCollection(
                results = listOf(
                    2016, 2020, 2021, 2022, 2023
                )
            )
        )

        fun poByPoNo2020() = setAndReturn(
            "/api/v1/l/1/po/2020", Po::class.java,
            Po(
                poNo = "2020",
                vendors = listOf("VIRGINIA HOUSE"),
                poDates = listOf("2020-02-02"),
                items = listOf(
                    PoItem(
                        pOID = 1,
                        style = "6000-066",
                        poNo = 2020,
                        leaseNo = "10348",
                        poDate = "2020-02-02",
                        initialQuantity = 2.0,
                        vendor = "VIRGINIA HOUSE",
                        quantity = 1.0,
                        desc = "Desk...",
                        cost = 250.0
                    )
                )
            )
        )

        // //////////////////////////////////////////////////
        // Report API
        fun reportGenerateUnknown() = setAndReturn(
            "/api/v1/l/1/report/generate/unknown", ReportGenerationResponse::class.java,
            ReportGenerationResponse(
                result = true,
                date = Date(),
                report = "unknown",
                reportName = "",
                resultId = UUID.randomUUID().toString(),
                ready = false,
            )
        )
    }
}
