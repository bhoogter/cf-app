package com.wincdspro.app.client

enum class StatusCodes(
    val desc: String,
    val code: Int
) {
    StC_CONTINUE(desc = "Continue", code = 100),
    StC_SWITCHING_PROTOCOLS(desc = "Switching Protocols", code = 101),
    StC_PROCESSING(desc = "Processing", code = 102),

    StC_OK(desc = "OK", code = 200),
    StC_CREATED(desc = "Created", code = 201),
    StC_ACCEPTED(desc = "Accepted", code = 202),
    StC_NON_AUTHORITATIVE_INFORMATION(desc = "Non Authoritative Information", code = 203),
    StC_NO_CONTENT(desc = "No Content", code = 204),
    StC_RESET_CONTENT(desc = "Reset Content", code = 205),
    StC_PARTIAL_CONTENT(desc = "Partial Content", code = 206),
    StC_MULTI_STATUS(desc = "Multi Status", code = 207),
    StC_ALREADY_REPORTED(desc = "Already Reported", code = 208),
    StC_IM_USED(desc = "Im Used", code = 226),

    StC_MULTIPLE_CHOICES(desc = "Multiple Choices", code = 300),
    StC_MOVED_PERMANENTLY(desc = "Moved Permanently", code = 301),
    StC_FOUND(desc = "Found", code = 302),
    StC_SEE_OTHER(desc = "See Other", code = 303),
    StC_NOT_MODIFIED(desc = "Not Modified", code = 304),
    StC_USE_PROXY(desc = "Use Proxy", code = 305),
    StC_TEMPORARY_REDIRECT(desc = "Temporary Redirect", code = 307),
    StC_PERMANENT_REDIRECT(desc = "Permanent Redirect", code = 308),

    StC_BAD_REQUEST(desc = "Bad Request", code = 400),
    StC_UNAUTHORIZED(desc = "Unauthorized", code = 401),
    StC_PAYMENT_REQUIRED(desc = "Payment Required", code = 402),
    StC_FORBIDDEN(desc = "Forbidden", code = 403),
    StC_NOT_FOUND(desc = "Not Found", code = 404),
    StC_METHOD_NOT_ALLOWED(desc = "Method Not Allowed", code = 405),
    StC_NOT_ACCEPTABLE(desc = "Not Acceptable", code = 406),
    StC_PROXY_AUTHENTICATION_REQUIRED(desc = "Proxy Authentication Required", code = 407),
    StC_REQUEST_TIMEOUT(desc = "Request Timeout", code = 408),
    StC_CONFLICT(desc = "Conflict", code = 409),
    StC_GONE(desc = "Gone", code = 410),
    StC_LENGTH_REQUIRED(desc = "Length Required", code = 411),
    StC_PRECONDITION_FAILED(desc = "Precondition Failed", code = 412),
    StC_PAYLOAD_TOO_LARGE(desc = "Payload Too Large", code = 413),
    StC_URI_TOO_LONG(desc = "URI Too Long", code = 414),
    StC_UNSUPPORTED_MEDIA_TYPE(desc = "Unsupported Media Type", code = 415),
    StC_RANGE_NOT_SATISFIABLE(desc = "Range Not Satisfiable", code = 416),
    StC_EXPECTATION_FAILED(desc = "Expectation Failed", code = 417),
    StC_I_AM_A_TEAPOT(desc = "I Am A Teapot", code = 418),
    StC_MISDIRECTED_REQUEST(desc = "Misdirected Request", code = 421),
    StC_UNPROCESSABLE_ENTITY(desc = "Unprocessable Entity", code = 422),
    StC_LOCKED(desc = "Locked", code = 423),
    StC_FAILED_DEPENDENCY(desc = "Failed Dependency", code = 424),
    StC_UPGRADE_REQUIRED(desc = "Upgrade Required", code = 426),
    StC_PRECONDITION_REQUIRED(desc = "Precondition Required", code = 428),
    StC_TOO_MANY_REQUESTS(desc = "Too Many Requests", code = 429),
    StC_REQUEST_HEADER_FIELDS_TOO_LARGE(desc = "RequestHeader Fields Too Large", code = 431),
    StC_UNAVAILABLE_FOR_LEGAL_REASONS(desc = "Unavailable For Legal Reasons", code = 451),

    StC_INTERNAL_SERVER_ERROR(desc = "Internal Server Error", code = 500),
    StC_NOT_IMPLEMENTED(desc = "Not Implemented", code = 501),
    StC_BAD_GATEWAY(desc = "Bad Gateway", code = 502),
    StC_SERVICE_UNAVAILABLE(desc = "Service Unavailable", code = 503),
    StC_GATEWAY_TIMEOUT(desc = "Gateway Timeout", code = 504),
    StC_HTTP_VERSION_NOT_SUPPORTED(desc = "HTTP Version Not Supported", code = 505),
    StC_VARIANT_ALSO_NEGOTIATES(desc = "Variant Also Negotiates", code = 506),
    StC_INSUFFICIENT_STORAGE(desc = "Insufficient Storage", code = 507),
    StC_LOOP_DETECTED(desc = "Loop Detected", code = 508),
    StC_NOT_EXTENDED(desc = "Not Extended", code = 510),
    StC_NETWORK_AUTHENTICATION_REQUIRED(desc = "Network Authentication Required", code = 511),

    StC_UNKNOWN(desc = "Unknown", code = 0)
    ;

    val is1xx get() = code in 100..199
    val is2xx get() = code in 200..299
    val is3xx get() = code in 300..399
    val is4xx get() = code in 400..499
    val is5xx get() = code in 500..599
}
