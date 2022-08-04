package com.muchbeerat

import kotlinx.serialization.Serializable

@Serializable
data class ATMessageResponse(
    val SMSMessageData: SMSMessageData
)

@Serializable
data class SMSMessageData(
    val Message: String,
    val Recipients: List<Recipient>
)

@Serializable
data class Recipient(
    val cost: String,
    val messageId: String,
    val number: String,
    val status: String,
    val statusCode: Int

)