import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

fun main() {
    currentComposer

    Window(title = "Funil de Arquivos", size = IntSize(854, 120)) {
        val count = remember { mutableStateOf(0) }
        MaterialTheme(colors = darkColors()) {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Column(Modifier.defaultMinSizeConstraints(), Arrangement.spacedBy(5.dp)) {
                    Text("Funil de Arquivos", fontSize = 2.5.em, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Row(Modifier.defaultMinSizeConstraints(), Arrangement.spacedBy(5.dp)) {

                        Text("Ouvindo", fontSize = 1.em, modifier = Modifier.align(Alignment.CenterVertically))
                        Button(modifier = Modifier.align(Alignment.CenterVertically),
                            onClick = {
                                count.value = 0
                            }) {
                            Text("Atualizar Agora")
                        }
                    }
                }
            }
        }
    }
}