package com.example.roomdatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDatabaseDao {

    @Insert
    suspend fun d(item: TodoItem)

    @Query("SELECT * FROM my_todo_list")
    suspend fun getTodo(): List<TodoItem>

}

@Dao
interface BloodDatabaseDao {
    @Insert
    suspend fun insert(item: BloodGroup)

    @Query("SELECT * FROM blood_group WHERE blood_group = :search_blood_group")
    suspend fun getBloodGroup(search_blood_group: String): List<BloodGroup>

    @Query("DELETE FROM blood_group WHERE person_name = :name")
    suspend fun delete(name: String)
}
