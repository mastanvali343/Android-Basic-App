package com.example.todo

import android.app.Application

class TodoApplication : Application() {
    val database: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }
}