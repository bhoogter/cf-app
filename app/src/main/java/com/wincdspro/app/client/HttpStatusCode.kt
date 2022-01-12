package com.wincdspro.app.client

class HttpStatusCode {
    companion object {
        const val SC_CONTINUE: Int = 100
        const val SC_SWITCHING_PROTOCOLS: Int = 101
        const val SC_PROCESSING: Int = 102

        const val SC_OK: Int = 200
        const val SC_CREATED: Int = 201
        const val SC_ACCEPTED: Int = 202
        const val SC_NON_AUTHORITATIVE_INFORMATION: Int = 203
        const val SC_NO_CONTENT: Int = 204
        const val SC_RESET_CONTENT: Int = 205
        const val SC_PARTIAL_CONTENT: Int = 206
        const val SC_MULTI_STATUS: Int = 207
        const val SC_ALREADY_REPORTED: Int = 208
        const val SC_IM_USED: Int = 226

        const val SC_MULTIPLE_CHOICES: Int = 300
        const val SC_MOVED_PERMANENTLY: Int = 301
        const val SC_FOUND: Int = 302
        const val SC_SEE_OTHER: Int = 303
        const val SC_NOT_MODIFIED: Int = 304
        const val SC_USE_PROXY: Int = 305
        const val SC_TEMPORARY_REDIRECT: Int = 307
        const val SC_PERMANENT_REDIRECT: Int = 308

        const val SC_BAD_REQUEST: Int = 400
        const val SC_UNAUTHORIZED: Int = 401
        const val SC_PAYMENT_REQUIRED: Int = 402
        const val SC_FORBIDDEN: Int = 403
        const val SC_NOT_FOUND: Int = 404
        const val SC_METHOD_NOT_ALLOWED: Int = 405
        const val SC_NOT_ACCEPTABLE: Int = 406
        const val SC_PROXY_AUTHENTICATION_REQUIRED: Int = 407
        const val SC_REQUEST_TIMEOUT: Int = 408
        const val SC_CONFLICT: Int = 409
        const val SC_GONE: Int = 410
        const val SC_LENGTH_REQUIRED: Int = 411
        const val SC_PRECONDITION_FAILED: Int = 412
        const val SC_PAYLOAD_TOO_LARGE: Int = 413
        const val SC_URI_TOO_LONG: Int = 414
        const val SC_UNSUPPORTED_MEDIA_TYPE: Int = 415
        const val SC_RANGE_NOT_SATISFIABLE: Int = 416
        const val SC_EXPECTATION_FAILED: Int = 417
        const val SC_I_AM_A_TEAPOT: Int = 418
        const val SC_MISDIRECTED_REQUEST: Int = 421
        const val SC_UNPROCESSABLE_ENTITY: Int = 422
        const val SC_LOCKED: Int = 423
        const val SC_FAILED_DEPENDENCY: Int = 424
        const val SC_UPGRADE_REQUIRED: Int = 426
        const val SC_PRECONDITION_REQUIRED: Int = 428
        const val SC_TOO_MANY_REQUESTS: Int = 429
        const val SC_REQUEST_HEADER_FIELDS_TOO_LARGE: Int = 431
        const val SC_UNAVAILABLE_FOR_LEGAL_REASONS: Int = 451

        const val SC_INTERNAL_SERVER_ERROR: Int = 500
        const val SC_NOT_IMPLEMENTED: Int = 501
        const val SC_BAD_GATEWAY: Int = 502
        const val SC_SERVICE_UNAVAILABLE: Int = 503
        const val SC_GATEWAY_TIMEOUT: Int = 504
        const val SC_HTTP_VERSION_NOT_SUPPORTED: Int = 505
        const val SC_VARIANT_ALSO_NEGOTIATES: Int = 506
        const val SC_INSUFFICIENT_STORAGE: Int = 507
        const val SC_LOOP_DETECTED: Int = 508
        const val SC_NOT_EXTENDED: Int = 510
        const val SC_NETWORK_AUTHENTICATION_REQUIRED: Int = 511

        const val SC_UNKNOWN: Int = 0
    }
}
