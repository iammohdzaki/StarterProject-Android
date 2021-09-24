package com.starter.utils.extensions

/**
 * Safe String
 */
object SafeString {
    private const val EMPTY_STRING = ""
    /**
     * Method to assign Strings safely
     *
     * @param assignable  string
     * @param alternative string
     * @return string
     */
    fun assign(assignable: String?, alternative: String?): String {
        return if (assignable == null) alternative
            ?: EMPTY_STRING else if ("null" == assignable) assign(
            alternative
        ) else assignable
    }

    /**
     * Method to assign Strings safely
     *
     * @param assignable       string
     * @param filterAssignable string
     * @param alternative      string
     * @return string
     */
    fun assign(
        assignable: String?,
        filterAssignable: String,
        alternative: String?
    ): String {
        return if (assignable == null) alternative
            ?: EMPTY_STRING else if (assignable == filterAssignable) assign(
            alternative
        ) else assignable
    }

    /**
     * Method to assign strings Safely
     *
     * @param assignable assignable string
     * @return string
     */
    private fun assign(assignable: String?): String {
        return if (assignable == null || "[]".equals(
                assignable,
                ignoreCase = true
            )
        ) EMPTY_STRING else assignable
    }
}