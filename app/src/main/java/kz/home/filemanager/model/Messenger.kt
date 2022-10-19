package kz.home.filemanager.model

enum class Messenger(val dirs: List<String>) {
    TELEGRAM(listOf("Android/media/org.telegram.messenger", "Telegram")),
    WHATSAPP(listOf("Android/media/com.whatsapp")),
    DOWNLOADS(listOf("Download"))
}