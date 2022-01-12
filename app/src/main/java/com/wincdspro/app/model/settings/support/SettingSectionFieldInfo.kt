package com.wincdspro.app.model.settings.support

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingSectionFieldInfo(
    val name: String,
    val def: String = "",
)
