package com.wincdspro.app.model.settings.general

import com.wincdspro.app.mapper.ToJson

data class StoreInfo(
    var refLoaded: Boolean? = null,
    var refFileDate: String? = null,
    var refFileSize: Long? = null,
    var refNextCheck: String? = null,

    var license: String? = null,

    var name: String? = null,
    var address: String? = null,
    var city: String? = null,
    var phone: String? = null,
    var commCode: Long? = null,
    var fabSeal: Double? = null,
    var salesTax: Double? = null,

    var storeShipToName: String? = null,
    var storeShipToAddr: String? = null,
    var storeShipToCity: String? = null,
    var storeShipToTele: String? = null,

    var bPOInvoicesLocation: Boolean? = null,
    var bDeliveryTaxable: Boolean? = null,
    var bLaborTaxable: Boolean? = null,
    var bPicturesOnTags: Boolean? = null,

    var tagJustify: String? = null,

    var bNoMerchandisePrice: Boolean? = null,
    var bNoListPriceOnTags: Boolean? = null,

    var calculateList: Long? = null,
    var printCopies: Long? = null,

    var loadedLicense: String? = null,

    var bPaymentBooksMonthly: Boolean? = null,

    var salesCopyID1: String? = null,
    var salesCopyID2: String? = null,
    var salesCopyID3: String? = null,
    var salesCopyID4: String? = null,

    var bDelIsCommissionable: Boolean? = null,
    var bLabIsCommissionable: Boolean? = null,
    var bNotIsCommissionable: Boolean? = null,

    var gracePeriod: Long? = null,
    var receivingLabels: String? = null,

    var bAPPost: Boolean? = null,
    var bBankManagerPost: Boolean? = null,

    var bPrintPoNoCost: Boolean? = null,

    var useCost: String? = null,

    var bPOSpecialInstr: Boolean? = null,

    var loadedInstallmentLicense: String? = null,

    var simpleInterestRate: Double? = null,
    var docFee: Double? = null,
    var lateChargePer: Double? = null,
    var maxLateCharge: Double? = null,

    var bPrintPaymentBooks: Boolean? = null,
    var bManualBillofSaleNo: Boolean? = null,
    var bShowRegularPrice: Boolean? = null,
    var bShowManufacturer: Boolean? = null,
    var bUseStoreCode: Boolean? = null,
    var bStyleNoInCode: Boolean? = null,
    var bCostInCode: Boolean? = null,

    var bShowAvailableStock: Boolean? = null,
    var bPrintBarCode: Boolean? = null,
    var bSellFromLoginLocation: Boolean? = null,

    var minLateCharge: Double? = null,

    var bPostToLoc1: Boolean? = null,

    var bRequireAdvertising: Boolean? = null,
    var bTagIncommingDistinct: Boolean? = null,
    var bUseCCMachine: Boolean? = null,
    var bStartMaximized: Boolean? = null,
    var bUseBTScanner: Boolean? = null,

    var poSpecInstr1: String? = null,
    var poSpecInstr2: String? = null,
    var poSpecInstr3: String? = null,
    var poSpecInstr4: String? = null,
    var delPercent: String? = null,

    var bUseCashRegisterAddress: Boolean? = null,
    var cashRegisterReceiptTailMessage: String? = null,

    var email: String? = null,
    var bUseQB: Boolean? = null,

    var bSeparateCommTables: Boolean? = null,
    var bOneCalendar: Boolean? = null,
    var equifaxAccountNo: String? = null,

    var bInstallmentInterestIsTaxable: Boolean? = null,
    var equifaxSecurityCode: String? = null,
    var bAPR: Boolean? = null,
    var transUnionAcctNo: String? = null,

    var bUseTimeWindows: Boolean? = null,

    var experianAcctNo: String? = null,
    var companyIdent: String? = null,

    var bJointLife: Boolean? = null,

    var ashleyID: String? = null,
    var ashleyUName: String? = null,
    var ashleyPWord: String? = null,
    var ashleyPath: String? = null,

    var ashleyATPExternalID: String? = null,
    var ashleyATPKeyCode: String? = null,
    var ashleyATPUserName: String? = null,
    var ashleyATPPassword: String? = null,
    var ashleyATPPICode: String? = null,
    var ashleyATPShipToID: String? = null,

    var bEmailLateChargeNotices: Boolean? = null,
    var bEmailMonthlyStatements: Boolean? = null,

    var bRequestEmail: Boolean? = null,

    var equifaxSiteID: String? = null,
    var equifaxPassword: String? = null,

    var cCProcessor: String? = null,
    var cCConfig: String? = null,

    var payPalUsername: String? = null,
    var payPalPassword: String? = null,
    var payPalSignature: String? = null,
    var payPalAuthKey: String? = null,

    var bShowPackageItemPrices: Boolean? = null,

    var cashDrawerConfig: String? = null,

    var serverLock: Boolean? = null,
    var modifiedRevolvingCharge: Boolean? = null,
    var modifiedRevolvingRate: Double? = null,
    var modifiedRevolvingAPR: Boolean? = null,
    var modifiedRevolvingSameAsCash: Long? = null,
    var modifiedRevolvingMinPmt: Double? = null,

    var amazonKeyID: String? = null,
    var amazonSecretKey: String? = null,
    var amazonUserName: String? = null,
    var amazonCustomerBucket: String? = null,
    var amazonPassword: String? = null,
    var amazonAWSPanelConfig: String? = null,
    var amazonQBPath: String? = null,
    var amazonMisc: String? = null,

    var dispatchTrackLicense: String? = null,
    var dispatchTrackServiceCode: String? = null,
    var dispatchTrackServiceAPI: String? = null,
    var dispatchTrackServiceURL: String? = null,

    var equifaxVendorIDCode: String? = null,

    var tRAXLicense: String? = null,
    var tRAXID: String? = null,

    var bInstallmentRoundUp: Boolean? = null,

    var bPostTermInterest: Boolean? = null,
    var postTermInterestRate: Double? = null,
) : ToJson()
