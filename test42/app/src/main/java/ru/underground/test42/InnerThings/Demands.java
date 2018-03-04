package ru.underground.test42.InnerThings;

import java.util.ArrayList;

public class Demands {
    public class CurrentIngredient
    {
        public Ingredient ingredient;
        public int size;
    }
    protected ArrayList<CurrentIngredient> m_ingredients;
    protected ArrayList<Tool> m_tools;

    Demands()
    {
        m_ingredients = new ArrayList();
        m_tools = new ArrayList();
    }

    public void Initialize(ArrayList<Integer> ingrIDs, ArrayList<Integer> ingrSizes, ArrayList<Integer> toolIDs)
    {
        //Добавление ингредиентов
        for(int i = 0;i<ingrIDs.size();i++)
        {
            try{
                CurrentIngredient ingr = new CurrentIngredient();
                ingr.ingredient = IngridientManager.getIngredient(ingrIDs.get(i));
                ingr.size = ingrSizes.get(i);
                if(ingr.ingredient != null)
                    m_ingredients.add(ingr);
            }
            catch (Exception e) {
            }
        }
        //Добавление инструментов
        for(int i = 0;i<toolIDs.size();i++)
        {
            try{
                Tool tool = ToolManager.getTool(toolIDs.get(i));
                if(tool != null)
                    m_tools.add(tool);
            }
            catch (Exception e) {
            }
        }
    }

    public ArrayList<CurrentIngredient> getIngredients(){
        return m_ingredients;
    }

    public ArrayList<Ingredient> getOnlyIngredients(){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for(int i = 0;i<m_ingredients.size();i++)
        {
            ingredients.add(m_ingredients.get(i).ingredient);
        }
        return ingredients;
    }

    public ArrayList<Tool> getTools(){
        return m_tools;
    }

    public float getPrice()
    {
        float price = 0;
        for(int i = 0;i<m_ingredients.size();i++) {
            CurrentIngredient ingr = m_ingredients.get(i);
            price+= ingr.ingredient.getPrice() * ingr.size;

        }

        return price;
    }

    public float getProtein()
    {
        float protein = 0;
        for(int i = 0;i<m_ingredients.size();i++) {
            CurrentIngredient ingr = m_ingredients.get(i);
            protein+= ingr.ingredient.getProtein() * ingr.size;

        }

        return protein;
    }

    public float getFats()
    {
        float fats = 0;
        for(int i = 0;i<m_ingredients.size();i++) {
            CurrentIngredient ingr = m_ingredients.get(i);
            fats+= ingr.ingredient.getFats() * ingr.size;
        }

        return fats;
    }

    public float getCarbonyd()
    {
        float carbonyd = 0;
        for(int i = 0;i<m_ingredients.size();i++) {
            CurrentIngredient ingr = m_ingredients.get(i);
            carbonyd+= ingr.ingredient.getCarbonyd() * ingr.size;
        }

        return carbonyd;
    }
}
