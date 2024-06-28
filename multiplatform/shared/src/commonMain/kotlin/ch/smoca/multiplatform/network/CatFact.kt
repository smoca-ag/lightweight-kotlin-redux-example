package ch.smoca.multiplatform.network

import kotlinx.serialization.Serializable

@Serializable
data class CatFact(
    val fact: String,
    val length: Int
)
