package at.htl.qr_code_scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
//import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import at.htl.qr_code_scanner.ui.theme.QrCodeScannerComposeTheme
import org.w3c.dom.Text
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL
import java.net.HttpURLConnection


class MainActivity : ComponentActivity() {

    var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            QrCodeScannerComposeTheme {
                var code by remember {
                    mutableStateOf("")
                }
                val context = LocalContext.current
                val lifecycleOwner = LocalLifecycleOwner.current
                val cameraProviderFuture = remember {
                    ProcessCameraProvider.getInstance(context)
                }
                var hasCamPermission by remember {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { granted ->
                        hasCamPermission = granted
                    }
                )
                LaunchedEffect(key1 = true) {
                    launcher.launch(Manifest.permission.CAMERA)
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (hasCamPermission) {
                        AndroidView(
                            factory = { context ->
                                val previewView = PreviewView(context)
                                val preview = Preview.Builder().build()
                                val selector = CameraSelector.Builder()
                                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                    .build()
                                preview.setSurfaceProvider(previewView.surfaceProvider)
                                val imageAnalysis = ImageAnalysis.Builder()
                                    .setTargetResolution(
                                        Size(
                                            previewView.width,
                                            previewView.height
                                        )
                                    )
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build()
                                imageAnalysis.setAnalyzer(
                                    ContextCompat.getMainExecutor(context),
                                    QrCodeAnalyzer { result ->
                                        code = result
                                    }
                                )
                                try {
                                    cameraProviderFuture.get().bindToLifecycle(
                                        lifecycleOwner,
                                        selector,
                                        preview,
                                        imageAnalysis
                                    )
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                previewView
                            },
                            modifier = Modifier.weight(1f)
                        )


//                        ScanButton(code)

                        var url = code.replace("localhost", "10.0.2.2")
                        url = url.replace("qrcodes", "voucher")
                        url = StringBuilder(url).append("?cancel=true").toString()
                        ButtonWithAsyncAction(url)
//                        Button(onClick = {
//
//                            println(url)
//                        }) {
//                            Text(text = "scan qr")
//                        }
//                        Text(
//                            text = code,
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(32.dp)
//                        )


                    }
                }
            }
        }
    }


    @Composable
    fun ButtonWithAsyncAction(url: String, modifier: Modifier = Modifier) {
        val coroutineScope = rememberCoroutineScope()
        // This state will tell Compose when to show TicketRedeemed
        var showTicketRedeemed by remember { mutableStateOf(false) }

        if (showTicketRedeemed) {
            TicketRedeemed() // This will be displayed when the flag is true
        } else {
        Button(onClick = {
            coroutineScope.launch {
                val response = getUrlContent(url)
                println("url: $url")
                println("response: $response")
                showTicketRedeemed = true
            }
        }) {
            Text(text = "Scan QR")
        }

        }
    }

    private suspend fun getUrlContent(url: String): String = withContext(Dispatchers.IO) {
        try {
            (URL(url).openConnection() as HttpURLConnection).run {
                requestMethod = "GET"
                return@withContext inputStream.bufferedReader().use { it.readText() }
            }
        } catch (e: FileNotFoundException) {
            return@withContext "File not found: $url"
        } catch (e: IOException) {
            return@withContext "IO Error: ${e.message}"
        } catch (e: Exception) {
            return@withContext "Error: ${e.message}"
        }
    }

    @Composable
    fun TicketRedeemed(modifier: Modifier = Modifier) {
        Text(
            text = "Ticket Redeemed"
        )

    }


}
