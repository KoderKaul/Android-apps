package com.example.delivryatdoors;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter1;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Restaurants> restaurants;
    private ArrayList<ImageUrl> imageUrls,oye;
    private ArrayList<ArrayList<Dish>> menus;
    private ArrayList<Dish> menu1 ,menu2,menu3,menu4,menu5,menu6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView1 = v.findViewById(R.id.restaurant_recyclerview);
        recyclerView1.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager);

        restaurants = new ArrayList<>();
        imageUrls = new ArrayList<>();
        oye = new ArrayList<>();
        menus = new ArrayList<>();
        menu1 = new ArrayList<>();
        menu2 = new ArrayList<>();
        menu3 = new ArrayList<>();
        menu4 = new ArrayList<>();
        menu5 = new ArrayList<>();
        menu6 = new ArrayList<>();

        imageUrls.add(new ImageUrl("https://b.zmtcdn.com/data/pictures/chains/2/1212/b6429ddad24625e65344caabb921bd57.jpg?output-format=webp?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"));
        imageUrls.add(new ImageUrl("https://b.zmtcdn.com/data/pictures/chains/5/1795/bc9f8e5a333dc0c85317506708defd6d.png?output-format=webp?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"));
        imageUrls.add(new ImageUrl("https://i.ndtvimg.com/i/2017-07/rajasthani-food-festival_620x350_41500037747.jpg"));
        imageUrls.add(new ImageUrl("https://resize.indiatvnews.com/en/resize/newbucket/715_-/2019/01/650592-non-veg-food-pti-1548311629.jpg"));
        imageUrls.add(new ImageUrl("https://b.zmtcdn.com/data/pictures/chains/8/3258/84500076fb1903575f0f57579343586e.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"));
        imageUrls.add(new ImageUrl("https://b.zmtcdn.com/data/reviews_photos/b11/c4a99a980003c6b63e78454d3b643b11_1555334281.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"));

        oye.add(new ImageUrl("https://www.myjewishlearning.com/wp-content/uploads/2007/07/KUFTA.jpg"));
        oye.add(new ImageUrl("https://www.holidify.com/blog/wp-content/uploads/2016/09/aamat.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"));
        oye.add(new ImageUrl("https://allaboutjaipur.com/wp-content/uploads/2019/03/fabhotels-Thali-and-More.jpg"));
        oye.add(new ImageUrl("https://hips.hearstapps.com/del.h-cdn.co/assets/17/35/2048x1365/gallery-1504128527-delish-mushroom-risotto.jpg?resize=980:*"));
        oye.add(new ImageUrl("https://panchhipetha.com/wp-content/uploads/2017/01/dalmoth.jpg?v=98f877c23e25"));
        oye.add(new ImageUrl("https://www.sanjanafeasts.co.uk/blog/wp-content/uploads/2020/01/Homemade-Shahi-Paneer-recipe.jpg"));

        restaurants.add(new Restaurants("Kaul Ke Kebab","Kebab, Biriyani & Panner","Kaul's Mansion, Near Spar Mall, Secundarabad",null,250,4.5));
        restaurants.add(new Restaurants("Mishra Khana Bhandar","Faraa","Mishra Niwas,Near Viceroy Restaurant, Shamirpet, Secunderabad",null,270,4.3));
        restaurants.add(new Restaurants("Nathawat Rajwada","Dal Bhatti Churma","Singh's,Near ISKCON Hyderabad, Nampally Station Road,Hyderabad",null,300,4.4));
        restaurants.add(new Restaurants("Dwivedi Darbar","Desi Risotto","Dwivedi Mansion, Plot No: 69, Pokalwada Village Beside, Dream Valley, Tanasha Nagar, Hyderabad",null,310,4.8));
        restaurants.add(new Restaurants("Chauhan Snacky Bites","Bhalla","Survanshi's,GGWQ+M4 Hakimpet, Secunderabad",null,200,4.7));
        restaurants.add(new Restaurants("Baghel's UP Wale","Boti Kebab","Prasan Villa, Rd Number 1, Mada Manzil, Banjara Hills, Hyderabad",null,225,4.6));


        menu1.add(new Dish("Kashmiri Chicken Biryani","Non-Veg",350));
        menu1.add(new Dish("Falafal Kebab","Non-Veg",195));
        menu1.add(new Dish("Pepperoni Panner Pulao","Non-Veg",299));
        menu1.add(new Dish("Special Egg-Chicken Kebab","Non-Veg",150));

        menu2.add(new Dish("Bafauri Chawal","Veg",299));
        menu2.add(new Dish("Bore Baasi","Veg",249));
        menu2.add(new Dish("Faraa","Veg",279));
        menu2.add(new Dish("Dubki Kadi","Veg",239));


        menu3.add(new Dish("Rajastani Badi Thalli","Veg",599));
        menu3.add(new Dish("Dal Bati Churma","Veg",259));
        menu3.add(new Dish("Mirchi Bada","Veg",120));
        menu3.add(new Dish("Bajra Chutney Rotla","Veg",250));


        menu4.add(new Dish("Spicy Indian Steak","Non Veg",450));
        menu4.add(new Dish("Chicken Lemon Rice","Non Veg",210));
        menu4.add(new Dish("Desi Risotto","Non Veg",325));
        menu4.add(new Dish("Kadi Chawal","Veg",249));


        menu5.add(new Dish("Bhalla","Veg",150));
        menu5.add(new Dish("Dalmoth","Veg",225));
        menu5.add(new Dish("Bedai","Veg",250));
        menu5.add(new Dish("Mughlai Namkeen","Veg",189));


        menu6.add(new Dish("Shahi Paneer and Bread","Non Veg",299));
        menu6.add(new Dish("Tiranga Chilla","Veg",175));
        menu6.add(new Dish("Boti Kebab","Non Veg",250));
        menu6.add(new Dish("Kachori Sabzi Thalli","Veg",150));

        menus.add(menu1);
        menus.add(menu2);
        menus.add(menu3);
        menus.add(menu4);
        menus.add(menu5);
        menus.add(menu6);

        Log.e("","running adapter");
        adapter1 = new RestaurantAdapter(restaurants,imageUrls,getActivity(),oye,menus);
        recyclerView1.setAdapter(adapter1);
        return v;
    }
}