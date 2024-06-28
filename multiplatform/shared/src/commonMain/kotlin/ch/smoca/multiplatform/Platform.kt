package ch.smoca.multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform