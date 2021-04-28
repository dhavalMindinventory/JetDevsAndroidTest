package com.mi.imaginatoprac.data.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReplace(region: T)
}