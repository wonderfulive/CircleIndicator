package phoenix.com.circleindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lichengfeng on 2016/8/30.
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> views;
    public MyViewPagerAdapter(Context context,List<View> views){
        mContext = context;
        this.views = views;
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
