package ru.underground.test42;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import ru.underground.test42.InnerThings.Ingredient;
import ru.underground.test42.InnerThings.IngridientManager;
import ru.underground.test42.InnerThings.MainSystem;
import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.InnerThings.Searchable;
import ru.underground.test42.Lists.IngredientAdapter;
import ru.underground.test42.Lists.RecipesAdapter;

import static ru.underground.test42.R.id.recipeList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AdapterView.OnItemClickListener listener;
    Activity activity=this;
    SharedPreferences preferences;
    static ArrayList<String> dis;
    ListView mainList;
    ArrayList<Ingredient> ingredientsSearch;
    Button book, podbor;
    View homeView,recipeView;
    private EditText searchText;
    IngredientAdapter ingredientAdapter;
    RecipesAdapter recipesAdapter;

    public static Recipe staticRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Инициализация системы

        super.onCreate(savedInstanceState);
        MainSystem.Initialize(this);

        ingredientAdapter = new IngredientAdapter(activity,new ArrayList<Ingredient>());
        recipesAdapter=new RecipesAdapter(activity,new ArrayList<Recipe>());
        ingredientsSearch=new ArrayList<Ingredient>();
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_ingredients_dislike);
        listView=(ListView)findViewById(R.id.menuList);
        mainList = (ListView)findViewById(recipeList);
        homeView=findViewById(R.id.homeLayout);
        recipeView=findViewById(R.id.recipeBookView);
        book=(Button)findViewById(R.id.button2);
        podbor=(Button)findViewById(R.id.button3);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeView.setVisibility(View.GONE);
                recipeView.setVisibility(View.VISIBLE);
                mainList.setAdapter(recipesAdapter);
                recipesAdapter.clear();
                recipesAdapter.addAll(MainSystem.getRecipeList().getRecipes());
                recipesAdapter.notifyDataSetChanged();
                setTitle("Книга рецептов");
            }
        });
        podbor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeView.setVisibility(View.GONE);
                recipeView.setVisibility(View.VISIBLE);
                mainList.setAdapter(ingredientAdapter);
                ingredientAdapter.clear();
                ingredientAdapter.addAll(IngridientManager.getAllIngredients());
                ingredientAdapter.notifyDataSetChanged();
            }
        });
        final LinearLayout frameView=(LinearLayout)findViewById(R.id.frameView);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.addAll("Главная","Профиль","История","Любимые блюда","Нелюбимые ингридиенты");
        adapter.notifyDataSetChanged();
        listener= new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    homeView.setVisibility(View.VISIBLE);
                    recipeView.setVisibility(View.GONE);
                }else{
                    homeView.setVisibility(View.GONE);
                    recipeView.setVisibility(View.VISIBLE);
                }
                if(position==2) {

                }if (position==4){
                    Toast.makeText(getApplicationContext(),"Выберите нелюбимые ингредиенты, чтобы исключить блюда с ними из поиска",Toast.LENGTH_LONG).show();
                    dis = new ArrayList<>();
                    dis.addAll(preferences.getStringSet("dislikes",new HashSet<String>()));
                    mainList.setAdapter(ingredientAdapter);
                    ingredientAdapter.clear();
                   ingredientAdapter.addAll(IngridientManager.getAllIngredients());

                   ingredientAdapter.notifyDataSetChanged();

                }else if(position==3){

                }
            }
        };
        listView.setOnItemClickListener(listener);
        searchText=(EditText)findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<Searchable> newList1 = search(editable.toString(),IngridientManager.getAllIngredients());
                ArrayList<Searchable> newList2 = search(editable.toString(),MainSystem.getRecipeList().getRecipes().toArray(new Searchable[]{}));
                ingredientAdapter.clear();
                for(int i = 0;i<newList1.size();i++)
                {
                    ingredientAdapter.add((Ingredient)newList1.get(i));
                }
                ingredientAdapter.notifyDataSetChanged();
                recipesAdapter.clear();
                for(int i = 0;i<newList2.size();i++)
                {
                    recipesAdapter.add((Recipe) newList2.get(i));
                }
                recipesAdapter.notifyDataSetChanged();
            }
        });
    }
    private ArrayList<Searchable> search(String searchStr, Searchable[] objects)
    {
        ArrayList<Searchable> result = new ArrayList<>();
        for(int i = 0;i<objects.length;i++)
        {
            if(checkStr(objects[i].searchStr(), searchStr))
                result.add(objects[i]);
        }
        return result;
    }



    private boolean checkStr(String str, String searchStr)
    {
        if(str.toLowerCase().contains(searchStr.toLowerCase()) )
            return true;
        else
            return false;
    }
}
