package com.example.igor.profile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ru.underground.test42.InnerThings.Ingredient;
import ru.underground.test42.InnerThings.IngridientManager;
import ru.underground.test42.InnerThings.MainSystem;
import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.Lists.RecipesAdapter;

import static ru.underground.test42.R.id.recipeList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AdapterView.OnItemClickListener listener;
    Activity activity=this;
    SharedPreferences preferences;
    static ArrayList<String> dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Инициализация системы
        MainSystem.Initialize();

        super.onCreate(savedInstanceState);
        MainSystem.Initialize();
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_ingredients_dislike);
        listView=(ListView)findViewById(R.id.menuList);
        final LinearLayout frameView=(LinearLayout)findViewById(R.id.frameView);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.addAll("Главная","Профиль","История","Любимые блюда","Нелюбимые ингридиенты");
        adapter.notifyDataSetChanged();
        listener= new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==4){
                    Toast.makeText(getApplicationContext(),"Выберите нелюбимые ингредиенты, чтобы исключить блюда с ними из поиска",Toast.LENGTH_LONG).show();
                    ListView mainList=(ListView)findViewById(recipeList);
                    final IngredientAdapter recipesAdapter=new IngredientAdapter(activity,new ArrayList<Ingredient>());
                    dis = new ArrayList<>();
                    dis.addAll(preferences.getStringSet("dislikes",new HashSet<String>()));
                    mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("list","click");

                        }
                    });
                    mainList.setAdapter(recipesAdapter);
                    recipesAdapter.add(IngridientManager.getIngredient(0), Arrays.asList(dis.toArray()).contains(IngridientManager.getIngredient(0).getName()));
                    recipesAdapter.add(IngridientManager.getIngredient(1), Arrays.asList(dis.toArray()).contains(IngridientManager.getIngredient(1).getName()));
                    recipesAdapter.add(IngridientManager.getIngredient(2), Arrays.asList(dis.toArray()).contains(IngridientManager.getIngredient(2).getName()));
                    recipesAdapter.notifyDataSetChanged();

                }
            }
        };
        listView.setOnItemClickListener(listener);
    }
}
