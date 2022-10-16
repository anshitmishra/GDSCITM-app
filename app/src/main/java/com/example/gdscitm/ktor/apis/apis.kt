package com.example.gdscitm.ktor.apis

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.gdscitm.ktor.KtorClient
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.File


class apis {
        suspend fun getPosts(selectImages: List<Uri>): String =
            KtorClient.httpClient.post("http://10.0.2.2:5000/present/image")


    suspend fun uploadImage(text: String, byteArray: Bitmap): Boolean {
        return try {
            val path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            )
            if (byteArray != null) {
                val response: HttpResponse = KtorClient.httpClient.submitFormWithBinaryData(
                    url = "http://10.0.2.2:5000/present/image",
                    formData = formData {
                        append("description", "Ktor logo")
                        append("image", File(byteArray.toString()).readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
                        })
                    }
                ) {
                    onUpload { bytesSentTotal, contentLength ->
                        println("Sent $bytesSentTotal bytes from $contentLength")
                    }
                }
            }
            true
        } catch (ex: Exception) {
//im using timber for logs, you can always replace this with Log.d
            Log.d("asd","error ${ex.message}")
            false
        }
    }
}