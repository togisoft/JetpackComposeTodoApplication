package com.togitech.todo.di

import android.app.Application
import androidx.room.Room
import com.togitech.todo.feature_todo.data.data_source.TodoDatabase
import com.togitech.todo.feature_todo.data.repository.TodoRepositoryImpl
import com.togitech.todo.feature_todo.domain.repository.TodoRepository
import com.togitech.todo.feature_todo.domain.use_case.*
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
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.toDoDao)
    }

    @Provides
    @Singleton
    fun provideTodoUseCases(repository: TodoRepository): TodoUseCases {
        return TodoUseCases(
            getTodos = GetTodos(repository),
            getTodo = GetTodo(repository),
            addTodo = AddTodo(repository),
            deleteTodo = DeleteTodo(repository),
            todoCompleted = CompletedTodo(repository)
        )
    }
}