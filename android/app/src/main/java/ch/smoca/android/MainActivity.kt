package ch.smoca.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.smoca.android.ui.theme.AndroidTheme
import ch.smoca.multiplatform.AppState
import ch.smoca.multiplatform.reducers.CountReducer
import ch.smoca.multiplatform.sagas.CatFactNetworkSaga
import ch.smoca.redux.Store

class MainActivity : ComponentActivity() {
    private lateinit var store: Store<AppState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = (application as MainApplication).store

        // fetch first fact
        store.dispatch(CatFactNetworkSaga.NetworkAction.FetchCatFact)

        enableEdgeToEdge()

        // strong skipping mode is enabled in the module build.gradle
        // this is important in order to use the dispatch function directly in the callback
        setContent {
            // collect AppState as compose state
            val state by store.stateObservable.collectAsState()

            AndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Counter(
                            count = state.count.toString(),
                            onDecrease = { store.dispatch(CountReducer.CountAction.Decrease) },
                            onIncrease = { store.dispatch(CountReducer.CountAction.Increase) }
                        )
                        Spacer(modifier = Modifier.size(32.dp))
                        Text(modifier = Modifier.padding(horizontal = 32.dp), text = state.catFact)
                        Spacer(modifier = Modifier.size(32.dp))
                        Button(onClick = { store.dispatch(CatFactNetworkSaga.NetworkAction.FetchCatFact) }) {
                            Text(text = "New Fact")
                        }
                    }
                }
            }
        }
    }
}

// this component will re-render every time the variable count changes
@Composable
fun Counter(count: String, onDecrease: () -> Unit, onIncrease: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = onDecrease) {
            Text(text = "-")
        }
        Text(text = count, fontSize = 32.sp)
        Button(onClick = onIncrease) {
            Text(text = "+")
        }
    }
}
