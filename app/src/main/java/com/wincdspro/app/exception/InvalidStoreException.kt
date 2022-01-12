package com.wincdspro.app.exception

class InvalidStoreException(msg: String? = null, ex: Exception? = null) : RuntimeException(msg, ex) {
    constructor(ex: java.lang.Exception) : this(null, ex)
}
