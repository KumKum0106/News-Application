package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements categoryRVAdapter.CategoryClickInterface{

    // 01b0c85a60fa487a97fa52c394b96d76
    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private categoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new categoryRVAdapter(categoryRVModalArrayList, this, this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fthumbnails%2F004%2F216%2F831%2Foriginal%2F3d-world-news-background-loop-free-video.jpg&tbnid=8hWFT1NCd6VSvM&vet=12ahUKEwjZ6pGB67b_AhV7itgFHYtcBPgQMygGegUIARCGAg..i&imgrefurl=https%3A%2F%2Fwww.vecteezy.com%2Ffree-videos%2Fnews-background&docid=rmahxeYgvRtVmM&w=3840&h=2160&q=all%20news%20image&ved=2ahUKEwjZ6pGB67b_AhV7itgFHYtcBPgQMygGegUIARCGAg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fm.economictimes.com%2Fthumb%2Fmsid-80278033%2Cwidth-1200%2Cheight-900%2Cresizemode-4%2Cimgsize-644351%2Ftechnology.jpg&tbnid=Lm_rXhJhiiRcdM&vet=12ahUKEwiX9KiW67b_AhUJMLcAHa5bCMMQMygGegUIARD8AQ..i&imgrefurl=https%3A%2F%2Fm.economictimes.com%2Ftech%2Ftech-bytes%2Fettech-morning-dispatch-on-15-jan-2021-top-tech-news-to-start-your-day%2Farticleshow%2F80277917.cms&docid=i3pGyst4o9-kFM&w=1200&h=900&q=texhnology%20news&ved=2ahUKEwiX9KiW67b_AhUJMLcAHa5bCMMQMygGegUIARD8AQ"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fimg.freepik.com%2Ffree-vector%2Fscience-word-concept_23-2148533907.jpg&tbnid=9nRssQNnZ3L_0M&vet=12ahUKEwi3w4qo67b_AhX23nMBHdk6AJ0QMygAegUIARDrAQ..i&imgrefurl=https%3A%2F%2Fwww.freepik.com%2Ffree-photos-vectors%2Fscience-logo&docid=WoZ2iHtJcX4nOM&w=626&h=626&q=science%20logo&ved=2ahUKEwi3w4qo67b_AhX23nMBHdk6AJ0QMygAegUIARDrAQ"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F007%2F955%2F134%2Foriginal%2Fsport-logo-free-vector.jpg&tbnid=-L1sf0F-N0Dj_M&vet=12ahUKEwi9mOO267b_AhUhPbcAHUNpAFkQMygAegUIARDqAQ..i&imgrefurl=https%3A%2F%2Fwww.vecteezy.com%2Ffree-vector%2Fsports-logo&docid=V8WO2V5J9Kdy8M&w=1920&h=1920&q=sports%20logo&ved=2ahUKEwi9mOO267b_AhUhPbcAHUNpAFkQMygAegUIARDqAQ"));
        categoryRVModalArrayList.add(new CategoryRVModal("General", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fmedia.istockphoto.com%2Fid%2F929047972%2Fvector%2Fworld-news-flat-vector-icon-news-symbol-logo-illustration-business-concept-simple-flat.jpg%3Fs%3D612x612%26w%3D0%26k%3D20%26c%3D5jpcJ7xejjFa2qKCzeOXKJGeUl7KZi9qoojZj1Kq_po%3D&tbnid=aHKAXoXrhQvpzM&vet=12ahUKEwjv2LbG67b_AhV-13MBHT9pBkQQMygAegUIARDGAQ..i&imgrefurl=https%3A%2F%2Fwww.istockphoto.com%2Fillustrations%2Fnews-logo&docid=4TkW8YibVvooqM&w=612&h=612&q=general%20news%20logo&ved=2ahUKEwjv2LbG67b_AhV-13MBHT9pBkQQMygAegUIARDGAQ"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business", "https://www.google.com/imgres?imgurl=https%3A%2F%2F99designs-blog.imgix.net%2Fblog%2Fwp-content%2Fuploads%2F2016%2F08%2Ffeatured.png%3Fauto%3Dformat%26q%3D60%26w%3D2060%26h%3D1236%26fit%3Dcrop%26crop%3Dfaces&tbnid=RLn0G8lauzbDzM&vet=12ahUKEwjngZfj67b_AhWJKrcAHfFhA54QMygMegUIARD8AQ..i&imgrefurl=https%3A%2F%2F99designs.com%2Fblog%2Ftips%2Ftypes-of-logos%2F&docid=xqICHFMKrhE4pM&w=2060&h=1236&q=business%20logo&ved=2ahUKEwjngZfj67b_AhWJKrcAHfFhA54QMygMegUIARD8AQ"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fglobal-uploads.webflow.com%2F5e157548d6f7910beea4e2d6%2F62c2f0048f5e0d3504f2ce10_free-logo-design-entertainment.png&tbnid=gkYrpe6MMSB3TM&vet=12ahUKEwjs0cvx67b_AhU0PbcAHaCIC5QQMygHegUIARDuAQ..i&imgrefurl=https%3A%2F%2Flogo.com%2Flogos%2Fentertainment&docid=wn0Lag4TXUubTM&w=731&h=731&q=entertainment%20logo&ved=2ahUKEwjs0cvx67b_AhU0PbcAHaCIC5QQMygHegUIARDuAQ"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.trivitron.com%2Fblog%2Fwp-content%2Fuploads%2F2019%2F09%2Fshutterstock_582412642-836x418.jpg&tbnid=0yiHvR-3PzN1EM&vet=12ahUKEwj9jIqD7Lb_AhWH2HMBHdGBCIQQMygFegUIARDnAQ..i&imgrefurl=https%3A%2F%2Fwww.trivitron.com%2Fblog%2Fhow-healthcare-industry-helps-in-contributing-to-the-economy%2F&docid=2yemyUmxZbdQ6M&w=836&h=418&q=healthcare%20&ved=2ahUKEwj9jIqD7Lb_AhWH2HMBHdGBCIQQMygFegUIARDnAQ"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apikey=01b0c85a60fa487a97fa52c394b96d76";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=01b0c85a60fa487a97fa52c394b96d76";
        String BASE_URL = "https://newsapi.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for(int i = 0; i<articles.size(); i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {

            }
        });
        
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }
}