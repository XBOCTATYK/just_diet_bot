package dietbot.googlesheets

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.services.AbstractGoogleClient
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.CellData
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

object Initializer {
    private val JSON_FACTORY: JacksonFactory = JacksonFactory.getDefaultInstance()
    private val BASE_DIR = System.getProperty("user.dir") + "/src/main"
    private val TOKENS_DIRECTORY_PATH = "$BASE_DIR/resources/tokens"
    private val CREDENTIALS_PATH = "$BASE_DIR/resources/credentials.json"
    private val SCOPES: MutableList<String> = mutableListOf(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE, SheetsScopes.DRIVE_FILE)

    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        val credentialsReadStream = FileInputStream(CREDENTIALS_PATH)
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(credentialsReadStream))

        val flow = GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()

        //
        System.out.println("brakepoint here")

        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    fun initialize(): Sheets {
        val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()

        val credentials = getCredentials(httpTransport)
        return Sheets.Builder(httpTransport, JSON_FACTORY, credentials)
            .setApplicationName("diet_bot2")
            .build()
    }
}