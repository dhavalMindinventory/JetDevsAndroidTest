package com.mi.imaginatoprac.domain.account.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME = "User"
const val COLUMN_USER_ID = "user_id"
const val COLUMN_X_ACC = "x_acc"
const val COLUMN_USER_NAME = "user_name"

@Entity(tableName = TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_USER_ID)
    val userId: String,
    @ColumnInfo(name = COLUMN_X_ACC)
    val authHeader: String,
    @ColumnInfo(name = COLUMN_USER_NAME)
    val userName: String
)