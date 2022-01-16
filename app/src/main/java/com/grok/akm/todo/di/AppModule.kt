package com.grok.akm.todo.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grok.akm.todo.data.ToDoDatabase
import com.grok.akm.todo.data.ToDoRepository
import com.grok.akm.todo.data.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application) : ToDoDatabase{
        return Room.databaseBuilder(
            app,
            ToDoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: ToDoDatabase) : ToDoRepository {
        return ToDoRepositoryImpl(database.toDoDao)
    }
}