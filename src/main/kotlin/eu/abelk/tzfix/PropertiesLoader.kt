package eu.abelk.tzfix

import java.util.*

object PropertiesLoader {
    fun loadProperties(): Properties {
        val props = Properties()
        val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("config.properties")
            ?: throw IllegalArgumentException("Properties file not found")
        props.load(inputStream)
        return props
    }
}
