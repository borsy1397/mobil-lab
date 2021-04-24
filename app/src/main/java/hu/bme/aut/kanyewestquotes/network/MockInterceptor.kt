package hu.bme.aut.kanyewestquotes.network

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import org.json.JSONObject
import kotlin.random.Random

class MockInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        when (chain.request().method) {
            "POST" -> {
                val buffer = Buffer()
                chain.request().body?.writeTo(buffer)
                val bodyString = buffer.readUtf8()

                val mockCreatedResponse = """
                {"id":"${Random.nextLong(
                    1,
                    10000
                )}","quote":"${JSONObject(bodyString).get("quote")}"}
                """
                return chain.proceed(chain.request())
                    .newBuilder()
                    .code(201)
                    .protocol(Protocol.HTTP_2)
                    .message(mockCreatedResponse)
                    .body(
                        mockCreatedResponse.toByteArray()
                            .toResponseBody("application/json".toMediaTypeOrNull())
                    )
                    .addHeader("content-type", "application/json")
                    .build()
            }
            "DELETE" ->
                return chain.proceed(chain.request())
                    .newBuilder()
                    .code(204)
                    .protocol(Protocol.HTTP_2)
                    .build()

        }

        return chain.proceed(chain.request())

    }

}
