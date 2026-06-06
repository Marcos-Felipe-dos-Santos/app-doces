package com.confeitaria.gestao.di

import android.content.Context
import androidx.room.Room
import com.confeitaria.gestao.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "confeitaria.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides fun provideClienteDao(db: AppDatabase) = db.clienteDao()
    @Provides fun provideEnderecoDao(db: AppDatabase) = db.enderecoDao()
    @Provides fun provideCategoriaDao(db: AppDatabase) = db.categoriaDao()
    @Provides fun provideProdutoDao(db: AppDatabase) = db.produtoDao()
    @Provides fun providePedidoDao(db: AppDatabase) = db.pedidoDao()
    @Provides fun provideItemPedidoDao(db: AppDatabase) = db.itemPedidoDao()
    @Provides fun providePagamentoDao(db: AppDatabase) = db.pagamentoDao()
}
