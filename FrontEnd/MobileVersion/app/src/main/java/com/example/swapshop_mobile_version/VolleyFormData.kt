package com.example.swapshop_mobile_version

import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException

open class VolleyFormData(
    method: Int,
    url: String,
    private val listener: Response.Listener<NetworkResponse>,
    errorListener: Response.ErrorListener
) : Request<NetworkResponse>(method, url, errorListener) {

    private val twoHyphens = "--"
    private val lineEnd = "\r\n"
    private val boundary = "apiclient-" + System.currentTimeMillis()

    override fun getHeaders(): MutableMap<String, String> {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "multipart/form-data; boundary=$boundary"
        return headers
    }

    override fun getBody(): ByteArray? {
        val bos = ByteArrayOutputStream()
        val dos = DataOutputStream(bos)

        try {
            val params = getParams()
            for ((key, value) in params) {
                buildTextPart(dos, key, value)
            }
            val byteData = getByteData()
            byteData?.let {
                for ((key, byteArray) in it) {
                    buildDataPart(dos, key, byteArray)
                }
            }
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd)

            return bos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos.close()
                dos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<NetworkResponse> {
        return try {
            Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: Exception) {
            Response.error(e as VolleyError?)
        }
    }

    override fun deliverResponse(response: NetworkResponse?) {
    }

    private fun buildTextPart(dos: DataOutputStream, paramName: String, value: String) {
        dos.writeBytes(twoHyphens + boundary + lineEnd)
        dos.writeBytes("Content-Disposition: form-data; name=\"$paramName\"$lineEnd")
        dos.writeBytes(lineEnd)
        dos.writeBytes(value + lineEnd)
    }

    private fun buildDataPart(dos: DataOutputStream, paramName: String, byteArray: ByteArray) {
        dos.writeBytes(twoHyphens + boundary + lineEnd)
        dos.writeBytes("Content-Disposition: form-data; name=\"$paramName\"; filename=\"file.jpg\"$lineEnd")
        dos.writeBytes("Content-Type: image/jpeg$lineEnd")
        dos.writeBytes(lineEnd)
        dos.write(byteArray)
        dos.writeBytes(lineEnd)
    }
    @Throws(AuthFailureError::class)
    protected override fun getParams(): Map<String, String> {
        return emptyMap()
    }

    @Throws(AuthFailureError::class)
    protected open fun getByteData(): Map<String, ByteArray>? {
        return null
    }
}
