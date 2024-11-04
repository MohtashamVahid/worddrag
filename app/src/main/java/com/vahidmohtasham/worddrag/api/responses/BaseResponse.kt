package com.vahidmohtasham.worddrag.api.responses

open class BaseResponse(
    open val success: Boolean? = null,
    open val message: String? = null,
    open val error: String? = null
) {
    open fun hasMessage(): Boolean {
        return !message.isNullOrEmpty()
    }

    open fun hasErrors(): Boolean {
        return !error.isNullOrEmpty()
    }

    open fun getMessageOrError(): String? {
        if (hasErrors())
            return error
        if (hasMessage())
            return message
        return "خطا"
    }
}

open class BaseRequest {
}
