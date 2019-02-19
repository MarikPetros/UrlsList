package com.example.marik.urlslist.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "itemurl")
data class ItemUrl(
    val name: String,
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var isAvailable: Boolean? = null,
    var responseTime: Long = Long.MAX_VALUE
)