package com.jagath.mycheesepaging.repository

import com.jagath.mycheesepaging.db.Cheese
import com.jagath.mycheesepaging.db.CheeseDB
import com.jagath.mycheesepaging.utils.ioThread
import com.jagath.mycheesepaging.utils.CHEESE_DATA

class CheeseRepository(val cheeseDB: CheeseDB) {

    fun populateDB(){
        ioThread {
            cheeseDB.cheeseDao().insert(
                CHEESE_DATA.map { Cheese(id = 0, name = it) }
            )
        }
    }

    fun getAllCheese()=cheeseDB.cheeseDao().allCheesesByName()

    fun insert(text:CharSequence)= ioThread {
        cheeseDB.cheeseDao().insert(Cheese(id = 0, name = text.toString()))
    }

    fun remove(cheese: Cheese)= ioThread {
        cheeseDB.cheeseDao().delete(cheese)
    }
}