package com.mi.imaginatoprac.data.account.respository

import androidx.room.Dao
import androidx.room.Query
import com.mi.imaginatoprac.data.base.BaseDao
import com.mi.imaginatoprac.domain.account.entity.COLUMN_USER_ID
import com.mi.imaginatoprac.domain.account.entity.TABLE_NAME
import com.mi.imaginatoprac.domain.account.entity.User

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_USER_ID = :id")
    fun getUser(id: String): User

}
