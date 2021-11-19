package dietbot.services

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.FileInputStream
import java.io.InputStreamReader

data class CredentialsConfig(
    @SerializedName("token") var token: String,
    @SerializedName("spreadsheetId") var spreadsheetId: String
    )

class CredentialsService {
    private val baseDir = System.getProperty("user.dir") + "/src/main"
    var config: CredentialsConfig

    init {
        val credentialFile = FileInputStream("$baseDir/resources/bot_credentials.json")
        val text = InputStreamReader(credentialFile).readText()

        this.config = Gson().fromJson(text, CredentialsConfig::class.java)
    }
}