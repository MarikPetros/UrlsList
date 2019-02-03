package com.example.marik.urlslist.model

/**
 *  RequestResult holding url's availability and response time from network request
 */

data class RequestResult(
    var isAvailable: Boolean = false,
    var responseTime: Long = Long.MAX_VALUE
)
