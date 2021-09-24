package com.ids.mercury.utils


import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.ids.mercury.R
import com.ids.mercury.controller.Activities.*
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.PagerSectionArray
import com.ids.mercury.model.SectionPagerItem
import com.ids.mercury.model.response.ResponseMemberDetails
import kotlinx.android.synthetic.main.activity_classes.*
import kotlinx.android.synthetic.main.toolbar.*
import me.grantland.widget.AutofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Ibrahim on 8/23/2017.
 */

class AppHelper {



    fun setRoundImageResize(context: Context, img: ImageView, ImgUrl: String, isLocal: Boolean) {
        Log.wtf("image_rounded_1", ImgUrl)
        if (isLocal) {
            Glide.with(context).asBitmap().load(File(ImgUrl)).centerCrop()
                .dontAnimate()
                .into(object : BitmapImageViewTarget(img) {
                    override fun setResource(resource: Bitmap?) {

                        val circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        img.setImageDrawable(circularBitmapDrawable)
                    }
                })




        } else {
            Glide.with(context).asBitmap().load(ImgUrl).centerCrop().dontAnimate().override(
                500,
                500
            )
                .into(object : BitmapImageViewTarget(img) {
                    override fun setResource(resource: Bitmap?) {
                        val circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        img.setImageDrawable(circularBitmapDrawable)
                    }
                })



        }
    }


    fun setbackgroundImage(context: Context, view: View, ImgUrl: String) {

        Glide.with(context)
            .asBitmap()
            .load(ImgUrl)

            .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    view.background = BitmapDrawable(context.resources, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }


    companion object {

        var fragmentAvailable: Int? = null
        var dateFormat1 = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var dateFormat2 = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
        var dateFormat3 = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var dateFormat4 = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        var dateFormat5 = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        var dateFormat6 = SimpleDateFormat("EEEE", Locale.ENGLISH)
        var dateFormat7 = SimpleDateFormat("MMMM d", Locale.ENGLISH)
        var dateFormat8 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        var dateFormat9 = SimpleDateFormat("hh:00 a", Locale.ENGLISH)
        var dateFormat10 = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        var dateFormat11 = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
        fun getAndroidVersion(): String {

            val release = Build.VERSION.RELEASE
            val sdkVersion = Build.VERSION.SDK_INT
            return "Android:$sdkVersion ($release)"
        }


        fun getTypeFace(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/Inter-Medium.ttf"
                )
            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/Inter-Medium.ttf"
                )
         }

        fun getTypeFaceBold(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/Inter-Bold.ttf"
                )

            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/Inter-Bold.ttf"
                )

        }

        fun opacity(percenage:Double):String{
           return Integer.toHexString(percenage.toInt())
        }

        fun getMemberDetails () {
            RetrofitClient.client?.create(RetrofitInterface::class.java)
                ?.getMemberDetails(MyApplication.memberId.toString())?.enqueue(object :
                    Callback<ResponseMemberDetails> {
                    override fun onResponse(
                        call: Call<ResponseMemberDetails>,
                        response: Response<ResponseMemberDetails>
                    ) {
                        if(response.body()!!.success=="1" && response.body()!!.members!!.size>0){
                          MyApplication.memberDetails= response.body()!!.members!![0]
                        }

                    }
                    override fun onFailure(call: Call<ResponseMemberDetails>, t: Throwable) {
                    }
                })
        }



        fun getTypeFaceLight(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/Anisette-Medium.otf"
                )
            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/Anisette-Medium.otf"
                )

        }

        fun getTypeFaceWeb(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/PTSansNarrow_Regular.ttf"
                )
            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/PTSansNarrow_Regular.ttf"
                )

        }




        fun getIdFromUserId(Id: Int){

        }

        fun createDialog(c: Activity, message: String) {

            if(message!=null && message!="null"){
            var ok = c.getString(R.string.ok)

            val builder = AlertDialog.Builder(c)
            builder
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(ok) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()}

        }

        fun handleCrashes(context: Activity) {
            if (!MyApplication.isDebug)
                Thread.setDefaultUncaughtExceptionHandler(MyExceptionHandler(context))
        }


        private fun getStringResourceByName(aString: String, context: Context): String? {
            val packageName: String = context.getPackageName()
            val resId: Int = context.getResources().getIdentifier(aString, aString, packageName)
            return context.getString(resId)
        }


        fun formatNumber(num: Double, format: String): String {
            val formatter = DecimalFormat(format, setInEnglish())
            return formatter.format(num)
        }

        fun setInEnglish(): DecimalFormatSymbols {
            val custom = DecimalFormatSymbols(Locale.ENGLISH)
            custom.decimalSeparator = '.'
            return custom
        }



        fun formatDate(c: Context, dateString: String, oldDateFormat: String, newDateFormat: String):String?{
            var format = SimpleDateFormat(oldDateFormat, Locale.ENGLISH)
            val newDate = format.parse(dateString)
            format = SimpleDateFormat(newDateFormat, Locale.ENGLISH)
            val date = format.format(newDate)
            return date
        }


        fun formatDateTimestamp(dateFormat:SimpleDateFormat,timeStamp:String,cut:Boolean):String?{
            try {
                var myTimeStamp=timeStamp
                if(cut)
                    myTimeStamp=timeStamp.substring(6,myTimeStamp.length-2)
                Log.w("timestamp_1", "formatDateTimestamp: "+myTimeStamp )
                val netDate = Date(myTimeStamp.toLong())
                return dateFormat.format(netDate)
            } catch (e: Exception) {
                Log.w("timestamp_1", e.toString() )
                return ""
            }
        }

         fun setTimePickerInterval(timePicker: TimePickerDialog) {
            var TIME_PICKER_INTERVAL=15
            try {
                val minutePicker = timePicker.findViewById(
                    Resources.getSystem().getIdentifier(
                        "minute", "id", "android"
                    )
                ) as NumberPicker
                minutePicker.minValue = 0
                minutePicker.maxValue = 60 / TIME_PICKER_INTERVAL - 1
                val displayedValues: MutableList<String> = ArrayList()
                var i = 0
                while (i < 60) {
                    displayedValues.add(String.format("%02d", i))
                    i += TIME_PICKER_INTERVAL
                }
                minutePicker.displayedValues = displayedValues.toTypedArray()
            } catch (e: java.lang.Exception) {

            }
        }

        fun changeLanguage(context: Context, language: String) {

            when (language) {
                AppConstants.LANG_ARABIC -> Locale.setDefault(Locale("ar"))
                AppConstants.LANG_ENGLISH -> Locale.setDefault(Locale.ENGLISH)
                "0" -> {
                    Locale.setDefault(Locale.ENGLISH)
                }
            }

            val configuration = Configuration()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(Locale.getDefault())
                configuration.setLayoutDirection(Locale.getDefault())

            } else
                configuration.locale = Locale.getDefault()


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context.createConfigurationContext(configuration);
            } else {
                context.resources.updateConfiguration(
                    configuration,
                    context.resources.displayMetrics
                )
            }
            MyApplication.languageCode = language


        }



        fun setLocal(context: Context) {

            if (MyApplication.languageCode == AppConstants.LANG_ENGLISH) {
               LocaleUtils.setLocale(Locale("en"))
            } else if (MyApplication.languageCode == AppConstants.LANG_ARABIC) {
                LocaleUtils.setLocale(Locale("ar", "LB"))
            }

        }

        fun isValidEmail(target: String): Boolean {

            return if (target.isEmpty()) {
                false
            } else {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }

        fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }
            return true
        }



        fun isProbablyArabic(s: String): Boolean {
            var i = 0
            while (i < s.length) {
                val c = s.codePointAt(i)
                if (c in 0x0600..0x06E0)
                    return true
                i += Character.charCount(c)
            }
            return false
        }

        fun share(context: Context, subject: String, text: String) {
            val intent = Intent(Intent.ACTION_SEND)

            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, text)

            context.startActivity(Intent.createChooser(intent, "share"))
        }


   /*     fun AddFragment(
            fragmentManager: FragmentManager,
            selectedFragment: Int,
            myFragment: Fragment,
            myTag: String
        ){
            fragmentAvailable = selectedFragment
            fragmentManager.beginTransaction()
*//*                .setCustomAnimations(
                    com.ids.qaseemati.R.anim.enter_from_right,
                    com.ids.qaseemati.R.anim.exit_to_left,
                    com.ids.qaseemati.R.anim.enter_from_left,
                    com.ids.qaseemati.R.anim.exit_to_right
                )*//*
                .add(com.ids.qaseemati.R.id.container, myFragment, myTag)
                .addToBackStack(null)
                .commit()
        }



        fun ReplaceFragment(
            fragmentManager: FragmentManager,
            selectedFragment: Int,
            myFragment: Fragment,
            myTag: String
        ){
            fragmentAvailable = selectedFragment
            fragmentManager.beginTransaction()

                .replace(com.ids.qaseemati.R.id.container, myFragment, myTag)
                *//*              .setCustomAnimations(
                                  com.ids.qaseemati.R.anim.enter_from_right,
                                  com.ids.qaseemati.R.anim.exit_to_left,
                                  com.ids.qaseemati.R.anim.enter_from_left,
                                  com.ids.qaseemati.R.anim.exit_to_right
                              )*//*
                .commit()

        }
*/




        fun setMargins(context: Context, view: View, left: Int, top: Int, right: Int, bottom: Int) {
            if (view.layoutParams is ViewGroup.MarginLayoutParams) {
                val leftInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, left.toFloat(), context.resources
                        .displayMetrics
                ).toInt()
                val topInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, top.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val rightInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, right.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val bottomInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, bottom.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val p = view.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(leftInDp, topInDp, rightInDp, bottomInDp)
                view.requestLayout()
            }
        }


        fun setPaddings(context: Context, view: View, left: Int, top: Int, right: Int, bottom: Int) {
            if (view.layoutParams is ViewGroup.MarginLayoutParams) {
                val leftInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, left.toFloat(), context.resources
                        .displayMetrics
                ).toInt()
                val topInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, top.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val rightInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, right.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val bottomInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, bottom.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val p = view.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(leftInDp, topInDp, rightInDp, bottomInDp)
                view.requestLayout()
            }
        }




        fun getScreenSize(context: Context): String {
            when (context.resources.displayMetrics.densityDpi) {
                DisplayMetrics.DENSITY_MEDIUM -> return AppConstants.MDPI
                DisplayMetrics.DENSITY_HIGH -> return AppConstants.HDPI
                DisplayMetrics.DENSITY_XHIGH -> return AppConstants.XHDPI
                DisplayMetrics.DENSITY_XXHIGH -> return AppConstants.XXHDPI
                DisplayMetrics.DENSITY_XXXHIGH -> return AppConstants.XXXHDPI
                else -> return AppConstants.XXXHDPI
            }
        }




        private fun capitalize(model: String): String {
            if (model.length == 0) {
                return ""
            }
            val first = model.get(0)
            return if (Character.isUpperCase(first)) {
                model
            } else {
                Character.toUpperCase(first) + model.substring(1)
            }
        }

        fun getDeviceName(): String {

            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }




        fun setImageResize(
            context: Context,
            img: ImageView,
            ImgUrl: String,
            height: Int,
            width: Int
        ){


            Glide
                .with(context)
                .load(ImgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(width, height)
                .centerCrop()
                .into(img);

        }

        fun setRoundImageResize(context: Context, img: ImageView, ImgUrl: String, isLocal: Boolean) {
            Log.wtf("image_rounded", ImgUrl)
            if (isLocal) {
                Glide.with(context).asBitmap().load(File(ImgUrl)).centerCrop()
                    .dontAnimate()
                    .into(object : BitmapImageViewTarget(img) {
                        override fun setResource(resource: Bitmap?) {

                            val circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            img.setImageDrawable(circularBitmapDrawable)
                        }
                    })




            } else {
                Glide.with(context).asBitmap().load(ImgUrl).centerCrop().dontAnimate().override(
                    500,
                    500
                )
                    .into(object : BitmapImageViewTarget(img) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            img.setImageDrawable(circularBitmapDrawable)
                        }
                    })



            }
        }


        fun setImageResizePost(context: Context, img: ImageView, ImgUrl: String) {


            Glide
                .with(context)
                .load(ImgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(700, 500)
                .centerCrop()
                .into(img);

        }



        fun setImage(context: Context, img: ImageView, ImgUrl: String, isLocal: Boolean) {
            try {
                if (isLocal) {
                    Glide.with(context)
                        .load(File(ImgUrl))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .fitCenter()
                        .dontTransform()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .into(img)
                } else {
                    Glide.with(context)
                        .load(ImgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .fitCenter()
                        .dontTransform()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .into(img)
                }


            } catch (e: Exception) {
            }

        }

        fun setRoundImage(context: Context, img: ImageView, ImgUrl: String, isLocal: Boolean) {
            Log.wtf("image_rounded", ImgUrl)
            if (isLocal) {
                Glide.with(context).asBitmap().load(File(ImgUrl)).centerCrop().dontAnimate()
                    .into(object : BitmapImageViewTarget(img) {
                        override fun setResource(resource: Bitmap?) {

                            val circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            img.setImageDrawable(circularBitmapDrawable)
                        }
                    })




            } else {
                Glide.with(context).asBitmap().load(ImgUrl).centerCrop().dontAnimate()
                    .into(object : BitmapImageViewTarget(img) {
                        override fun setResource(resource: Bitmap?) {

                            val circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            img.setImageDrawable(circularBitmapDrawable)
                        }
                    })



            }
        }



        fun autofitText(vararg texts: TextView?) {
            for (element in texts) AutofitHelper.create(element)
        }



        fun setPagerArray(context: Context,pageItemCount:Int):ArrayList<PagerSectionArray>{
            var arrayPagesSections=java.util.ArrayList<PagerSectionArray>()
            var arrayAllSections=java.util.ArrayList<SectionPagerItem>()
            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_MEMBERSHIP_STATUS_ID,context.getString(R.string.membership_status),"",R.drawable.home_membership,"",true,true)
            {context.startActivity(
                Intent(context, ActivityMembershipStatus::class.java)
            )})

            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_FITNESS_ID,context.getString(R.string.fitness_classes),"",R.drawable.home_fitness,"",true,true)
            {context.startActivity(
                Intent(context, ActivityClasses::class.java)
            )})

            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_ACADEMIES_ID,context.getString(R.string.academies),"",R.drawable.home_academy,"",true,true)
            {context.startActivity(
                Intent(context, ActivityAcademies::class.java)
            )})

            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_RENT_A_COURT_ID,context.getString(R.string.rent_a_court),"",R.drawable.home_rent_a_court,"",true,true)
            {context.startActivity(
                Intent(context, ActivityRentCourt::class.java)
            )})

            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_PAYMENT_HISTORY_ID,context.getString(R.string.payments_history),"",R.drawable.home_paymenthist,"",true,true)
            {context.startActivity(
                Intent(context, ActivityPaymentHistory::class.java)
            )})

            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_GIFT_CARD_ID,context.getString(R.string.gift_card),"",R.drawable.home_giftcard,"",true,true)
            {context.startActivity(
                Intent(context, ActivityGiftCard::class.java)
            )})

            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_GUESS_PASSES_ID,context.getString(R.string.guess_passes),"",R.drawable.home_guestpass,"",true,true)
            {context.startActivity(
                Intent(context, ActivityGuestPass::class.java)
            )})
            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_REFFER_A_FRIEND_ID,context.getString(R.string.refer_a_friend),"",R.drawable.home_refer,"",true,true)
            {context.startActivity(
                Intent(context, ActivityReferFriends::class.java)
            )})
            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_CSR_ID,context.getString(R.string.csr),"",R.drawable.home_csr,"",true,true)
            {context.startActivity(
                Intent(context, ActivityCsr::class.java)
            )})
            arrayAllSections.add(SectionPagerItem(AppConstants.MENU_RULES_AND_REGULATIONS_ID,context.getString(R.string.rules_regulations),"",R.drawable.home_rulesregelations,"",true,true)
            {context.startActivity(
                Intent(context, ActivityInsideCsrRules::class.java).putExtra("type",AppConstants.TYPE_RULES)
            )})

            var tempArray=java.util.ArrayList<SectionPagerItem>()
            arrayPagesSections.clear()
            for (i in arrayAllSections.indices){
                tempArray.add(arrayAllSections[i])
                if((((i+1) % pageItemCount) == 0 ) && i!=0 || (i == arrayAllSections.size-1)){
                    arrayPagesSections.add(PagerSectionArray(tempArray))
                    tempArray= arrayListOf()
                }
            }

            return arrayPagesSections
        }



         fun setToolbarScrollAnimation(context: Context,linearToolbar:RelativeLayout,tvToolbarTitle:TextView,myScroll:NestedScrollView,tvTitleOver:TextView) {

            linearToolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent))
            tvToolbarTitle.setTextColor(ContextCompat.getColor(context,R.color.transparent))
            var percentage = 0.0
            val colorsBlack: Array<String> = context.resources.getStringArray(R.array.black_colors)
            colorsBlack.reverse()
            val colorsWhite: Array<String> = context.resources.getStringArray(R.array.white_colors)
            val colorsWhiteReverse: Array<String> = context.resources.getStringArray(R.array.white_colors)
            colorsWhiteReverse.reverse()
            myScroll.viewTreeObserver.addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
                try {
                    var scrollY = myScroll.scrollY // For ScrollView
                    var toolbarHeight=context.dp(R.dimen.toolbar_height)
                    if (convertPixelsToDp(scrollY.toFloat(), context) < toolbarHeight) {
                        linearToolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent))
                        tvToolbarTitle.setTextColor(ContextCompat.getColor(context,R.color.transparent))
                        tvTitleOver.setTextColor(ContextCompat.getColor(context,R.color.white))
                    } else if (convertPixelsToDp(scrollY.toFloat(), context) in toolbarHeight..toolbarHeight*2) {
                        percentage = (((convertPixelsToDp(scrollY.toFloat(), context) - toolbarHeight).toDouble() * 100) / toolbarHeight) / 100
                        //linearToolbar.background.alpha = (percentage * 255).toInt()
                        var position=0
                        try{position=(percentage*100*colorsBlack.size/100).toInt()}catch (e:Exception){}
                        linearToolbar.setBackgroundColor(Color.parseColor(colorsBlack[position]))
                        tvToolbarTitle.setTextColor(Color.parseColor(colorsWhiteReverse[position]))
                        tvTitleOver.setTextColor(Color.parseColor(colorsWhite[position]))
                    } else {
                        tvToolbarTitle.show()
                        linearToolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.black))
                        tvToolbarTitle.setTextColor(ContextCompat.getColor(context,R.color.white))
                        tvTitleOver.setTextColor(ContextCompat.getColor(context,R.color.transparent))
                    }
                } catch (E: Exception) {
                }
            })
        }

        fun convertPixelsToDp(px: Float, context: Context): Float {
            return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }


    }





}
