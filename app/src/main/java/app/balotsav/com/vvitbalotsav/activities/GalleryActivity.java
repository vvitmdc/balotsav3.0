package app.balotsav.com.vvitbalotsav.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.OnClickAdapter;
import app.balotsav.com.vvitbalotsav.model.PhotoFullPopupWindow;
import app.balotsav.com.vvitbalotsav.utils.GalleryAdapter;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PhotoFullPopupWindow photoFullPopupWindow=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setTitle(R.string.photo_gallery);
        List<String> galleryList = Arrays.asList(getResources().getStringArray(R.array.event_photos));
        recyclerView=findViewById(R.id.recycler_gallery);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        GalleryAdapter galleryAdapter=new GalleryAdapter(this);
        galleryAdapter.setGalleryList(galleryList);
        recyclerView.setAdapter(galleryAdapter);
        galleryAdapter.setClickAdapter(new OnClickAdapter() {
            @Override
            public void onClickAdapter(ImageView imageView,String url,int i) {
               photoFullPopupWindow = new  PhotoFullPopupWindow(getApplicationContext(), imageView, url, null);
              }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(photoFullPopupWindow!=null){
            if(photoFullPopupWindow.isShowing()){
                photoFullPopupWindow.dismiss();
                photoFullPopupWindow=null;
            }
        }
        else{
            finish();
        }
    }
}
