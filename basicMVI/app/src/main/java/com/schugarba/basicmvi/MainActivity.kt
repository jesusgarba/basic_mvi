package com.schugarba.basicmvi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.schugarba.basicmvi.api.TodoService
import com.schugarba.basicmvi.model.Todo
import com.schugarba.basicmvi.ui.theme.BasicMVITheme
import com.schugarba.basicmvi.view.MainIntent
import com.schugarba.basicmvi.view.MainState
import com.schugarba.basicmvi.view.MainViewModel
import com.schugarba.basicmvi.view.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel =
            ViewModelProvider(this, ViewModelFactory(TodoService.api))[MainViewModel::class.java]

        val onButtonClick: () -> Unit = {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchTodos)
            }
        }

        setContent {
            BasicMVITheme {
                MainScreen(vm = mainViewModel, onbuttonClick = onButtonClick)
            }
        }
    }
}

@Composable
fun MainScreen(vm: MainViewModel, onbuttonClick: () -> Unit) {
    val state = vm.state.value

    when (state) {
        is MainState.Idle -> IdleScreen(onbuttonClick = onbuttonClick)
        is MainState.Todos -> TodosScreen(todos = state.todos)
        is MainState.Error -> {
            IdleScreen(onbuttonClick = onbuttonClick)
            Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
        }

        MainState.Loading -> LoadingScreen()
    }
}

@Composable
fun IdleScreen(onbuttonClick: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onbuttonClick) {
            Text(text = "Fetch TODO's")
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun TodosScreen(todos: List<Todo>) {
    LazyColumn() {
        items(items = todos) {
            TodoItem(todo = it)
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun TodoItem(todo: Todo) {
    val colorIcon = if (todo.completed) Color.Black else Color.Red
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(100.dp)
    ) {
        Icon(painter = painterResource(R.drawable.ic_pencil), contentDescription = "icon", tint = colorIcon)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp)
        ) {
            Text(text = todo.userId)
            Text(text = todo.title)
        }
    }
}

