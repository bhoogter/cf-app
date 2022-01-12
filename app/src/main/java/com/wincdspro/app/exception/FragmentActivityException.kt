package com.wincdspro.app.exception

class FragmentActivityException(msg: String? = null, ex: Exception? = null) : RuntimeException(msg, ex) {
    constructor(ex: java.lang.Exception) : this(null, ex)
}
