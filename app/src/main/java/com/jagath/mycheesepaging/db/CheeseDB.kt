package com.jagath.mycheesepaging.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cheese::class],version = 1,exportSchema = false)
abstract class CheeseDB:RoomDatabase() {
    abstract fun cheeseDao(): cheeseDao
}