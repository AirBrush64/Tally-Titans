import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import com.example.adminmainscreen_view.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMainScreen(
    navController: NavController,
    onProfileClick: () -> Unit
) {
    val iconTintColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Spacer(modifier = Modifier.fillMaxWidth())
                },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.account_icon), // Dein Profil-Icon hier
                            contentDescription = "Profile Icon",
                            modifier = Modifier.size(40.dp),
                            tint = iconTintColor
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Größere Titeltexte wie im Wireframe
                Text(
                    text = "Tally Titans",
                    fontSize = 32.sp, // Größerer Text für den Titel
                    modifier = Modifier.padding(top = 48.dp, bottom = 8.dp),
                    color = iconTintColor
                )
                Text(
                    text = "Counting Game",
                    fontSize = 20.sp, // Größerer Text für den Untertitel
                    color = iconTintColor,
                    modifier = Modifier.padding(bottom = 48.dp),
                )

                // Buttons wie im Wireframe: Volle Breite, zentriert
                Button(
                    onClick = { navController.navigate("game") },
                    modifier = Modifier
                        .fillMaxWidth() // Buttons füllen die gesamte Breite
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .align(Alignment.End)// Abstände an den Seiten und vertikal
                        .height(48.dp)
                ) {
                    Text("Enter Game")
                }

                Button(
                    onClick = { navController.navigate("highscores") },
                    modifier = Modifier
                        .fillMaxWidth() // Buttons füllen die gesamte Breite
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .align(Alignment.End)
                        .height(48.dp)
                ) {
                    Text("Highscore")
                }

                Button(
                    onClick = { navController.navigate("management") },
                    modifier = Modifier
                        .fillMaxWidth() // Buttons füllen die gesamte Breite
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .align(Alignment.End)
                        .height(48.dp)
                ) {
                    Text("Management")
                }
            }
        }
    }
}
