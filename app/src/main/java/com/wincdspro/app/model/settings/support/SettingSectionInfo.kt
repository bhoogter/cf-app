package com.wincdspro.app.model.settings.support

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingSectionInfo(
    val key: String
)
