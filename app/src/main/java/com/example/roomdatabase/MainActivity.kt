package com.example.roomdatabase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roomdatabase.ui.theme.RoomDatabaseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDatabaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

//                    dbtest(context = applicationContext)
                    bloodTask(context = applicationContext)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun bloodTask(context: Context) {
    val database = BloodDatabase.getDatabase(context)
    val bloodDao = database.BloodDao()
    var ins by remember {
        mutableStateOf(false)
    }
    var scope = rememberCoroutineScope()
    var bloodGroup by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var data by remember {
        mutableStateOf<List<BloodGroup>?>(null)
    }
    if (ins) {
        scope.launch {
            try {
                bloodDao.insert(
                    BloodGroup(
                        bloodGroup = bloodGroup,
                        personName = name,
                    )

                )
            } catch (
                ex: Exception
            ) {
                println("cancelled")
            }

        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var expanded by remember { mutableStateOf(false) }
        val blood_groups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        TextField(value = name, onValueChange = { name = it }, label = { Text(text = "Name") })
//        TextField(value = bloodGroup, onValueChange = {bloodGroup = it}, label = {Text(text = "Blood Group")})
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = bloodGroup,
                onValueChange = {},
                label = { Text(text = "Blood Group") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                blood_groups.forEach { blood ->
                    DropdownMenuItem(text = { Text(text = blood) }, onClick = {
                        bloodGroup = blood
                        expanded = false
                    })

                }

            }
        }


        Button(onClick = { ins = !ins }) {
            Text(text = "Insert")
        }
        var search_blood_group by remember {
            mutableStateOf("")
        }
        var expanded_search by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded_search,
            onExpandedChange = { expanded_search = !expanded_search }) {
            TextField(
                value = search_blood_group,
                onValueChange = {},
                label = { Text(text = "Blood Group") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded_search) },
                modifier = Modifier
                    .menuAnchor()
                    .padding(8.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded_search,
                onDismissRequest = { expanded_search = false }
            ) {
                blood_groups.forEach { blood ->
                    DropdownMenuItem(text = { Text(text = blood) }, onClick = {
                        search_blood_group = blood
                        expanded_search = false
                    })

                }

            }
        }
        Button(onClick = {
            scope.launch {
                try {
                    data = bloodDao.getBloodGroup(search_blood_group)
                    Log.d("TAG", "bloodTask: $data")
                } catch (ex: Exception) {
                    println("Error while retrieving data")
                }
            }
        }) {
            Text(text = "Retrieve Data", Modifier.padding(4.dp))
        }

        if (!data.isNullOrEmpty()) {
            LazyColumn {
                items(data!!) {
                    Text(text = "Item Id: ${it.personName} \nName: ${it.bloodGroup}\n")
                }
            }
        }
        var delete_name by remember {
            mutableStateOf("")
        }
        TextField(
            value = delete_name,
            onValueChange = { delete_name = it },
            label = { Text(text = "Name to delete") },
            modifier = Modifier.padding(8.dp)
        )

        Button(onClick = {
            scope.launch {
                try {
                    bloodDao.delete(delete_name)
                    // show Toast
                    Toast.makeText(context, "Deleted records with name: $delete_name", Toast.LENGTH_SHORT).show()
                } catch (ex: Exception) {
                    println("Error while deleting data")
                    Toast.makeText(context, "Error while deleting data", Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "Delete Data", Modifier.padding(4.dp))
        }

    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun dbtest(context: Context){
    val database = MyDatabase.getDatabase(context)
    val myDao = database.myDao()
    var ins by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showData by remember {
        mutableStateOf(false)
    }
    var data by remember {
        mutableStateOf<List<TodoItem>?>(null)
    }

    if (ins) {
        scope.launch {

            try {
                myDao.d(
                    TodoItem(
                        itemName = "Android session",
                        itemdesk = "learning Room Database ",
                        isDone = true
                    )
                )
            } catch (ex: Exception) {
                println("cancelled")
            }
        }
    }
    Column {
        Button(onClick = { ins =  !ins}) {
            Text(text = "Insert")
        }

        Button(onClick = {
            scope.launch {
                try {
                    data = myDao.getTodo();
                    showData = true
                } catch (ex: Exception) {
                    println("Error while retrieving data")
                }
            }
        }) {
            Text(text = "Retrieve Data")
        }


        if(showData && !data.isNullOrEmpty()) {
            LazyColumn {
                items(data!!) {
                    Text(text = "\tItem Id: ${it.itemName} \nName: ${it.itemName} \nDesc: ${it.itemName} \nisCompleted: ${it.isDone}\n\n")
                }
            }
        }
    }

}
