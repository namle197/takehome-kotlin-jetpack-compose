package com.namle197.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val mobileTakeHome: MobileTakeHomeDispatchers)

enum class MobileTakeHomeDispatchers {
    Default,
    IO,
}