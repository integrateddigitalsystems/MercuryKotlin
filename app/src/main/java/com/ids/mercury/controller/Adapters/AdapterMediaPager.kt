package com.ids.mercury.controller.Adapters


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import com.ids.mercury.R
import com.ids.mercury.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.mercury.controller.Fragments.FragmentBottomSheetNotification
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.model.response.MediaFile
import com.ids.mercury.utils.hide
import com.ids.mercury.utils.loadImagesUrl
import com.ids.mercury.utils.show
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


internal class AdapterMediaPager(private val context: Context, private val arrayList: ArrayList<MediaFile>,lifecycle: Lifecycle,private var sfm: FragmentManager) :
    PagerAdapter(),RVOnItemClickListener {
    var lifecycle=lifecycle
    var supportFragmentManager=sfm
    lateinit var myYouTubePlayer:YouTubePlayer
    private val layoutInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view:View
        var item=arrayList[position]
        view = layoutInflater.inflate(R.layout.item_media_file, container, false)
        val ivFile: ImageView = view.findViewById(R.id.ivFile) as ImageView
        val youTubePlayerView: YouTubePlayerView = view.findViewById(R.id.youtube_player_view) as YouTubePlayerView

        if(arrayList[position].isLocal==null || !arrayList[position].isLocal!!){
       if(arrayList[position].youTubePath!!.isNotEmpty()){
        lifecycle.addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = arrayList[position].youTubePath!!
                myYouTubePlayer=youTubePlayer
                myYouTubePlayer.cueVideo(videoId, 0f)
               // youTubePlayer.pause()
            }
        })
       youTubePlayerView.show()
       ivFile.hide()
       }else{
           youTubePlayerView.hide()
           ivFile.show()
           ivFile.loadImagesUrl(arrayList[position].filePath!!)
          if(arrayList[position].filePath!=null && arrayList[position].filePath!!.isNotEmpty()){
            ivFile.setOnClickListener {
                MyApplication.selectedImage = arrayList[position].filePath!!
                    val bottom_fragment = FragmentBottomSheetNotification()
                    bottom_fragment.show(supportFragmentManager, "frag_image")

            }
          }
       }}else{
            youTubePlayerView.hide()
            ivFile.show()
            ivFile.setImageResource(arrayList[position].localImage!!)
            ivFile.setOnClickListener{
                val bottom_fragment = FragmentBottomSheetNotification()
                bottom_fragment.show(supportFragmentManager, "frag_image")
                val bundle = Bundle()
                bundle.putString("image", "local")
                bottom_fragment.setArguments(bundle)
            }

       }

        container.addView(view)
        return view
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun onItemClicked(view: View, position: Int) {

    }

    fun stopPlayer(){
        myYouTubePlayer.pause()
    }




}