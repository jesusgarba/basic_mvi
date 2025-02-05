package com.schugarba.basicmvi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.schugarba.basicmvi.api.AnimalService
import com.schugarba.basicmvi.model.Animal
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
            ViewModelProvider(this, ViewModelFactory(AnimalService.api))[MainViewModel::class.java]

        val onButtonClick: () -> Unit = {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchhAnimals)
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
        is MainState.Animals -> AnimalsScreen(animals = state.animals)
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
            Text(text = "Fetch Animals")
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
fun AnimalsScreen(animals: List<Animal>) {
    LazyColumn() {
        items(items = animals) {
            AnimalItem(animal = it)
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun AnimalItem(animal: Animal) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(100.dp)
    ) {
        val url = AnimalService.BASE_URL + animal.image

        AsyncImage(model = url, contentDescription = "images of animals", contentScale = ContentScale.FillHeight)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp)
        ) {
            Text(text = animal.name)
            Text(text = animal.location)
        }
    }
}

