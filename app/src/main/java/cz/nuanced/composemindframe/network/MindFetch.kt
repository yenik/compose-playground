package cz.nuanced.composemindframe.network

import kotlinx.serialization.Serializable

@Serializable
data class MindFetch(
    val body: String,
    val tags: List<String>,
    val url: String,
    val image: String?
)