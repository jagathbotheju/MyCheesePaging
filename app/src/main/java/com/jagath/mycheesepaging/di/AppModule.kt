package com.jagath.mycheesepaging.di

import androidx.room.Room
import com.jagath.mycheesepaging.db.CheeseDB
import com.jagath.mycheesepaging.repository.CheeseRepository
import com.jagath.mycheesepaging.vm.CheeseViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule= module {

    single<CheeseDB> {
        Room.databaseBuilder(
            androidContext(),
            CheeseDB::class.java,
            "CheeseDB"
        ).build()
    }

    single<CheeseRepository> {
        CheeseRepository(get() as CheeseDB)
    }

    viewModel { CheeseViewModel(get() as CheeseRepository) }

}