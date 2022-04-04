package com.example.weatherapp

import android.database.Cursor
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import kotlin.math.roundToLong

typealias UlipBi = MutableList<MutableMap<Int, MutableMap<String, String>>>

typealias TraditionalBi = MutableMap<Int, MutableMap<String, String>>

fun Double.round(precision: Int): Double {
    var precisionVal = 1.0
    for (i in 1..precision) {
        precisionVal *= 10.0
    }
    return (this * precisionVal).roundToLong() / precisionVal
}


fun Cursor.getBoolean(columnIndex: Int): Boolean = this.getInt(columnIndex) == 1

fun Cursor.getIntOrNull(columnName: String): Int? = this.getIntOrNull(this.getColumnIndex(columnName.trim()))

fun Cursor.getDoubleOrNull(columnName: String): Double? = this.getDoubleOrNull(this.getColumnIndex(columnName.trim()))

fun Cursor.getStringOrNull(columnName: String): String? = this.getStringOrNull(this.getColumnIndex(columnName.trim()))


fun Cursor.getIntegerList(columnName: String): List<Int> {
    val commaSeparatedValue = getStringOrNull(columnName)
    val commaSeparatedValueStrList = commaSeparatedValue
            ?.split(',')
    val valueList = mutableListOf<Int>()
    commaSeparatedValueStrList?.forEach { value ->
        if (!value.isNullOrBlank() && !value.isNullOrEmpty()) {
            valueList.add(value.toInt())
        }
    }
    return valueList
}



fun <T> MutableLiveData<T>.removeObserversIfAny(owner: LifecycleOwner) {
    if (this.hasObservers()) {
        this.hasActiveObservers()
        this.removeObservers(owner)
    }
}

fun Map<Int, Map<String, String>>.toCsv(): String {
    val csvBuilder = StringBuilder()

    // add headers
    for ((_, monthData) in this.entries) {
        for ((keywords) in monthData.entries) {
            csvBuilder.append(keywords).append(",")
        }
        break
    }

    csvBuilder.append("\n")

    // add values
    for ((_, monthData) in this.entries) {
        for ((_, value) in monthData.entries) {
            csvBuilder.append(value).append(",")
        }
        csvBuilder.append("\n")
    }

    return csvBuilder.toString()
}

fun String.remove(removeValue: String): String {
    return this.replace(removeValue, "")
}

fun <K, V> MutableMap<K, V>.clearAndPutAll(from: Map<out K, V>) {
    this.clear()
    this.putAll(from)
}

fun <E> MutableList<E>.clearAndAddAll(elements: Collection<E>): Boolean {
    this.clear()
    return this.addAll(elements)
}

fun Cursor?.isNullOrEmpty() = this == null || this.count == 0

fun Cursor?.isEmpty() = this?.count == 0

fun Cursor?.isNotEmpty() = !this.isEmpty()


fun Boolean?.dbValue() = if (this == true) 1 else 0

