package com.ids.mercury.utils

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.net.http.SslError
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.Window
import android.webkit.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.ids.mercury.R
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.ResponseCheckout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentApiDialog {
    companion object{
        lateinit var paymentListener :PaymentListener
        var amount=""
        lateinit var ctx :Context
        lateinit var dialog :Dialog

        fun showPaymentDialog(context: Context,listener: PaymentListener,amt:String){
            paymentListener=listener
            amount=amt.trim().toDouble().toInt().toString()
            ctx=context
            showWebviewDialog()
        }

        private fun createCheckOutSession(webView: WebView,loading:LinearLayout){
            loading.show()
            var orderId=System.currentTimeMillis()/1000
            MyApplication.lastUsedOrderId = orderId
            var sname=MyApplication.URL_CREATE_CHECKOUT_SESSION.substring(
                MyApplication.URL_CREATE_CHECKOUT_SESSION.lastIndexOf("/")+1,
                MyApplication.URL_CREATE_CHECKOUT_SESSION.length
            )
            RetrofitClientSession.client?.create(RetrofitInterface::class.java)
                ?.createCheckoutSession(sname,orderId, amount)?.enqueue(object :
                    Callback<ResponseCheckout> {
                    override fun onResponse(
                        call: Call<ResponseCheckout>,
                        response: Response<ResponseCheckout>
                    ) {
                        try{
                        if(response.body()!!.result.toString().lowercase()=="success")
                           loadWebview(loading,webView,response.body()!!.merchant!!,response.body()!!.session!!.id!!,amount,orderId.toString())
                        else{
                            loading.hide()
                            ctx.toastt(ctx.getString(R.string.try_again))
                        }}catch (e:Exception){
                            ctx.toastt(ctx.getString(R.string.try_again))
                        }

                    }
                    override fun onFailure(call: Call<ResponseCheckout>, t: Throwable) {
                        loading.hide()
                         ctx.toastt(ctx.getString(R.string.try_again))
                    }
                })
        }


        private fun showWebviewDialog(){

            dialog = Dialog(ctx, R.style.DialogThemePayment)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(R.layout.popup_webview)
            dialog.setCancelable(true)
            val wvPayment: WebView = dialog.findViewById(R.id.wvPayment)
            val btClose: Button = dialog.findViewById(R.id.btClose)
            val loading: LinearLayout = dialog.findViewById(R.id.loading)
            wvPayment.setBackgroundColor(Color.WHITE)
            btClose.setOnClickListener{
                dialog.dismiss()
            }

            createCheckOutSession(wvPayment,loading)

            dialog.show()
        }


        private fun loadWebview(loading: LinearLayout,myWebview:WebView,merchanId:String,checkOutSessionid:String,amt: String,orderId:String){
            val mWebSettings = myWebview.settings
            mWebSettings.javaScriptEnabled = true
            mWebSettings.domStorageEnabled = true
            mWebSettings.setSupportMultipleWindows(true)

            var url=AppConstants.htmlText
            .replace("myUrl",MyApplication.URL_PAYMENT_JS)
            .replace("<your_merchant_id>",merchanId)
            .replace("<checkout_session_id>",checkOutSessionid)
            .replace("<amount>",amt)
            .replace("<unique_order_id>",orderId)

            myWebview.webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(view: WebView, dialog: Boolean, userGesture: Boolean, resultMsg: Message?): Boolean {
                    val result: WebView.HitTestResult = view.hitTestResult
                    val data: String = result.getExtra()!!
                    val context: Context = view.context
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data))
                    context.startActivity(browserIntent)
                    return false
                }
            }
            myWebview.webViewClient = object : WebViewClient() {

                override fun onReceivedError(
                    view: WebView,
                    errorCode: Int,
                    description: String,
                    failingUrl: String
                ) {
                    Toast.makeText(ctx.applicationContext, description, Toast.LENGTH_SHORT).show()
                }

                @TargetApi(android.os.Build.VERSION_CODES.M)
                override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
                }
                override fun onReceivedSslError(
                    view: WebView?, handler: SslErrorHandler?, error: SslError?
                ) {
                    val builder = AlertDialog.Builder(ctx)
                    builder.setMessage(R.string.notification_error_ssl_cert_invalid)
                    builder.setPositiveButton("continue"
                    ) { dialog, which -> handler!!.proceed() }
                    builder.setNegativeButton("cancel"
                    ) { dialog, which -> handler!!.cancel() }
                    val dialog = builder.create()
                    dialog.show()

                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                        Log.wtf("change_url",url+"......")

                    Handler(Looper.getMainLooper()).postDelayed({
                        loading.hide()
                    }, 1200)
                }

                override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    Log.wtf("change_url_2",url+"......")
                    if(url!!.contains("hc-action-complete")){
                        dialog.dismiss()
                        paymentListener.onFinishPayment(true)
                    }
                    

              }


            }
            myWebview.loadDataWithBaseURL(null, url, "text/html", "utf-8", null)
         //   myWebview.loadUrl(MyApplication.URL_PAYMENT_JS)
        }


    }



}