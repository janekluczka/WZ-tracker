package com.example.wz_tracker.koin

import android.app.Application
import com.example.wz_tracker.database.WzDao
import com.example.wz_tracker.database.WzDatabase
import com.example.wz_tracker.repositories.WzRepositoryImpl
import com.example.wz_tracker.viewModels.MatchDetailsViewModel
import com.example.wz_tracker.viewModels.LifetimeStatsViewModel
import com.example.wz_tracker.viewModels.MatchesListViewModel
import com.example.wz_tracker.viewModels.UsernameViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): WzDao =
        WzDatabase.getInstance(application).wzDao

    single {
        provideDatabase(androidApplication())
    }
}

val repositoryModule = module {
    fun provideWzRepository(gamesDao: WzDao): WzRepositoryImpl = WzRepositoryImpl(gamesDao)

    single {
        provideWzRepository(get())
    }
}

val matchesListViewModelModule = module {
    viewModel {
        MatchesListViewModel(get())
    }
}

val matchDetailsViewModelModule = module {
    viewModel {
        MatchDetailsViewModel(get(), get())
    }
}

val lifetimeStatsViewModelModule = module {
    viewModel {
        LifetimeStatsViewModel(get())
    }
}

val usernameViewModelModule = module {
    viewModel {
        UsernameViewModel()
    }
}