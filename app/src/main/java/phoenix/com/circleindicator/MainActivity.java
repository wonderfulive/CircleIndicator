package phoenix.com.circleindicator;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<View> views = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            View view = new View(this);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            if (i % 3 == 0) {
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            } else if (i % 3 == 1) {
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            } else {
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
            }
            views.add(view);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(this, views);
        indicator.setCount(views.size());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indicator.setCurrentIndex(position);
            }
        });

    }
}
