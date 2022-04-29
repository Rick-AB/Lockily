package com.example.lockily.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lockily.data.local.entities.CredentialEntity

@Database(entities = [CredentialEntity::class], version = 1)
abstract class LockilyDatabase : RoomDatabase() {
    abstract val dao: Dao
}